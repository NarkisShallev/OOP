package levels;

import sprite.Sprite;
import basic.Velocity;
import game.Block;

import java.util.List;

/**
 * The LevelInformation interface specifies the information required to fully describe a level.
 */
public interface LevelInformation {
    /**
     * return number of balls.
     *
     * @return number of balls
     */
    int numberOfBalls();

    /**
     * The initial velocity of each ball. Note that initialBallVelocities().size() == numberOfBalls().
     *
     * @return initial velocity of each ball
     */
    List<Velocity> initialBallVelocities();

    /**
     * return the speed of the paddle.
     *
     * @return speed of the paddle.
     */
    int paddleSpeed();

    /**
     * return the paddle width.
     *
     * @return paddle width
     */
    int paddleWidth();

    /**
     * the level name will be displayed at the top of the screen.
     *
     * @return level name
     */
    String levelName();

    /**
     * Returns a sprite with the background of the level.
     *
     * @return background
     */
    Sprite getBackground();

    /**
     * The blocks that make up this level, each block contains its size, color and location.
     *
     * @return blocks that make up this level
     */
    List<Block> blocks();

    /**
     * Number of blocks that should be removed before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     *
     * @return number of blocks that should be removed
     */
    int numberOfBlocksToRemove();
}