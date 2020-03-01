package basic;

import game.Ball;
import geometry.Point;
import geometry.Rectangle;

/**
 * interfacename: Collidable
 * The Collidable interface will be used by things that can be collided with.
 *
 * @author Narkis Kremizi
 * @version 1.0 19/04/2018
 */
public interface Collidable {
    /**
     * Return the "collision shape" of the object.
     *
     * @return collision shape
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based on the force the object inflicted on us).
     *
     * @param collisionPoint  collision point
     * @param currentVelocity current velocity
     * @param hitter          a ball
     * @return new velocity
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
