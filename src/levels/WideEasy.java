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
 * an eazy level with 10 balls and wide paddle.
 */
public class WideEasy implements LevelInformation {
    // members
    private final int width = 800;

    /**
     * return number of balls.
     *
     * @return number of balls
     */
    public int numberOfBalls() {
        return 10;
    }

    /**
     * The initial velocity of each ball. Note that initialBallVelocities().size() == numberOfBalls().
     *
     * @return initial velocity of each ball
     */
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<Velocity>();
        for (int i = 0; i < 5; i++) {
            velocities.add(Velocity.fromAngleAndSpeed(-50 + 10 * i, 8 * 60));
        }
        for (int i = 0; i < 5; i++) {
            velocities.add(Velocity.fromAngleAndSpeed(10 + 10 * i, 8 * 60));
        }
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
        return 640;
    }

    /**
     * 1
     * the level name will be displayed at the top of the screen.
     *
     * @return level name
     */
    public String levelName() {
        return "Wide Eazy";
    }

    /**
     * Returns a sprite with the background of the level.
     *
     * @return background
     */
    public Sprite getBackground() {
        return new WideEasyBackground();
    }

    /**
     * The blocks that make up this level, each block contains its size, color and location.
     *
     * @return blocks that make up this level
     */
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<Block>();
        java.awt.Color[] colorsArr = {Color.CYAN, Color.PINK, Color.BLUE, Color.GREEN, Color.YELLOW,
                new Color(255, 242, 60), Color.RED};
        for (int i = 15; i > 0; i--) {
            blocks.add(new Block(new Rectangle(new Point(50.5 * i - 28, 277), 50.5, 25),
                    Color.BLACK, 1));
        }
        // set the color of the blocks
        for (int i = 0; i < 2; i++) {
            blocks.get(i).setFillColor(colorsArr[0]);
        }
        for (int i = 0; i < 2; i++) {
            blocks.get(i + 2).setFillColor(colorsArr[1]);
            blocks.get(i + 4).setFillColor(colorsArr[2]);
            blocks.get(i + 9).setFillColor(colorsArr[4]);
            blocks.get(i + 11).setFillColor(colorsArr[5]);
            blocks.get(i + 13).setFillColor(colorsArr[6]);
        }
        for (int i = 0; i < 3; i++) {
            blocks.get(i + 6).setFillColor(colorsArr[3]);
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
        return 15;
    }
}
