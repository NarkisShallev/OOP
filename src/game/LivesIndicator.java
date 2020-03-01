package game;

import basic.Counter;
import sprite.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * sit at the top of the screen and indicate the number of lives.
 */
public class LivesIndicator implements Sprite {
    private Counter lives;

    /**
     * constructor.
     *
     * @param lives lives
     */
    public LivesIndicator(Counter lives) {
        this.lives = lives;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d surface
     */
    public void drawOn(DrawSurface d) {
        // Set the color to the text's color
        d.setColor(Color.BLACK);
        // draw the number of lives
        d.drawText(150, 15, ("Lives: " + this.lives.getValue()), 15);
    }

    /**
     * notify the sprite that time has passed.
     * @param dt frame rate
     */
    public void timePassed(double dt) {
    }

    /**
     * add the indicator to the game as sprite.
     *
     * @param g the current game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
