package animation;

import basic.Counter;
import sprite.SpriteCollection;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The CountdownAnimation will display the given gameScreen, for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will appear on the screen for (numOfSeconds / countFrom)
 * seconds, before it is replaced with the next one.
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private boolean stop;
    private Counter counter = new Counter();
    private long startMillisecond;

    /**
     * constructor.
     *
     * @param numOfSeconds number of seconds
     * @param countFrom    count from
     * @param gameScreen   game screen
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds * 1000;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.stop = false;
        this.counter.increase(this.countFrom + 1);
    }

    /**
     * doOneFrame(DrawSurface) is in charge of the logic.
     *
     * @param d drawSurface
     * @param dt seconds
     */
    public void doOneFrame(DrawSurface d, double dt) {
        double numOfMillisecondPerText = this.numOfSeconds / (this.countFrom + 1);
        long currentMillisecond = System.currentTimeMillis() - this.startMillisecond;
        /* Draw all the sprites on the screen and notify them about the time. */
        this.gameScreen.drawAllOn(d);
        if (counter.getValue() == 0) {
            this.stop = true;
        }
        d.setColor(Color.WHITE);
        d.drawText(d.getWidth() / 2 - 7, d.getHeight() / 2, "" + (this.counter.getValue()) + "", 35);
        if (counter.getValue() == this.countFrom + 1) {
            this.startMillisecond = System.currentTimeMillis();
        }
        if (currentMillisecond > numOfMillisecondPerText) {
            this.counter.decrease(1);
            this.startMillisecond = System.currentTimeMillis();
        }
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