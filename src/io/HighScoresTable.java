package io;

import game.ScoreInfo;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * represent historic high scores.
 */
public class HighScoresTable implements Serializable {
    // members
    private List<ScoreInfo> listScores;
    private int size;
    private HighScoresTable tableScores;

    /**
     * Create an empty high-scores table with the specified size.
     * The size means that the table holds up to size top scores.
     *
     * @param size num of scores
     */
    public HighScoresTable(int size) {
        this.listScores = new ArrayList<>();
        this.size = size;
    }

    /**
     * Add a high-score.
     *
     * @param score score
     */
    public void add(ScoreInfo score) {
        int rankScore = getRank(score.getScore());
        this.listScores.add(rankScore - 1, score);
        if (this.listScores.size() > this.size()) {
            this.listScores.remove(this.size);
        }
    }

    /**
     * Return table size.
     *
     * @return size
     */
    public int size() {
        return this.size;
    }

    /**
     * Return the current high scores.
     * The list is sorted such that the highest scores come first.
     *
     * @return sorted list of high scores
     */
    public List<ScoreInfo> getHighScores() {
        return this.listScores;
    }

    /**
     * return the rank of the current score: where will it be on the list if added?
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not be added to the list.
     *
     * @param score score
     * @return index
     */
    public int getRank(int score) {
        int i;
        for (i = 0; i < this.listScores.size(); i++) {
            if (score > this.listScores.get(i).getScore()) {
                return i + 1;
            }
        }
        return this.getListScores().size() + 1;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        for (int i = 0; i < this.listScores.size(); i++) {
            this.listScores.remove(i);
        }
    }

    /**
     * Load table data from file.
     * Current table data is cleared.
     *
     * @param filename high scores table
     * @throws IOException exception
     */
    public void load(File filename) throws IOException {
        this.tableScores = loadFromFile(filename);
        if (loadFromFile(filename) != null) {
            this.listScores = tableScores.listScores;
        } else {
            throw new IOException("Failed loading file");
        }
    }

    /**
     * Save table data to the specified file.
     *
     * @param filename high scores table
     * @throws IOException exception
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(this);
        } catch (IOException ex) {
            System.err.println("Failed saving object");
            ex.printStackTrace(System.err);
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException ex) {
                System.err.println("Failed closing file: " + filename.getName());
                ex.printStackTrace(System.err);
            }
        }
    }

    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with reading it, an empty table is returned.
     *
     * @param filename high scores table
     * @return exception
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable highScoresTable;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(filename));
            highScoresTable = (HighScoresTable) ois.readObject();
        } catch (FileNotFoundException ex) {
            System.err.println("Unable to find file: " + filename.getName());
            ex.printStackTrace(System.err);
            return null;
        } catch (ClassNotFoundException ex) {
            System.err.println("Unable to find class for object in file: " + filename);
            ex.printStackTrace(System.err);
            return null;
        } catch (IOException ex) {
            System.err.println("Failed reading file: " + filename.getName());
            ex.printStackTrace(System.err);
            return null;
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename.getName());
            }
        }
        return highScoresTable;
    }

    /**
     * return max list scores.
     * @return max list scores
     */
    public List<ScoreInfo> getListScores() {
        return listScores;
    }
}
