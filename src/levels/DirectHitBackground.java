package levels;

import sprite.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * background for DirectHit level.
 */
public class DirectHitBackground implements Sprite {
    // members
    private final int width = 800;

    /**
     * draw the sprite to the screen.
     *
     * @param d surface
     */
    public void drawOn(DrawSurface d) {
        // black background
        d.setColor(Color.BLACK);
        d.fillRectangle(20, 40, 760, 560);
        // blue lines
        d.setColor(Color.BLUE);
        d.drawLine(this.width / 2, 40, this.width / 2, 135);
        d.drawLine(this.width / 2, 175, this.width / 2, 295);
        d.drawLine((this.width / 2) - 140, 155, (this.width / 2) - 20, 155);
        d.drawLine((this.width / 2) + 20, 155, (this.width / 2) + 140, 155);
        // blue circles
        d.drawCircle(this.width / 2, 155, 60);
        d.drawCircle(this.width / 2, 155, 90);
        d.drawCircle(this.width / 2, 155, 120);
    }

    /**
     * notify the sprite that time has passed.
     * @param dt frame rate
     */
    public void timePassed(double dt) {

    }
}
