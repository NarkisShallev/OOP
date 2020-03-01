package io;

import game.BlocksDefinitionReader;
import game.BlocksFromSymbolsFactory;
import game.ColorsParser;
import levels.LevelInformationImp;
import sprite.ColorBackground;
import sprite.ImageBackground;
import sprite.Sprite;
import basic.Velocity;
import game.Block;
import levels.LevelInformation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * read from a file and return a list of level information.
 */
public class LevelSpecificationReader {
    // members;
    private List<LevelInformation> levels = new ArrayList<>();
    private List<Velocity> velocities = new ArrayList<>();
    private int paddleSpeed;
    private int paddleWidth;
    private String levelName;
    private List<Block> blocks = new ArrayList<>();
    private int numOfBlocks;
    private Sprite background;
    private int numberOfBalls;
    private Map<String, String> levelDetails = new HashMap<>();
    private List<String> blockDetails = new ArrayList<>();

    /**
     * get a file name and returns a list of LevelInformation objects.
     *
     * @param reader file reader
     * @return list of LevelInformation objects
     * @throws IOException IOException
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) throws IOException {
        BufferedReader br = null;
        List<List<String>> levelsList = new ArrayList<>();
        List<String> currentLevel = new ArrayList<>();
        Boolean isReadingLevel = false;
        Boolean isReadingBlocks = false;
        Integer currentLevelRowHeight;
        Integer currentLevelBlocksStartX;
        Integer currentLevelBlocksStartY;
        LevelSpecificationReader levelSpecificationReader = new LevelSpecificationReader();
        BlocksFromSymbolsFactory blocksFactory;
        ColorsParser colorsParser = new ColorsParser();
        // read the file and split it to levels list
        try {
            br = new BufferedReader(reader);
            String line = br.readLine();
            while (line != null) {
                // skips spaces
                line = line.trim();
                // comments or blanks lines are ignored.
                if ((!line.equals("")) && (!line.startsWith("#"))) {
                    // if isReadingLevel == false -> get in
                    if (!isReadingLevel) {
                        if ("START_LEVEL".equals(line)) {
                            currentLevel = new ArrayList<>();
                            isReadingLevel = true;
                        } else {
                            throw new RuntimeException("Problem with the format of line: " + line);
                        }
                    } else {
                        if (("START_LEVEL".equals(line))) {
                            throw new RuntimeException("Problem with the format of line: " + line);
                        } else if ("END_LEVEL".equals(line)) {
                            levelsList.add(currentLevel);
                            isReadingLevel = false;
                        } else {
                            currentLevel.add(line);
                        }
                    }
                }
                line = br.readLine();
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
        // split the levels list to map details and list of blocks information
        for (int i = 0; i < levelsList.size(); i++) {
            for (int j = 0; j < levelsList.get(i).size(); j++) {
                List<String> level = levelsList.get(i);
                // if isReadingBlocks == false -> get in
                if (!isReadingBlocks) {
                    if ("START_BLOCKS".equals(level.get(j))) {
                        isReadingBlocks = true;
                    } else if ("END_BLOCKS".equals(level.get(j))) {
                        throw new RuntimeException("Problem with the format of line: " + level.get(j));
                    } else {
                        this.levelDetails.putAll(levelSpecificationReader.mapFromString(level.get(j)));
                    }
                } else {
                    if ("START_BLOCKS".equals(level.get(j))) {
                        throw new RuntimeException("Problem with the format of line: " + level.get(j));
                    } else if ("END_BLOCKS".equals(level.get(j))) {
                        isReadingBlocks = false;
                    } else {
                        this.blockDetails.add(level.get(j));
                    }
                }
            }
            // check if all the details exist
            if ((!levelDetails.containsKey("level_name")) || (!levelDetails.containsKey("ball_velocities"))
                    || (!levelDetails.containsKey("paddle_speed")) || (!levelDetails.containsKey("paddle_width"))
                    || (!levelDetails.containsKey("blocks_start_x"))
                    || (!levelDetails.containsKey("blocks_start_y")) || (!levelDetails.containsKey("row_height"))
                    || (!levelDetails.containsKey("num_blocks")) || (!levelDetails.containsKey("background"))
                    || (!levelDetails.containsKey("block_definitions"))) {
                throw new RuntimeException("Problem with the format of the lines");
            }
            // saves the values into variables
            this.levelName = levelDetails.get("level_name");
            this.velocities = levelSpecificationReader.listVelocitiesFromString(levelDetails.get("ball_velocities"));
            this.numberOfBalls = this.velocities.size();
            this.paddleSpeed = Integer.parseInt(levelDetails.get("paddle_speed"));
            this.paddleWidth = Integer.parseInt(levelDetails.get("paddle_width"));
            currentLevelBlocksStartX = Integer.parseInt(levelDetails.get("blocks_start_x"));
            currentLevelBlocksStartY = Integer.parseInt(levelDetails.get("blocks_start_y"));
            currentLevelRowHeight = Integer.parseInt(levelDetails.get("row_height"));
            this.numOfBlocks = Integer.parseInt(levelDetails.get("num_blocks"));
            if ((this.levelDetails.get("background").startsWith("color(")) && (this.levelDetails.
                    get("background").endsWith(")"))) {
                this.background = new ColorBackground(colorsParser.colorFromString(this.levelDetails.
                        get("background")));
            } else if ((this.levelDetails.get("background").startsWith("image(")) && (this.levelDetails.
                    get("background").endsWith(")"))) {
                String name = this.levelDetails.get("background").substring("image(".length(),
                        this.levelDetails.get("background").length() - ")".length());
                InputStream is = null;
                BufferedImage image = null;
                try {
                    is = ClassLoader.getSystemClassLoader().getResourceAsStream(name);
                    image = ImageIO.read(is);
                    this.background = new ImageBackground(image);
                } catch (IOException ex) {
                    throw new RuntimeException("Failed loading image");
                } finally {
                    if (is != null) {
                        is.close();
                    }
                }
            }
            String levelsPath = levelDetails.get("block_definitions");
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(levelsPath);
            blocksFactory = BlocksDefinitionReader.fromReader(new InputStreamReader(is));
            this.blocks = blocksCreator(blockDetails, blocksFactory, currentLevelBlocksStartX, currentLevelBlocksStartY,
                    currentLevelRowHeight);
            // create a level and add it to the list
            LevelInformationImp level = new LevelInformationImp(this.numberOfBalls, this.velocities, this.paddleSpeed,
                    this.paddleWidth, this.levelName, this.background, this.blocks);
            levels.add(level);
            this.levelDetails = new HashMap<>();
            this.blockDetails = new ArrayList<>();
        }
        return levels;
    }

    /**
     * split the line to map details.
     *
     * @param line line
     * @return map
     */
    public Map<String, String> mapFromString(String line) {
        Map<String, String> map = new HashMap<>();
        String[] keyAndValue = line.split(":");
        if (keyAndValue.length != 2) {
            throw new RuntimeException("Problem with the format of line: " + line);
        }
        map.put(keyAndValue[0], keyAndValue[1]);
        return map;
    }

