package levels;

import sprite.Sprite;
import basic.Velocity;
import game.Block;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * a level with 3 balls and many blocks.
 */
public class FinalFour implements LevelInformation {
    // members
    private final int width = 800;

    /**
     * return number of balls.
     *
     * @return number of balls
     */
    public int numberOfBalls() {
        return 3;
    }

    /**
     * The initial velocity of each ball. Note that initialBallVelocities().size() == numberOfBalls().
     *
     * @return initial velocity of each ball
     */
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<Velocity>();
        velocities.add(Velocity.fromAngleAndSpeed(30, 8 * 60));
        velocities.add(Velocity.fromAngleAndSpeed(0, 8 * 60));
        velocities.add(Velocity.fromAngleAndSpeed(-30, 8 * 60));
        return velocities;
    }

    /**
     * return the speed of the paddle.
     *
     * @return speed of the paddle
     */
    public int paddleSpeed() {
        return 8 * 60;
    }

    /**
     * return the paddle width.
     *
     * @return paddle width
     */
    public int paddleWidth() {
        return 100;
    }

    /**
     * 1
     * the level name will be displayed at the top of the screen.
     *
     * @return level name
     */
    public String levelName() {
        return "Final Four";
    }

    /**
     * Returns a sprite with the background of the level.
     *
     * @return background
     */
    public Sprite getBackground() {
        return new FinalFourBackground();
    }

    /**
     * The blocks that make up this level, each block contains its size, color and location.
     *
     * @return blocks that make up this level
     */
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<Block>();
        java.awt.Color[] colorsArr = {Color.GRAY, Color.RED, Color.YELLOW, Color.GREEN, Color.WHITE, Color.PINK,
                Color.CYAN};
        for (int i = 0; i < 7; i++) {
            for (int j = 15; j > 0; j--) {
                blocks.add(new Block(new Rectangle(new Point(50.5 * j - 28.5, 25 * i + 110), 50.5, 25),
                        colorsArr[i], 1));
            }
        }
        /* change the number of hits of the blocks at the top row to 2. */
        for (int i = 0; i < 15; i++) {
            blocks.get(i).setNumOfHitPoints(2);
        }
        return blocks;
    }

    /**
     * Number of blocks that should be removed before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     *
     * @return number of blocks that should be removed
     */
    public int numberOfBlocksToRemove() {
        return 105;
    }
}
