package animation;

import biuoop.KeyboardSensor;
import biuoop.DrawSurface;

/**
 * simple animation that will display a screen with the message paused -- press space to continue until
 * a key is pressed.
 */
public class PauseScreen implements Animation {
    // members
    private KeyboardSensor keyboard;
    private boolean stop;

    /**
     * constructor.
     *
     * @param k KeyboardSensor
     */
    public PauseScreen(KeyboardSensor k) {
        this.keyboard = k;
        this.stop = false;
    }

    /**
     * doOneFrame(DrawSurface) is in charge of the logic.
     *
     * @param d drawSurface
     * @param dt frame rate
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText(160, d.getHeight() / 2, "paused -- press space to continue", 35);
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.stop = true;
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