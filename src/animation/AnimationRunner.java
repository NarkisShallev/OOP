package animation;

import biuoop.GUI;
import biuoop.DrawSurface;

/**
 * The AnimationRunner takes an Animation object and runs it.
 */
public class AnimationRunner {
    // members
    private GUI gui;
    private int framesPerSecond;

    /**
     * constructor.
     *
     * @param gui             gui
     * @param framesPerSecond frames per second
     */
    public AnimationRunner(GUI gui, int framesPerSecond) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
    }

    /**
     * runs the animation.
     *
     * @param animation animation
     */
    public void run(Animation animation) {
        biuoop.Sleeper sleeper = new biuoop.Sleeper(); // creates a sleeper
        long millisecondsPerFrame = (long) (1000 / this.framesPerSecond);
        /* A loop that activates the animation. */
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d, (double) millisecondsPerFrame / 1000);
            gui.show(d);
            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * return GUI.
     *
     * @return current GUI
     */
    public GUI getGui() {
        return this.gui;
    }
}