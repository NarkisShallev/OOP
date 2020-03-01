package levels;

import basic.Velocity;
import game.Block;
import sprite.Sprite;

import java.util.List;

/**
 * create a LevelInformation.
 */
public class LevelInformationImp implements LevelInformation {
    // members
    private int numberOfBalls;
    private List<Velocity> initialBallVelocities;
    private int paddleSpeed;
    private int paddleWidth;
    private String levelName;
    private Sprite background;
    private List<Block> blocks;
    private int numberOfBlocksToRemove;

    /**
     * constructor.
     *
     * @param numberOfBalls         number of balls
     * @param initialBallVelocities initial ball velocities
     * @param paddleSpeed           paddle speed
     * @param paddleWidth           paddle width
     * @param levelName             level name
     * @param background            background
     * @param blocks                blocks
     */
    public LevelInformationImp(int numberOfBalls, List<Velocity> initialBallVelocities, int paddleSpeed,
                               int paddleWidth, String levelName, Sprite background, List<Block> blocks) {
        this.numberOfBalls = numberOfBalls;
        this.initialBallVelocities = initialBallVelocities;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.levelName = levelName;
        this.background = background;
        this.blocks = blocks;
        this.numberOfBlocksToRemove = this.blocks.size();
    }

    /**
     * return number of balls.
     *
     * @return number of balls
     */
    public int numberOfBalls() {
        return this.numberOfBalls;
    }

    /**
     * The initial velocity of each ball. Note that initialBallVelocities().size() == numberOfBalls().
     *
     * @return initial velocity of each ball
     */
    public List<Velocity> initialBallVelocities() {
        return this.initialBallVelocities;
    }

    /**
     * return the speed of the paddle.
     *
     * @return speed of the paddle.
     */
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * return the paddle width.
     *
     * @return paddle width
     */
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * the level name will be displayed at the top of the screen.
     *
     * @return level name
     */
    public String levelName() {
        return this.levelName;
    }

    /**
     * Returns a sprite with the background of the level.
     *
     * @return background
     */
    public Sprite getBackground() {
        return this.background;
    }

    /**
     * The blocks that make up this level, each block contains its size, color and location.
     *
     * @return blocks that make up this level
     */
    public List<Block> blocks() {

        return this.blocks;
    }

    /**
     * Number of blocks that should be removed before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     *
     * @return number of blocks that should be removed
     */
    public int numberOfBlocksToRemove() {
        return this.numberOfBlocksToRemove;
    }
}