    /**
     * convert string to velocities' list.
     *
     * @param line line
     * @return velocities' list
     */
    public List<Velocity> listVelocitiesFromString(String line) {
        List<Velocity> velocitiesList = new ArrayList<>();
        String[] pairs = line.split(" ");
        for (int i = 0; i < pairs.length; i++) {
            String[] speedAndAngle = pairs[i].split(",");
            velocitiesList.add(Velocity.fromAngleAndSpeed(Integer.parseInt(speedAndAngle[0]),
                    Integer.parseInt(speedAndAngle[1])));
        }
        return velocitiesList;
    }

    /**
     * create real blocks.
     *
     * @param blockDetailsList blockDetailsList
     * @param blocksFactory    blocksFactory
     * @param xpos             xpos
     * @param ypos             ypos
     * @param rowHeight        rowHeight
     * @return block
     */
    public List<Block> blocksCreator(List<String> blockDetailsList, BlocksFromSymbolsFactory blocksFactory,
                                     int xpos, int ypos, int rowHeight) {
        List<Block> blocksList = new ArrayList<>();
        int width = 0;
        int height = 0;
        for (int i = 0; i < blockDetailsList.size(); i++) {
            for (int j = 0; j < blockDetailsList.get(i).length(); j++) {
                String oneChar = blockDetailsList.get(i).substring(j, j + 1);
                if (blocksFactory.isSpaceSymbol(oneChar)) {
                    if (blocksFactory.getSpaceWidth(oneChar) == -1) {
                        throw new RuntimeException("Problem with finding the width");
                    }
                    width += blocksFactory.getSpaceWidth(oneChar);
                } else if (blocksFactory.isBlockSymbol(oneChar)) {
                    Block block = blocksFactory.getBlock(oneChar, xpos + width, ypos + height);
                    blocksList.add(block);
                    if (blocksFactory.getBlockWidth(oneChar) == -1) {
                        throw new RuntimeException("Problem with finding the width");
                    }
                    width += blocksFactory.getBlockWidth(oneChar);
                }
            }
            width = 0;
            height += rowHeight;
        }
        return blocksList;
    }
}

