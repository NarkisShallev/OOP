package levels;

import sprite.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * background for Green3 level.
 */
public class Green3Background implements Sprite {
    // members
    private final int width = 800;

    /**
     * draw the sprite to the screen.
     *
     * @param d surface
     */
    public void drawOn(DrawSurface d) {
        // green background
        d.setColor(Color.GREEN.darker().darker());
        d.fillRectangle(20, 40, 760, 560);
        // Structure on the left
        d.setColor(Color.GRAY.darker().darker().darker());
        d.fillRectangle(55, 455, 100, 145);
        d.setColor(Color.WHITE);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                d.fillRectangle((int) (18 * i + 64), 30 * j + 465, 10, 25);
            }
        }
        d.setColor(Color.GRAY.darker().darker());
        d.fillRectangle(91, 410, 28, 45);
        d.fillRectangle(100, 200, 10, 210);
        d.setColor(Color.ORANGE);
        d.fillCircle(105, 190, 10);
        d.setColor(Color.RED);
        d.fillCircle(105, 190, 6);
        d.setColor(Color.WHITE);
        d.fillCircle(105, 190, 2);
    }

    /**
     * notify the sprite that time has passed.
     * @param dt frame rate
     */
    public void timePassed(double dt) {

    }
}
