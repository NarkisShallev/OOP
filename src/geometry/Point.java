package geometry;

/**
 * classname: Point
 * A point has an x and a y value, and can measure the distance to other points, and if its is equal to another point.
 *
 * @author Narkis Kremizi
 * @version 1.1 09/04/2018
 */
public class Point {
    /* Members. */
    private double x;
    private double y;

    /**
     * Constructor which gets X and Y and creates a point.
     *
     * @param x X value of the point
     * @param y Y value of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * distance - return the distance of this point to the other point.
     *
     * @param other other point
     * @return distance
     */
    public double distance(Point other) {
        double dx = this.x - other.getX(); // Calculates the distance between X values
        double dy = this.y - other.getY(); // Calculates the distance between Y values
        /* Calculates the distance between the two points according to the distance formula. */
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    /**
     * equals - return true if the points are equal, false otherwise.
     *
     * @param other other point
     * @return true if the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        /* If the X values and the Y values are the same, the points are equal. */
        if ((this.x == other.getX()) && (this.y == other.getY())) {
            return true;
        }
        return false;
    }

    /**
     * Return the x value of this point.
     *
     * @return x value of this point
     */
    public double getX() {
        return this.x;
    }

    /**
     * Return the y value of this point.
     *
     * @return y value of this point
     */
    public double getY() {
        return this.y;
    }
}
