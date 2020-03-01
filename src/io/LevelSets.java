package io;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * create level sets.
 */
public class LevelSets {
    private List<OneLevelSet> levelSetList;
    private String key;
    private String message;
    private String path;

    /**
     * read the levelSets file and return levelSets list.
     *
     * @param reader reader
     * @return levelSets list
     * @throws IOException exception
     */
    public List<OneLevelSet> fromReader(Reader reader) throws IOException {
        levelSetList = new ArrayList<>();
        OneLevelSet currentSet = null;
        LineNumberReader lnr = null;
        try {
            lnr = new LineNumberReader(reader);
            String line = lnr.readLine();

            while (line != null) {
                if (lnr.getLineNumber() % 2 == 0) {
                    this.path = line.trim();
                    currentSet = new OneLevelSet(this.key, this.message, this.path);
                    this.addLevelSet(currentSet);
                } else {
                    String[] pair = line.trim().split(":");
                    this.key = pair[0];
                    this.message = pair[1];
                }
                line = lnr.readLine();
            }
        } finally {
            if (lnr != null) {
                lnr.close();
            }
        }
        return this.levelSetList;
    }

    /**
     * add level set to the list.
     *
     * @param levelSet level set
     */
    public void addLevelSet(OneLevelSet levelSet) {
        this.levelSetList.add(levelSet);
    }
}
