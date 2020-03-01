package levels;

import sprite.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * background for FinalFour level.
 */
public class FinalFourBackground implements Sprite {
    // members
    private final int width = 800;

    /**
     * draw the sprite to the screen.
     *
     * @param d surface
     */
    public void drawOn(DrawSurface d) {
        // blue background
        d.setColor(new Color(69, 142, 255));
        d.fillRectangle(20, 40, 760, 560);
        // rain left
        d.setColor(Color.WHITE);
        for (int i = 0; i < 10; i++) {
            d.drawLine((int) (190 - 9.5 * i), 425, (int) (170 - 9.5 * i), 600);
        }
        // clouds left
        d.setColor(new Color(216, 218, 208));
        d.fillCircle(97, 430, 22);
        d.fillCircle(118, 448, 26);
        d.setColor(new Color(198, 200, 191));
        d.fillCircle(130, 415, 26);
        d.setColor(new Color(179, 181, 173));
        d.fillCircle(153, 450, 23);
        d.fillCircle(170, 425, 30);
        // rain right
        d.setColor(Color.WHITE);
        for (int i = 0; i < 10; i++) {
            d.drawLine((int) (700 - 10 * i), 550, (int) (680 - 10 * i), 600);
        }
        // clouds right
        d.setColor(new Color(216, 218, 208));
        d.fillCircle(615, 505, 22);
        d.fillCircle(628, 548, 26);
        d.setColor(new Color(198, 200, 191));
        d.fillCircle(650, 515, 26);
        d.setColor(new Color(179, 181, 173));
        d.fillCircle(668, 533, 23);
        d.fillCircle(690, 525, 30);


    }

    /**
     * notify the sprite that time has passed.
     * @param dt frame rate
     */
    public void timePassed(double dt) {

    }
}
