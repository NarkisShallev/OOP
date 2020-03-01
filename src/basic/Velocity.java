package basic;

import geometry.Point;

/**
 * classname: Velocity
 * Velocity specifies the change in position on the `x` and the `y` axes.
 *
 * @author Narkis Kremizi
 * @version 1.1 03/04/2018
 */
public class Velocity {
    /* Members. */
    private double dx;
    private double dy;

    /**
     * Constructor which gets dx and dy and create a velocity.
     *
     * @param dx progress on the X axis
     * @param dy progress on the Y axis
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Return the dx of the velocity.
     *
     * @return dx of the velocity
     */
    public double getDx() {
        return dx;
    }

    /**
     * Return the dy of the velocity.
     *
     * @return dy of the velocity
     */
    public double getDy() {
        return dy;
    }

    /**
     * Sets the dx of the velocity to the  dx we received.
     *
     * @param newDx new dx of velocity
     */
    public void setDx(double newDx) {
        this.dx = newDx;
    }

    /**
     * Sets the dy of the velocity to the  dy we received.
     *
     * @param newDy new dy of velocity
     */
    public void setDy(double newDy) {
        this.dy = newDy;
    }

    /**
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     *
     * @param p  a point with position (x,y)
     * @param dt frame rate
     * @return a new point with position (x+dx, y+dy)
     */
    public Point applyToPoint(Point p, double dt) {
        double x = p.getX(); // Gets the X value of the point
        double y = p.getY(); // Gets the Y value of the point
        return new Point(x + this.dx * dt, y + this.dy * dt); // return a new point with position (x+dx, y+dy)
    }

    /**
     * return velocity consists of angle and speed.
     *
     * @param angle an angle
     * @param speed a speed
     * @return velocity consists of angle and speed
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        angle = Math.toRadians(angle); // Convert the angle to radians
        double dx = speed * Math.sin(angle); // Calculate dx by angle and speed
        double dy = speed * Math.cos(angle); // Calculate dy by angle and speed
        return new Velocity(dx, -dy); // return velocity consists of angle and speed
    }
}
