package game;

import basic.Velocity;
import basic.Collidable;
import basic.HitListener;
import basic.HitNotifier;
import sprite.Background;
import sprite.Sprite;
import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * classname: Block
 * Creates a block which is a rectangle with color.
 *
 * @author Narkis Kremizi
 * @version 1.0 19/04/2018
 */
public class Block implements Collidable, Sprite, HitNotifier {
    /* members. */
    private Rectangle block;
    private java.awt.Color frameColor;
    private java.awt.Color fillColor;
    private int numOfHitPoints;
    private List<HitListener> hitListeners;
    private Map<Integer, Background> fillBackgrounds;

    /**
     * A constructor that creates a block.
     *
     * @param block          block
     * @param fillColor      fill color
     * @param numOfHitPoints number of hit points
     */
    public Block(Rectangle block, java.awt.Color fillColor, int numOfHitPoints) {
        this.block = block;
        this.frameColor = Color.BLACK;
        this.fillColor = fillColor;
        this.numOfHitPoints = numOfHitPoints;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * constructor that create a block at the specified location.
     *
     * @param x x position
     * @param y y position
     */
    public Block(int x, int y) {
        this.block = new Rectangle(new Point(x, y), 0, 0);
        this.frameColor = null;
        this.fillColor = null;
        this.numOfHitPoints = 0;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * Return the "collision shape" of the object.
     *
     * @return collision shape
     */
    public Rectangle getCollisionRectangle() {
        return this.block;
    }

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based on the force the object inflicted on us).
     *
     * @param collisionPoint  collision point
     * @param currentVelocity the velocity before the collision
     * @param hitter          a ball
     * @return new velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Velocity newVelocity = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
        /* Finding the vertices of the rectangle. */
        Point upperLeft = this.block.getUpperLeft();
        Point upperRight = new Point(upperLeft.getX() + this.block.getWidth(), upperLeft.getY());
        Point lowerLeft = new Point(upperLeft.getX(), upperLeft.getY() + this.block.getHeight());
        /* If the point is in up or down we will change its dy to -dy. */
        if ((collisionPoint.getY() == upperLeft.getY()) || (collisionPoint.getY() == lowerLeft.getY())) {
            newVelocity.setDy(-newVelocity.getDy());
            if (this.numOfHitPoints != 0) {
                setNumOfHitPoints(this.numOfHitPoints - 1);
            }
        }
        /* If the point is in left or right we will change its dx to -dx. */
        if ((collisionPoint.getX() == upperLeft.getX()) || (collisionPoint.getX() == upperRight.getX())) {
            newVelocity.setDx(-newVelocity.getDx());
            if (this.numOfHitPoints != 0) {
                setNumOfHitPoints(this.numOfHitPoints - 1);
            }
        }
        this.notifyHit(hitter);
        return newVelocity;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d surface
     */
    public void drawOn(DrawSurface d) {
        if (this.fillBackgrounds != null) {
            if (this.fillBackgrounds.containsKey(this.numOfHitPoints)) {
                this.fillBackgrounds.get(this.numOfHitPoints).drawOn(d, (int) this.block.getUpperLeft().getX(),
                        (int) this.block.getUpperLeft().getY(), (int) this.block.getWidth(),
                        (int) this.block.getHeight());
            } else {
                this.fillBackgrounds.get(0).drawOn(d, (int) this.block.getUpperLeft().getX(),
                        (int) this.block.getUpperLeft().getY(), (int) this.block.getWidth(),
                        (int) this.block.getHeight());
            }
        } else {
            /* Set the color to the fill's color of the block. */
            d.setColor(this.fillColor);
            /* fill the block. */
            d.fillRectangle((int) (this.block.getUpperLeft().getX()), (int) (this.block.getUpperLeft().getY()),
                    (int) (this.block.getWidth()), (int) (this.block.getHeight()));
        }
        if (this.frameColor != null) {
            d.setColor(this.frameColor);
            d.drawRectangle((int) this.block.getUpperLeft().getX(), (int) this.block.getUpperLeft().getY(),
                    (int) this.block.getWidth(), (int) this.block.getHeight());
        }
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt frame rate
     */
    public void timePassed(double dt) {
    }

    /**
     * add the block to the game as sprite and as collidable.
     *
     * @param g the current game
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * set the num of the hits.
     *
     * @param newNum num of hits
     */
    public void setNumOfHitPoints(int newNum) {
        this.numOfHitPoints = newNum;
    }

    /**
     * remove the block from the game as sprite and as collidable.
     *
     * @param g the current game
     */
    public void removeFromGame(GameLevel g) {
        g.removeCollidable(this);
        g.removeSprite(this);
    }

    /**
     * remove the block from the game as sprite.
     *
     * @param g the current game
     */
    public void removeFromSprite(GameLevel g) {
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

    /**
     * the method will be called whenever a hit() occurs, and notifiers all of the registered HitListener objects
     * by calling their hitEvent method.
     *
     * @param hitter a ball
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event.
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * return the num of hit points.
     *
     * @return num of hit points
     */
    public int getHitPoints() {
        return this.numOfHitPoints;
    }

    /**
     * set fill color.
     *
     * @param newFill new fill color
     */
    public void setFillColor(Color newFill) {
        this.fillColor = newFill;
    }

    /**
     * set the width of the block.
     *
     * @param newWidth new width
     */
    public void setWidth(int newWidth) {
        this.block.setWidth(newWidth);
    }

    /**
     * set the height of the block.
     *
     * @param newHeight new height
     */
    public void setHeight(int newHeight) {
        this.block.setHeight(newHeight);
    }

    /**
     * set the frame color of the block.
     *
     * @param newFrameColor new frame color
     */
    public void setFrameColor(Color newFrameColor) {
        this.frameColor = newFrameColor;
    }

    /**
     * set fill background.
     *
     * @param fillBackground fillBackground
     */
    public void setFillBackgrounds(Map<Integer, Background> fillBackground) {
        this.fillBackgrounds = fillBackground;
    }
}
