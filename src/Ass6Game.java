import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import animation.MenuAnimation;
import basic.Task;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import io.HighScoresTable;
import io.LevelSets;
import io.LevelSpecificationReader;
import io.OneLevelSet;
import levels.LevelInformation;
import animation.AnimationRunner;
import game.GameFlow;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * classname: Ass5Game
 * The main.
 *
 * @author Narkis Kremizi
 * @version 1.0 19/04/2018
 */
public class Ass6Game {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int FRAME_RATE = 60;

    /**
     * Create a game object, initializes and runs it.
     *
     * @param args nothing
     */
    public static void main(String[] args) {
        // create new scores table
        HighScoresTable tableScores = new HighScoresTable(10);
        File file = new File("highscores.txt");
        // load the table if its exists
        if ((file.exists()) && (!file.isDirectory())) {
            try {
                tableScores.load(file);
            } catch (IOException ex) {
                System.err.println("Failed loading file");
                ex.printStackTrace(System.err);
            }
        } else {
            // else, create new file
            try {
                tableScores.save(file);
            } catch (IOException ex) {
                System.err.println("Failed saving object");
                ex.printStackTrace(System.err);
            }
        }
        // Create a window with the title "New Game" which is 800 pixels wide and 600 pixels high
        GUI gui = new GUI("Game", WIDTH, HEIGHT);
        AnimationRunner runner = new AnimationRunner(gui, FRAME_RATE);
        /* create the levels. */
        LevelSets levelSets = new LevelSets();
        List<OneLevelSet> oneLevelSetList;
        String levelSetsPath = "level_sets.txt";
        // if there is something in args
        if (args.length > 0) {
            levelSetsPath = args[0];
        }
        // create oneLevelSetList
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(levelSetsPath);
            oneLevelSetList = levelSets.fromReader(new InputStreamReader(is));
        } catch (IOException ex) {
            throw new RuntimeException("Failed loading level sets");
        }
        while (true) {
            // passes over the oneLevelSetList and add selections to levelSetsMenu, run levelSetsMenu
            MenuAnimation<Task<Void>> levelSetsMenu = new MenuAnimation<>("Level Sets", runner,
                    gui.getKeyboardSensor());
            for (int i = 0; i < oneLevelSetList.size(); i++) {
                LevelSpecificationReader levelSpecificationReader = new LevelSpecificationReader();
                int finalI = i;
                levelSetsMenu.addSelection(oneLevelSetList.get(i).getKey(), oneLevelSetList.get(i).getMessage(),
                        new Task<Void>() {
                    public Void run() {
                        GameFlow game = new GameFlow(runner, gui.getKeyboardSensor(), file, tableScores);
                        List<LevelInformation> levels;
                        try {
                            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(oneLevelSetList.
                                    get(finalI).getPath());
                            levels = levelSpecificationReader.fromReader(new InputStreamReader(is));
                        } catch (IOException ex) {
                            throw new RuntimeException("Failed loading levels");
                        }
                        game.runLevels(levels);
                        return null;
                    }
                });
            }
            // menu - do something according to status: play a game, show high score, or quit
            MenuAnimation<Task<Void>> menu = new MenuAnimation<>("Arkanoid", runner, gui.getKeyboardSensor());
            menu.addSubMenu("s", "Start Game", levelSetsMenu);
            menu.addSelection("h", "High Scores", new Task<Void>() {
                public Void run() {
                    HighScoresAnimation highScoresAnimation = new HighScoresAnimation(gui.getKeyboardSensor(),
                            tableScores);
                    KeyPressStoppableAnimation keyPressScores = new KeyPressStoppableAnimation(gui.getKeyboardSensor(),
                            KeyboardSensor.SPACE_KEY, highScoresAnimation);
                    runner.run(keyPressScores);
                    return null;
                }
            });
            menu.addSelection("q", "Quit", new Task<Void>() {
                public Void run() {
                    System.exit(-1);
                    return null;
                }
            });
            runner.run(menu);
            Task<Void> task = menu.getStatus();
            task.run();
            menu.setStatus(null);
            menu.setStop(false);

        }
    }
}

