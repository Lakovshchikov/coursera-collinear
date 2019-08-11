import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;

public class FastCollinearPoints {

    private Point[] points;
    private int length;
    private LineSegment[] segments;


    public FastCollinearPoints(Point[] _points)   {
        checkPoint(_points);
        segments = new LineSegment[2];
        this.points = _points.clone();
        Point[] segment = new Point[_points.length];
        int segmentIndex = 0;
        int segmentsLength = 0;
        for (Point point : _points) {
            double prevSlope = 0.0;
            double currentSlope;
            Arrays.sort(points, point.slopeOrder());
            boolean first = true;
            for (int i = 0; i <= points.length; i++) {
                if (i == points.length) {
                    if(segmentIndex >= 3) {
                        segment[segmentIndex] = point;
                        Arrays.sort(segment, 0, segmentIndex+1 );
                        if (!containSegments(segment[0],segment[segmentIndex])) {
                            enqueue(new LineSegment(segment[0],segment[segmentIndex]));
                            segmentsLength++;
                        }
                    }
                    segment = new Point[_points.length];
                    segmentIndex = 0;
                    first = true;
                }
                else {
                    currentSlope = point.slopeTo(points[i]);
                    if (currentSlope == prevSlope) {
                        segment[segmentIndex] = points[i];
                        segmentIndex++;
                        if (first && points[i-1] != null) {
                            segment[segmentIndex] = points[i - 1];
                            segmentIndex++;
                            first = false;
                        }
                    }
                    else {
                        if(segmentIndex >= 3) {
                            segment[segmentIndex] = point;
                            Arrays.sort(segment, 0, segmentIndex+1 );
                            if (!containSegments(segment[0],segment[segmentIndex])) {
                                enqueue(new LineSegment(segment[0],segment[segmentIndex]));
                                segmentsLength++;
                            }
                        }
                        segment = new Point[_points.length];
                        segmentIndex = 0;
                        first = true;
                    }
                    prevSlope = currentSlope;
                }
            }
        }
        length = segmentsLength;
    }

    private void checkPoint( Point[] points ) {
        if(points == null) {
            throw new IllegalArgumentException();
        }
        for(int i = 0; i < points.length; i++) {
            for( int j = 0; j < points.length; j++){
                if (points[i] == null || points[j] == null )
                    throw new IllegalArgumentException();

                if (i != j && points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }
    }

    public int numberOfSegments() {
        return length;
    }

    private boolean containSegments(Point a, Point b) {
        boolean isContain = false;
        int i = 0;
        LineSegment testLine = new LineSegment(a, b);
        LineSegment testLineReverse = new LineSegment(b, a);
        while (i < length) {
            if(segments[i] != null) {
                if(testLine.toString().equals(segments[i].toString()) || testLineReverse.toString().equals(segments[i].toString())) {
                    isContain = true;
                    break;
                }
                i++;
            }
        }
        return isContain;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, length);
    }

    private void resize(int capacity) {
        assert capacity >= this.length;

        LineSegment[] temp = new LineSegment[capacity];
        System.arraycopy(this.segments, 0, temp, 0, this.length);
        this.segments = temp;

    }

    private void enqueue(LineSegment item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if(this.length == this.segments.length) {
            resize(2 * this.segments.length);
        }

        this.segments[this.length++] = item;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        LineSegment[] l = collinear.segments();
        for (LineSegment segment : collinear.segments()) {
            segment.draw();
        }
        StdDraw.show();
    }
}
