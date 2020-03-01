package geometry;

import java.util.List;
import java.util.ArrayList;

/**
 * classname: Rectangle
 * Creates a rectangle.
 *
 * @author Narkis Kremizi
 * @version 1.1 09/04/2018
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * Create a new rectangle with location and width/height.
     *
     * @param upperLeft upperLeft point
     * @param width     width
     * @param height    height
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Return a (possibly empty) List of intersection points with the specified line.
     *
     * @param line A line that we want to check if it is cut with the rectangle
     * @return List with intersection points
     */
    public java.util.List intersectionPoints(Line line) {
        /* Create a list of points where we will save the intersection points of the line with the rectangle. */
        List<Point> intersections = new ArrayList<Point>();
        /* Finding the vertices of the rectangle. */
        Point upperRight = new Point(this.upperLeft.getX() + width, this.upperLeft.getY());
        Point lowerLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        Point lowerRight = new Point(upperRight.getX(), upperRight.getY() + height);
        /* Finding the rectangle lines. */
        Line upLine = new Line(upperLeft, upperRight);
        Line downLine = new Line(lowerLeft, lowerRight);
        Line rightLine = new Line(upperRight, lowerRight);
        Line leftLine = new Line(upperLeft, lowerLeft);
        /* Check if there is an intersection point with the rectangle. If so, the point will be saved in the list. */
        if (upLine.isIntersecting(line)) {
            intersections.add(upLine.intersectionWith(line));
        }
        if (downLine.isIntersecting(line)) {
            intersections.add(downLine.intersectionWith(line));
        }
        if (rightLine.isIntersecting(line)) {
            intersections.add(rightLine.intersectionWith(line));
        }
        if (leftLine.isIntersecting(line)) {
            intersections.add(leftLine.intersectionWith(line));
        }
        return intersections;
    }

    /**
     * Return the width of the rectangle.
     *
     * @return width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Return the height of the rectangle.
     *
     * @return height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the upper-left point of the rectangle.
     *
     * @return upper-left point
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * set the upperLeft point to the one received.
     *
     * @param newUpperLeft new upperLeft
     */
    public void setUpperLeft(Point newUpperLeft) {
        this.upperLeft = newUpperLeft;
    }

    /**
     * set width.
     *
     * @param newWidth new width
     */
    public void setWidth(double newWidth) {
        this.width = newWidth;
    }

    /**
     * set height.
     *
     * @param newHeight new height
     */
    public void setHeight(double newHeight) {
        this.height = newHeight;
    }
}