package game;

import basic.HitListener;
import basic.HitNotifier;
import sprite.Sprite;
import basic.Velocity;
import basic.CollisionInfo;
import biuoop.DrawSurface;
import geometry.Line;
import geometry.Point;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * classname: Ball.
 * Creates a ball which have size (radius), color, and location (a Point). Balls also know how to draw themselves on
 * a DrawSurface.
 *
 * @author Narkis Kremizi
 * @version 1.1 19/04/2018
 */
public class Ball implements Sprite, HitNotifier {
    /* Members. */
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity v;
    private GameEnvironment ballGameEnvironment;
    private List<HitListener> hitListeners;

    /**
     * constructor which gets a center of a ball, a radius and a color and create a ball.
     *
     * @param center center
     * @param r      radius
     * @param color  color
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * Return the X value of the center.
     *
     * @return the X value of the center
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Return the Y value of the center.
     *
     * @return the Y value of the center
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Return the radius of the ball.
     *
     * @return the radius of the ball
     */
    public int getSize() {
        return this.r;
    }

    /**
     * Return the color of the ball.
     *
     * @return the color of the ball
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * draw the ball on the given DrawSurface.
     *
     * @param surface surface to draw the ball on it
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.BLACK);
        // draw the fram of the ball
        surface.drawCircle(this.getX(), this.getY(), r);
        // Set the color to the color of the ball
        surface.setColor(color);
        // fill the ball
        surface.fillCircle(this.getX(), this.getY(), r);
    }

    /**
     * Sets the speed of the ball to the velocity we received.
     *
     * @param newV velocity of the ball
     */
    public void setVelocity(Velocity newV) {
        this.v = newV;
    }

    /**
     * Creating a new velocity using dx and dy we received and sets the velocity of the ball to the new one.
     *
     * @param dx progress on the X axis
     * @param dy progress on the Y axis
     */
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }

    /**
     * Return the velocity of the  ball.
     *
     * @return the velocity of the  ball
     */
    public Velocity getVelocity() {
        return this.v;
    }

    /**
     * Promotes the ball in its trajectory, in case of a collision and in case of non-collision.
     *
     * @param dt frame rate
     */
    public void moveOneStep(double dt) {
        /* compute the ball trajectory . */
        Line trajectory = this.computeTrajectory(dt);
        /* Check if moving on this trajectory will hit anything. */
        CollisionInfo closestCollision = this.ballGameEnvironment.getClosestCollision(trajectory);
        /* If no, then the ball move to the end of the trajectory. */
        if (closestCollision == null) {
            this.center = this.v.applyToPoint(this.center, dt);
            return;
        }
        /* Otherwise (there is a hit): the ball move to "almost" the hit point, but just slightly before it. */
        this.v = closestCollision.collisionObject().hit(this, closestCollision.collisionPoint(), this.v);
        this.center = this.v.applyToPoint(this.center, dt);
    }

    /**
     * Sets the color of the ball to the color we received.
     *
     * @param newColor new color
     */
    public void setColor(java.awt.Color newColor) {
        this.color = newColor;
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt seconds
     */
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * return the trajectory of the ball.
     *
     * @param dt frame rate
     * @return trajectory
     */
    public Line computeTrajectory(double dt) {
        /* saved the dx and dy values in temporary variables. */
        double dx = this.v.getDx();
        double dy = this.v.getDy();
        /* the trajectory of the ball. */
        return new Line(this.center, new Point(this.center.getX() + dx * dt, this.center.getY() + dy * dt));
    }

    /**
     * Set the current environment of the ball.
     *
     * @param game current game
     */
    public void setBallGameEnvironment(GameEnvironment game) {
        this.ballGameEnvironment = game;
    }

    /**
     * add the ball to the game as sprite.
     *
     * @param g the current game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * remove the ball from the game as sprite.
     *
     * @param g the current game
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl Hit listener
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl Hit listener
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
}
