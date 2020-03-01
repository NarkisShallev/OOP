package sprite;

import biuoop.DrawSurface;

import java.awt.Image;

/**
 * background with image.
 */
public class ImageBackground implements Background {
    // members
    private Image image;

    /**
     * constructor.
     *
     * @param image image
     */
    public ImageBackground(Image image) {
        this.image = image;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d surface
     */
    public void drawOn(DrawSurface d) {
        d.drawImage(0, 0, this.image);
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
        d.drawImage(xpos, ypos, image);
    }
}
