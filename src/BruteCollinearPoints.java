import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] lineSegments;
    private int length;
    private Point[] points;

    public BruteCollinearPoints(Point[] _points) {
        checkPoint(_points);
        this.points = _points.clone();
        Arrays.sort(this.points);

        lineSegments = new LineSegment[points.length];
        double slope;
        int comparableElem = 0,
                leftMarker = 0,
                currentComparableLeft = 0,
                countPoints = 2,
                currentLineSegments = 0;
        while (comparableElem < points.length-3) {
            currentComparableLeft = comparableElem + 1;
            while(currentComparableLeft < points.length) {
                if( points[comparableElem] == null || points[currentComparableLeft] == null) {
                    throw new java.lang.IllegalArgumentException();
                }
                slope = points[comparableElem].slopeTo(points[currentComparableLeft]);
                for(int i = currentComparableLeft + 1; i < points.length; i++) {
                    if( points[i] == null ) {
                        throw new java.lang.IllegalArgumentException();
                    }
                    if(slope == points[comparableElem].slopeTo(points[i])) {
                        leftMarker = i;
                        countPoints++;
                    }
                }
                if(countPoints>=4) {
                    lineSegments[currentLineSegments] = new LineSegment(points[comparableElem], points[leftMarker]);
                    currentLineSegments++;
//                    if(!containSegments(points[comparableElem], points[leftMarker])) {
//
//                    }
                }
                currentComparableLeft++;
                leftMarker = 0;
                countPoints = 2;
            }
            comparableElem++;
        }
        length = currentLineSegments;
    }

    private boolean containSegments(Point a, Point b) {
        boolean isContain = false;
        int i = 0;
        LineSegment testLine = new LineSegment(a, b);
        LineSegment testLineReverse = new LineSegment(b, a);
        while (lineSegments[i] != null) {
            if(testLine.toString().equals(lineSegments[i].toString()) || testLineReverse.toString().equals(lineSegments[i].toString())) {
                isContain = true;
            }
            i++;
        }
        return isContain;
    }

    public int numberOfSegments() {
        return length;
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

    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, length);
    }

    public static void main(String[] args){
        In in = new In("test\\input8.txt");      // input file
        int n = in.readInt();

        // padding for drawing
        int padding = 1000;

        // set draw scale
        StdDraw.setXscale(-padding, Short.MAX_VALUE + padding);
        StdDraw.setYscale(-padding, Short.MAX_VALUE + padding);

        // Index of array
        int index = 0;

        // turn on animation mode
        StdDraw.enableDoubleBuffering();

        // Create array
        Point[] points = new Point[n];

        points[index] = new Point(in.readInt(), in.readInt());
        points[index].draw();
        StdDraw.show();

        index++;

        while (!in.isEmpty()) {
            points[index] = new Point(in.readInt(), in.readInt());
            points[index].draw();
            StdDraw.show();

            index++;
        }

        points = new Point[1];
        points[0] = null;

        BruteCollinearPoints bfcp = new BruteCollinearPoints(points);
        LineSegment[] lines = bfcp.segments();
    }
}
