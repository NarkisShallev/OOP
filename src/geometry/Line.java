package geometry;

import java.util.List;

/**
 * classname: Line
 * A line (actually a line-segment) connects two points -- a start point and an end point. Lines have lengths, and may
 * intersect with other lines. It can also tell if it is the same as another line segment.
 *
 * @author Narkis Kremizi
 * @version 1.1 09/04/2018
 */
public class Line {
    /* Members. */
    private Point start;
    private Point end;
    private Point intersection;

    /**
     * constructor which gets a start point and an end point and creates a line.
     *
     * @param start start point
     * @param end   end point
     */
    public Line(Point start, Point end) {
        /* The starting point will be the point with the smallest X value. */
        if (start.getX() < end.getX()) {
            this.start = start;
            this.end = end;
        }
        this.start = end;
        this.end = start;
    }

    /**
     * constructor which gets X and Y values of 2 points and creates points and then lines from them.
     *
     * @param x1 X value of one point
     * @param y1 Y value of one point
     * @param x2 X value of another point
     * @param y2 Y value of another point
     */
    public Line(double x1, double y1, double x2, double y2) {
        if (x1 < x2) {
            this.start = new Point(x1, y1);
            this.end = new Point(x2, y2);
        }
        this.start = new Point(x2, y2);
        this.end = new Point(x1, y1);
    }

    /**
     * Return the length of the line.
     *
     * @return length
     */
    public double length() {
        /* The function uses the distance method to calculate the length of the line (the distance between the
         starting point and the end point). */
        return start.distance(this.end);
    }

    /**
     * Returns the middle point of the line.
     *
     * @return middle point
     */
    public Point middle() {
        /* Calculates the midpoint of the line by formula. */
        double middleX = (this.start.getX() + this.end.getX()) / 2;
        double middleY = (this.start.getY() + this.end.getY()) / 2;
        Point middle = new Point(middleX, middleY); // Create the midpoint
        return middle;
    }

    /**
     * Returns the start point of the line.
     *
     * @return start point
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the end point of the line.
     *
     * @return end point
     */
    public Point end() {
        return this.end;
    }

    /**
     * Returns true if the lines intersect, false otherwise.
     *
     * @param other other line
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        /* Sets the edges between which the intersection point should be. */
        double edgeL1X1 = this.start.getX();
        double edgeL1Y1 = this.start.getY();
        double edgeL1X2 = this.end.getX();
        double edgeL1Y2 = this.end.getY();
        double edgeL2X1 = other.start().getX();
        double edgeL2Y1 = other.start().getY();
        double edgeL2X2 = other.end().getX();
        double edgeL2Y2 = other.end().getY();
        double slopeL1;
        double slopeL2;
        double intersectionX = 0;
        double intersectionY = 0;
        /* If these are lines that are actually a point. */
        if (this.equals(other) && this.start.equals(this.end)) {
            intersectionX = edgeL1X1;
            intersectionY = edgeL1Y1;
            /* If at least one of the lines is vertical. */
        } else if ((edgeL1X2 - edgeL1X1 == 0) || (edgeL2X2 - edgeL2X1 == 0)) {
            if ((edgeL1X2 - edgeL1X1 == 0) && (edgeL2X2 - edgeL2X1 == 0)) { // If both are vertical
                return false;
            } else if (edgeL1X2 - edgeL1X1 == 0) { // If the first is vertical
                slopeL2 = other.slope();
                intersectionX = edgeL1X1;
                intersectionY = slopeL2 * (intersectionX - edgeL2X1) + edgeL2Y1;
            } else if (edgeL2X2 - edgeL2X1 == 0) { // If the second is vertical
                slopeL1 = this.slope();
                intersectionX = edgeL2X1;
                intersectionY = slopeL1 * (intersectionX - edgeL1X1) + edgeL1Y1;
            }
        } else {
            /* Find the slopes of the two lines. */
            slopeL1 = this.slope();
            slopeL2 = other.slope();
            /* If the slopes are equal, the lines are parallel and therefore have no intersection point. */
            if (slopeL1 == slopeL2) {
                return false;
            }
            /* If they do not parallel they have an intersection point and we calculate it with a formula. */
            intersectionX = (slopeL1 * edgeL1X1 - edgeL1Y1 - slopeL2 * other.start().getX()
                    + other.start().getY()) / (slopeL1 - slopeL2);
            intersectionY = slopeL1 * intersectionX - slopeL1 * edgeL1X1 + edgeL1Y1;
        }
        this.intersection = new Point(intersectionX, intersectionY);
        /* If the intersection point is not between the edges of the lines, false is returned, otherwise true
         is returned. */
        if ((intersectionX <= Math.max(edgeL1X1, edgeL1X2)) && (intersectionX >= Math.min(edgeL1X1, edgeL1X2))
                && (intersectionY <= Math.max(edgeL1Y1, edgeL1Y2)) && (intersectionY >= Math.min(edgeL1Y1, edgeL1Y2))
                && (intersectionX <= Math.max(edgeL2X1, edgeL2X2)) && (intersectionX >= Math.min(edgeL2X1, edgeL2X2))
                && (intersectionY <= Math.max(edgeL2Y1, edgeL2Y2)) && (intersectionY >= Math.min(edgeL2Y1, edgeL2Y2))) {
            return true;
        }
        return false;
    }

    /**
     * Returns the intersection point if the lines intersect, and null otherwise.
     *
     * @param other other line
     * @return the intersection point if the lines intersect, and null otherwise
     */
    public Point intersectionWith(Line other) {
        /* If the lines have an intersection point calculate it with a formula. */
        if (this.isIntersecting(other)) {
            return this.intersection;
        }
        /* Otherwise, return null. */
        return null;
    }

    /**
     * equals - return true if the lines are equal, false otherwise.
     *
     * @param other other line
     * @return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        /* If the start and end points of the lines are the same - they are equal. */
        if ((this.start.equals(other.start()) && this.end.equals(other.end())) || (this.end.equals(other.start())
                && this.start.equals(other.end()))) {
            return true;
        }
        return false;
    }

    /**
     * Returns the slope of the lines.
     *
     * @return slope
     */
    public double slope() {
        /* Calculate the slope using a formula. */
        return (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
    }

    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the start of the line.
     *
     * @param rect a rectangle to check its intersection with this line
     * @return the closest intersection point to this start
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        /* If this line does not intersect with the rectangle, return null. */
        if (rect.intersectionPoints(this).size() == 0) {
            return null;
        }
        /* Create a list where we will save the rectangle intersections with the line. */
        List intersections = rect.intersectionPoints(this);
        Point closest = (Point) intersections.get(0); // Minimum value for comparison
        /* Go through the list and check which point is closest to this start - the point in the array or closest. */
        for (int i = 0; i < intersections.size(); i++) {
            if (this.start.distance((Point) intersections.get(i)) < this.start.distance(closest)) {
                closest = (Point) intersections.get(i);
            }
        }
        /* return the closest point to this start.*/
        return closest;
    }
}
