package game;

import basic.Counter;
import sprite.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Displaying a score is achieved by creating a sprite called ScoreIndicator which will be in charge of displaying
 * the current score.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;


    /**
     * constructor.
     *
     * @param score score
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d surface
     */
    public void drawOn(DrawSurface d) {
        /* Set a color */
        d.setColor(Color.BLACK);
        /* draw the frame. */
        d.drawRectangle(0, 0, 800, 20);
        /* Set the color to the fill's color of the block. */
        d.setColor(Color.WHITE);
        /* fill the block. */
        d.fillRectangle(0, 0, 800, 20);
        /* Set the color to the text's color. */
        d.setColor(Color.BLACK);
        /* draw the score. */
        d.drawText(350, 15, ("Score: " + this.score.getValue()), 15);
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
