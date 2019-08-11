import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point>{

    private final int x;
    private final int y;

    public Point (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x,y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x,  that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that) {
        if ((this.y < that.y) || (this.y == that.y && this.x < that.x)) {
            return -1;
        } else if (this.y == that.y && this.x == that.x) {
            return 0;
        } else {
            return 1;
        }
    }

    public double slopeTo(Point that) {
        if( (double)(that.x - this.x) == 0) {
            if ((double)(that.y - this.y) != 0) {
                return Double.POSITIVE_INFINITY;
            } else {
                return Double.NEGATIVE_INFINITY;
            }
        }
        if ((double)(that.y - this.y) == 0 ) {
            return 0;
        }
        return ((double)(that.y - this.y) / (double)(that.x - this.x));
    }

    public Comparator<Point> slopeOrder() {
        return new CompareSlope();
    }

    private class CompareSlope implements Comparator<Point> {

        Point that = Point.this;

        public int compare(Point a, Point b) {
            if( that.slopeTo(a) < that.slopeTo(b)){
                return -1;
            } else  if ( that.slopeTo(a) == that.slopeTo(b)) {
                return 0;
            } else {
                return  1;
            }
        }
    }

    public static void main(String args[]){

        In in = new In(args[0]);
        int n = in.readInt();
        int padding = 1000;

        StdDraw.setXscale(-padding, Short.MAX_VALUE + padding);
        StdDraw.setYscale(-padding, Short.MAX_VALUE + padding);

        Point[] points = new Point[n];

        int index = 0;

        while (!in.isEmpty()){
            points[index] = new Point(in.readInt(), in.readInt());
            index++;
        }

        for(Point p : points){
            p.draw();
        }

        BruteCollinearPoints BCP = new BruteCollinearPoints(points);
        LineSegment[] segments = BCP.segments();
        for(int i = 0; i < segments.length; i++) {
            if (segments[i] != null) {
                segments[i].draw();
            }
            else {
                break;
            }
        }
        StdDraw.show();
    }
}
//Горизонтально вверх   -    + бесконеченость
//Горизонтально вниз    -    - бесконеченость
//Вертикально вправо    -    0
//0 - Pi/2              -    +
//Pi/2 - Pi             -    -

//Подумать про арктангенс!
