package game;

import basic.Collidable;
import basic.CollisionInfo;
import geometry.Line;
import geometry.Point;

import java.util.List;
import java.util.ArrayList;

/**
 * classname: GameEnvironment
 * A collection of many objects a Ball can collide with.
 *
 * @author Narkis Kremizi
 * @version 1.0 19/04/2018
 */
public class GameEnvironment {
    /* Members. */
    private List<Collidable> collidables;

    /**
     * A constructor that initializes the game.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }

    /**
     * add the given collidable to the environment.
     *
     * @param c new collidable
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * remove the given collidable from the environment.
     *
     * @param c collidable
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }


    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables in this collection, return null. Else, return the
     * information about the closest collision that is going to occur.
     *
     * @param trajectory line
     * @return collision info
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        int minDistance = 100000000;
        Point closestCollisionPoint = null;
        Collidable closestCollisionObject = null;
        /* Passes all the objects in the list. */
        for (Collidable current : collidables) {
            /* Saves the closest intersection point of the current object to the beginning of the line in a temporary
             variable. */
            Point tempClosestPoint = trajectory.closestIntersectionToStartOfLine(current.getCollisionRectangle());
            /* If there is an intersection point, and its distance is minimal, we will keep the point and object as a
             minimum point of collision. */
            if ((tempClosestPoint != null) && (tempClosestPoint.distance(trajectory.start()) < minDistance)) {
                closestCollisionPoint = tempClosestPoint;
                closestCollisionObject = current;
            }
        }
        /* If this object will not collide with any of the collidables in this collection, return null. */
        if (closestCollisionPoint == null) {
            return null;
        }
        /* return the information about the closest collision that is going to occur. */
        return new CollisionInfo(closestCollisionPoint, closestCollisionObject);
    }
}
