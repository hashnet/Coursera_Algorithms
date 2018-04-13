import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {
	private LineSegment[] segments;
	
	private class Slope {
		double slope;
		Point point1;
		Point point2;
	}
	
	public FastCollinearPoints(Point[] points) { 		   // finds all line segments containing 4 or more points
	
		checkNull(points);
		
		checkRepeatition(points);
		
		segments = computeSegments(Arrays.copyOf(points, points.length));
	}
	
	private void checkNull(Point[] points) {
		for(int i=0; i<points.length; i++) {
			if(points[i] == null) {
				throw new NullPointerException();
			}
		}
	}
	
	private void checkRepeatition(Point[] points) {
		for(int i=0; i<points.length; i++) {
			for(int j=i+1; j<points.length; j++) {
				if(points[i].compareTo(points[j]) == 0) {
					throw new IllegalArgumentException();
				}
			}
		}
	}
	
	private LineSegment[] computeSegments(Point[] points) {
		Arrays.sort(points);
		
		List<LineSegment> computedSegments = new ArrayList<>();
		List<Slope> tempSegments = new ArrayList<>();
		
		Slope[] slopes = new Slope[points.length];
		for(int i=0; i<points.length; i++) {
			for(int j=0; j<points.length; j++) {
				slopes[j] = new Slope();
				slopes[j].slope = points[i].slopeTo(points[j]);
				slopes[j].point2 = points[j];
			}
			
			Arrays.sort(slopes, new Comparator<Slope>(){

				@Override
				public int compare(Slope slope1, Slope slope2) {
					if(slope1.slope == slope2.slope) {
						return slope1.point2.compareTo(slope2.point2);
					} else {
						if(slope1.slope == slope2.slope) return 0;
						else if(slope1.slope < slope2.slope) return -1;
						else return 1;
					}
				}				
			});
			
			double slope = slopes[0].slope;
			int count = 1;
			int k = 1;
			while(k<points.length) {
				if(slopes[k].slope == slope) {
					++count;
				} else {
					if(count >= 3) {
						addLineSegmentIfUnique(tempSegments, points[i], slopes[k-1].point2);
					}
					
					slope = slopes[k].slope;
					count = 1;
				}
				
				k++;
			}
			
			if(count >= 3) {
				addLineSegmentIfUnique(tempSegments, points[i], slopes[k-1].point2);
			}
		}
		
		for(Slope slope : tempSegments) {
			computedSegments.add(new LineSegment(slope.point1, slope.point2));
		}
		
		return computedSegments.toArray(new LineSegment[computedSegments.size()]);
	}
	
	private void addLineSegmentIfUnique(List<Slope> tempSegments, Point point1, Point point2) {
		Point pointSmaller, pointBigger;
		if(point1.compareTo(point2) < 0) {
			pointSmaller = point1;
			pointBigger = point2;
		} else {
			pointSmaller = point2;
			pointBigger = point1;
		}
		
		Double slopeToAdd = pointSmaller.slopeTo(pointBigger);
		for(Slope slope : tempSegments) {
			if(slopeToAdd == slope.slope && pointBigger.compareTo(slope.point2) == 0) {
				return;
			}
		}
		
		Slope tempSegment = new Slope();
		tempSegment.slope = slopeToAdd;
		tempSegment.point1 = pointSmaller;
		tempSegment.point2 = pointBigger;
		tempSegments.add(tempSegment);
	}
	
	public int numberOfSegments() {        			 // the number of line segments
		return segments.length;
	}
	
	public LineSegment[] segments() {                // the line segments
		return Arrays.copyOf(segments, numberOfSegments());
	}
}
