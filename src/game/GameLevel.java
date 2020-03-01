package game;

import animation.PauseScreen;
import animation.KeyPressStoppableAnimation;
import animation.Animation;
import animation.AnimationRunner;
import animation.CountdownAnimation;
import basic.Collidable;
import basic.Counter;
import sprite.Sprite;
import sprite.SpriteCollection;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Point;
import geometry.Rectangle;
import levels.LevelInformation;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

/**
 * classname: Game
 * Holds the sprites and the collidables, and is in charge of the animation.
 *
 * @author Narkis Kremizi
 * @version 1.0 19/04/2018
 */
public class GameLevel implements Animation {
    /* Members. */
    private final int height = 600;
    private final int width = 800;
    private Paddle paddle;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter blocksNum = new Counter();
    private Counter ballsNum = new Counter();
    private Counter score;
    private Counter numberOfLives;
    private BallRemover ballRemoverListener = new BallRemover(this, this.ballsNum);
    private AnimationRunner runner;
    private boolean running;
    private LevelInformation levelInfo;
    private biuoop.KeyboardSensor keyboard;

    /**
     * constructor.
     *
     * @param levelInfo     level info
     * @param keyboard      keyboard
     * @param runner        runner
     * @param score         score
     * @param numberOfLives number of lives
     */
    public GameLevel(LevelInformation levelInfo, biuoop.KeyboardSensor keyboard, AnimationRunner runner, Counter score,
                     Counter numberOfLives) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.levelInfo = levelInfo;
        this.keyboard = keyboard;
        this.runner = runner;
        this.score = score;
        this.numberOfLives = numberOfLives;
    }

    /**
     * add new collitable to the environment.
     *
     * @param c new collidable
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * remove collitable from the environment.
     *
     * @param c collidable
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * add new sprite to the sprite collection.
     *
     * @param s new sprite
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * remove sprite from the sprite collection.
     *
     * @param s sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle) and add them to the game.
     */
    public void initialize() {
        BlockRemover blockRemoverListener = new BlockRemover(this, this.blocksNum);
        ScoreTrackingListener scoreListener = new ScoreTrackingListener(this.score);
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
        LivesIndicator livesIndicator = new LivesIndicator(this.numberOfLives);
        NameIndicator nameIndicator = new NameIndicator(this.levelInfo);
        List<Block> blocks = new ArrayList<>();
        // draw the background
        sprites.addSprite(this.levelInfo.getBackground());
        // creates the paddle and add it to the game
        this.paddle = new Paddle(new Rectangle(new Point(1, 1), this.levelInfo.paddleWidth(), 15), this,
                this.levelInfo.paddleSpeed(), Color.YELLOW);
        paddle.addToGame(this);
        // creates the boundaries
        blocks.add(new Block(new Rectangle(new Point(0, 0), 20, this.height), Color.GRAY,
                0));
        blocks.add(new Block(new Rectangle(new Point(this.width - 20, 0), 20, this.height), Color.GRAY,
                0));
        blocks.add(new Block(new Rectangle(new Point(0, this.height), this.width, 20), Color.GRAY,
                0));
        blocks.add(new Block(new Rectangle(new Point(0, 20), this.width, 20), Color.GRAY,
                0));
        // creates the blocks
        for (int i = 0; i < this.levelInfo.numberOfBlocksToRemove(); i++) {
            blocks.add(this.levelInfo.blocks().get(i));
        }
        // Adds the blocks to the game and register the block remover object as a listener to all the blocks
        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).addToGame(this);
            this.blocksNum.increase(1);
            blocks.get(i).addHitListener(blockRemoverListener);
            blocks.get(i).addHitListener(scoreListener);
        }
        // un-register the block remover object as a listener to the border blocks and remove them from the counter
        for (int i = 0; i < 4; i++) {
            blocks.get(i).removeHitListener(blockRemoverListener);
            this.blocksNum.decrease(1);
            blocks.get(i).removeHitListener(scoreListener);
        }
        // register the BallRemover class as a listener of the death-region and make the block invisible.
        blocks.get(2).addHitListener(ballRemoverListener);
        blocks.get(2).removeFromSprite(this);

        // add the score indicator, the lives indicator and the name indicator to the game.
        scoreIndicator.addToGame(this);
        livesIndicator.addToGame(this);
        nameIndicator.addToGame(this);
    }

    /**
     * Run one turn of the game -- start the animation loop.
     */
    public void playOneTurn() {
        List<Ball> balls = new ArrayList<>();
        // creates balls, set its velocity and adds the balls to the game and to the environment
        for (int i = 0; i < this.levelInfo.numberOfBalls(); i++) {
            balls.add(new Ball(new Point(400, 560), 5, Color.WHITE));
            balls.get(i).setVelocity(this.levelInfo.initialBallVelocities().get(i));
            balls.get(i).setBallGameEnvironment(this.environment);
            balls.get(i).addToGame(this);
            this.ballsNum.increase(1);
            balls.get(i).addHitListener(this.ballRemoverListener);
        }
        // Returns the paddle to the middle
        this.paddle.putPaddleInTheMiddle();
        // countdown before turn starts.
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        // use our runner to run the current animation -- which is one turn of the game.
        this.running = true;
        this.runner.run(this);
    }

    /**
     * doOneFrame(DrawSurface) is in charge of the logic.
     *
     * @param d  drawSurface
     * @param dt frame rate
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // Stops the game if there are no balls or blocks
        if (this.ballsNum.getValue() == 0) {
            this.numberOfLives.decrease(1);
        }
        if ((this.blocksNum.getValue() == 0) || (this.ballsNum.getValue() == 0)) {
            this.running = false;
        }
        // You get 100 points if you're done one level
        if (this.blocksNum.getValue() == 0) {
            this.score.increase(100);
        }
        // pause for the key 'p'
        if (this.keyboard.isPressed("p")) {
            KeyPressStoppableAnimation keyPressPause = new KeyPressStoppableAnimation(this.keyboard,
                    KeyboardSensor.SPACE_KEY, new PauseScreen(this.keyboard));
            this.runner.run(keyPressPause);
        }
        /* Draw all the sprites on the screen and notify them about the time. */
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
    }

    /**
     * shouldStop() is in charge of stopping condition.
     *
     * @return T or F
     */
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * return keyboard.
     *
     * @return keyboard
     */
    public KeyboardSensor getKeyboard() {
        return keyboard;
    }

    /**
     * return number of blocks.
     *
     * @return number of blocks
     */
    public Counter getBlocksNum() {
        return blocksNum;
    }
}
