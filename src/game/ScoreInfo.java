package game;

import java.io.Serializable;

/**
 * Information about scores.
 */
public class ScoreInfo implements Serializable {
    // members
    private String name;
    private int score;

    /**
     * constructor.
     *
     * @param name  name of player
     * @param score score
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * return name's player.
     *
     * @return name's player
     */
    public String getName() {
        return this.name;
    }

    /**
     * return the score of the player.
     *
     * @return score
     */
    public int getScore() {
        return this.score;
    }
}
