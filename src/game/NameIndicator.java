package game;

import sprite.Sprite;
import biuoop.DrawSurface;
import levels.LevelInformation;

import java.awt.Color;

/**
 * Displaying the name of the level.
 */
public class NameIndicator implements Sprite {
    // members
    private LevelInformation level;

    /**
     * constructor.
     *
     * @param level current level
     */
    public NameIndicator(LevelInformation level) {
        this.level = level;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d surface
     */
    public void drawOn(DrawSurface d) {
        // name of level
        d.setColor(Color.BLACK);
        d.drawText(550, 15, "Level Name:" + this.level.levelName() + "", 15);
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