package animation;


import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import io.HighScoresTable;

import java.awt.Color;

/**
 * display the scores in the high-scores table, until a specified key is pressed.
 */
public class HighScoresAnimation implements Animation {
    // members
    private KeyboardSensor keyboard;
    private HighScoresTable tableScores;
    private String endKey;
    private boolean stop;

    /**
     * constructor without end key.
     *
     * @param k           keyboardSensor
     * @param tableScores table scores
     */
    public HighScoresAnimation(KeyboardSensor k, HighScoresTable tableScores) {
        this.keyboard = k;
        this.tableScores = tableScores;
        this.stop = false;
    }

    /**
     * constructor with end key.
     *
     * @param k           keyboardSensor
     * @param tableScores table scores
     * @param endKey      end key
     */
    public HighScoresAnimation(KeyboardSensor k, HighScoresTable tableScores, String endKey) {
        this.keyboard = k;
        this.tableScores = tableScores;
        this.endKey = endKey;
        this.stop = false;
    }

    /**
     * doOneFrame(DrawSurface) is in charge of the logic.
     *
     * @param d  drawSurface
     * @param dt frame rate
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.YELLOW.darker());
        d.drawText(50, 50, "High Scores", 35);
        d.setColor(Color.GREEN.darker());
        d.drawText(90, 110, "Name", 25);
        d.drawText(430, 110, "Score", 25);
        d.drawLine(90, 120, 700, 120);
        d.setColor(Color.BLACK);
        for (int i = 0; i < this.tableScores.getListScores().size(); i++) {
            d.drawText(90, 150 + i * 30, this.tableScores.getHighScores().get(i).getName(), 25);
            d.drawText(430, 150 + i * 30, "" + this.tableScores.getHighScores().get(i).getScore(), 25);
        }
        d.drawText(200, 460, "press space to continue", 35);
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
