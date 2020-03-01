package levels;

import sprite.Sprite;
import basic.Velocity;
import game.Block;
import geometry.Point;
import geometry.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

/**
 * a level with one block that the user need to destroy it directly.
 */
public class DirectHit implements LevelInformation {
    // members
    private final int width = 800;

    /**
     * return number of balls.
     *
     * @return number of balls
     */
    public int numberOfBalls() {
        return 1;
    }

    /**
     * The initial velocity of each ball. Note that initialBallVelocities().size() == numberOfBalls().
     *
     * @return initial velocity of each ball
     */
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<Velocity>();
        velocities.add(Velocity.fromAngleAndSpeed(0, 6 * 60));
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
        return 80;
    }

    /**
     * the level name will be displayed at the top of the screen.
     *
     * @return level name
     */
    public String levelName() {
        return "Direct Hit";
    }

    /**
     * Returns a sprite with the background of the level.
     *
     * @return background
     */
    public Sprite getBackground() {
        return new DirectHitBackground();
    }

    /**
     * The blocks that make up this level, each block contains its size, color and location.
     *
     * @return blocks that make up this level
     */
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<Block>();
        blocks.add(new Block(new Rectangle(new Point((this.width / 2) - 15, 140), 30, 30),
                Color.RED, 1));
        return blocks;
    }

    /**
     * Number of blocks that should be removed before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     *
     * @return number of blocks that should be removed
     */
    public int numberOfBlocksToRemove() {
        return 1;
    }
}
