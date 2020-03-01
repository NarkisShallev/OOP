package animation;

import biuoop.DrawSurface;

/**
 * Animation interface.
 */
public interface Animation {
    /**
     * doOneFrame(DrawSurface) is in charge of the logic.
     *
     * @param d drawSurface
     * @param dt frame rate
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * shouldStop() is in charge of stopping condition.
     *
     * @return T or F
     */
    boolean shouldStop();
}