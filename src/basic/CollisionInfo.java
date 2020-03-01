package basic;

import geometry.Point;

/**
 * classname: CollisionInfo
 * Gives us some information about the collision.
 *
 * @author Narkis Kremizi
 * @version 1.0 19/04/2018
 */
public class CollisionInfo {
    /* Members. */
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * @param collisionPoint  collision with point
     * @param collisionObject collition with object
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * the point at which the collision occurs.
     *
     * @return collision point
     */
    public Point collisionPoint() {
        return collisionPoint;
    }

    /**
     * the collidable object involved in the collision.
     *
     * @return collision with the object
     */
    public Collidable collisionObject() {
        return collisionObject;
    }
}
