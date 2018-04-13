import java.util.List;

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	private LineSegment[] segments;
	
	public BruteCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
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
		
		for(int i=0; i<points.length; i++) {
			for(int j=i+1; j<points.length; j++) {
				for(int k=j+1; k<points.length; k++) {
					for(int l=k+1; l<points.length; l++) {
						if(equal(points[i].slopeTo(points[j]), 
								points[i].slopeTo(points[k]), 
								points[i].slopeTo(points[l]))) {
							computedSegments.add(new LineSegment(points[i], points[l]));
						}
					}
				}
			}
		}
		
		
		return computedSegments.toArray(new LineSegment[computedSegments.size()]);
	}
	
	private boolean equal(double a, double b, double c) {
		if(a == b) {
			if(b == c) return true;
			else return false;
		} else {
			return false;
		}
	}
	
	public int numberOfSegments() {        			 // the number of line segments
		return segments.length;
	}
	
	public LineSegment[] segments() {                // the line segments
		return Arrays.copyOf(segments, numberOfSegments());
	}
}