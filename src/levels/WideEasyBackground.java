package levels;

import sprite.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * background for WideEazy level.
 */
public class WideEasyBackground implements Sprite {
    // members
    private final int width = 800;

    /**
     * draw the sprite to the screen.
     *
     * @param d surface
     */
    public void drawOn(DrawSurface d) {
        // white background
        d.setColor(Color.WHITE);
        d.fillRectangle(20, 40, 760, 560);
        // sun
        d.setColor(new Color(236, 237, 164));
        for (int i = 0; i < 100; ++i) {
            d.drawLine(150, 150, 750 / 100 * i, 275);
        }
        d.fillCircle(150, 150, 60);
        d.setColor(new Color(231, 205, 40));
        d.fillCircle(150, 150, 50);
        d.setColor(new Color(255, 242, 60));
        d.fillCircle(150, 150, 40);
    }

    /**
     * notify the sprite that time has passed.
     * @param dt frame rate
     */
    public void timePassed(double dt) {

    }
}
