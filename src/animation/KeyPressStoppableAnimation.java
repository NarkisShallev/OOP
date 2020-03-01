package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * wrap an existing animation and add a "waiting-for-key" behavior to it.
 */
public class KeyPressStoppableAnimation implements Animation {
    // members
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**
     * constructor.
     *
     * @param sensor    KeyboardSensor
     * @param key       key
     * @param animation animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    /**
     * doOneFrame(DrawSurface) is in charge of the logic.
     *
     * @param d  drawSurface
     * @param dt frame rate
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.sensor.isPressed(this.key)) {
            if (this.isAlreadyPressed) {
                return;
            }
            this.stop = true;
        }
        isAlreadyPressed = false;
        this.animation.doOneFrame(d, dt);
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
