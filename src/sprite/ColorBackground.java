package sprite;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * background with one color (RGB or regular color).
 */
public class ColorBackground implements Background {
    // members
    private Color color;

    /**
     * constructor.
     *
     * @param color color
     */
    public ColorBackground(Color color) {
        this.color = color;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        drawOn(d, 0, 0, 800, 600);
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt frame rate
     */
    public void timePassed(double dt) {
    }

    @Override
    public void drawOn(DrawSurface d, int xpos, int ypos, int width, int height) {
        d.setColor(color);
        d.fillRectangle(xpos, ypos, width, height);
    }
}
