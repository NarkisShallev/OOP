package animation;

import basic.Counter;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

/**
 * end screen.
 */
public class EndScreen implements Animation {
    // members
    private KeyboardSensor keyboard;
    private Counter score;
    private Counter numberOfLives;
    private boolean stop;

    /**
     * constructor.
     *
     * @param k             KeyboardSensor
     * @param score         score
     * @param numberOfLives number of lives
     */
    public EndScreen(KeyboardSensor k, Counter score, Counter numberOfLives) {
        this.keyboard = k;
        this.stop = false;
        this.score = score;
        this.numberOfLives = numberOfLives;
    }

    /**
     * doOneFrame(DrawSurface) is in charge of the logic.
     *
     * @param d  drawSurface
     * @param dt frame rate
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.BLACK);
        if (numberOfLives.getValue() == 0) {
            d.drawText(160, 160, "Game Over. Your score is " + score.getValue(), 35);
        }
        if (numberOfLives.getValue() > 0) {
            d.drawText(160, 160, "You Win! Your score is " + score.getValue(), 35);
        }
        d.drawText(200, 460, "press space to continue", 35);
    }

    /**
     * shouldStop() is in charge of stopping condition.
     *
     * @return T or F
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
