import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Test {
	public static void main(String[] args) {

	    // read the n points from a file
	    In in = new In("files/input6.txt");
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	    	p.draw();
	    }
	    StdDraw.show();
	    
	    // print and draw the line segments
	   /* BruteCollinearPoints collinear1 = new BruteCollinearPoints(points);
	    for (LineSegment segment : collinear1.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();*/
	    
	    FastCollinearPoints collinear2 = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear2.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
}