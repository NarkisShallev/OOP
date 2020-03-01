package sprite;

import biuoop.DrawSurface;

/**
 * interfacename: Sprite
 * A game object that can be drawn to the screen.
 * Sprites can be drawn on the screen, and can be notified that time has passed (so that they know to change their
 * position / shape / appearance / etc)
 *
 * @author Narkis Kremizi
 * @version 1.0 19/04/2018
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d surface
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     * @param dt frame rate
     */
    void timePassed(double dt);
}
