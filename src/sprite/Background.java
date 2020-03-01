package sprite;

import biuoop.DrawSurface;

/**
 * background interface.
 */
public interface Background extends Sprite {
    /**
     * draw on.
     *
     * @param d      drawSurface
     * @param xpos   xpos
     * @param ypos   ypos
     * @param width  width
     * @param height height
     */
    void drawOn(DrawSurface d, int xpos, int ypos, int width, int height);
}
