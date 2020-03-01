package game;

import sprite.Background;
import sprite.ColorBackground;
import sprite.ImageBackground;
import sprite.Sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * read a block-definitions file and return a BlocksFromSymbolsFactory object.
 */
public class BlocksDefinitionReader {
    // members
    private Map<String, Integer> spacerWidths = new HashMap<>();
    private Map<String, BlockCreator> blockCreators = new HashMap<>();
    private ColorsParser colorsParser = new ColorsParser();

    /**
     * reading a block-definitions file and returning a BlocksFromSymbolsFactory object.
     *
     * @param reader file
     * @return BlocksFromSymbolsFactory object
     * @throws IOException IOException
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) throws IOException {
        BufferedReader br = null;
        BlocksDefinitionReader blocksDefinitionReader = new BlocksDefinitionReader();
        try {
            br = new BufferedReader(reader);
            String line = br.readLine();
            Map<String, String> defaultValues = new HashMap<>();
            Map<Integer, Sprite> fills = new HashMap<>();
            Map<String, Map<String, String>> blocks = new HashMap<>();
            while (line != null) {
                // skips spaces
                line = line.trim();
                // comments or blanks lines are ignored.
                if ((!line.equals("")) && (!line.startsWith("#"))) {
                    if (line.startsWith("sdef")) {
                        // lines begin with the token "sdef", followed by a space-separated list of properties.
                        // Each property has the form key:value
                        String newLine = line.substring("sdef".length()).trim();
                        Map<String, String> map = blocksDefinitionReader.mapFromString(newLine);
                        if (map.get("symbol").length() != 1) {
                            throw new RuntimeException("Problem with the format of line: " + line);
                        }
                        String symbol = Character.toString(map.get("symbol").charAt(0));
                        int width = Integer.parseInt(map.get("width"));
                        blocksDefinitionReader.addSpacer(symbol, width);
                    } else if (!line.startsWith("bdef")) {
                        // If a required property is not defined and there is no default for it the parsing process
                        // should fail.
                        if (!line.startsWith("default")) {
                            throw new RuntimeException("Problem with the format of line: " + line);
                        }
                        // there is a default
                        String newLine = line.substring("default".length()).trim();
                        defaultValues = blocksDefinitionReader.mapFromString(newLine);
                    } else {
                        // lines begin with the token bdef, followed by a space-separated list of
                        // properties. Each property has the form key:value.
                        String newLine = line.substring("bdef".length()).trim();
                        Map<String, String> map = blocksDefinitionReader.mapFromString(newLine);
                        if (map.get("symbol").length() != 1) {
                            throw new RuntimeException("Problem with the format of line: " + line);
                        }
                        String symbol = Character.toString(map.get("symbol").charAt(0));
                        blocks.put(symbol, map);
                    }
                }
                line = br.readLine();
            }
            // check if some details need to be completed from the default
            blocksDefinitionReader.compliteDetails(defaultValues, blocks);
            // create blockCreator
            blocksDefinitionReader.fromMapToBlockCreator(blocks);
        } catch (Exception ex) {
            throw new RuntimeException("Problem with the format of line");
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return new BlocksFromSymbolsFactory(blocksDefinitionReader.spacerWidths, blocksDefinitionReader.blockCreators);
    }

    /**
     * create blockCreator from map details with help of createBlockCreator function.
     *
     * @param blocks blocks
     */
    private void fromMapToBlockCreator(Map<String, Map<String, String>> blocks) {
        for (Map.Entry<String, Map<String, String>> blockEntry : blocks.entrySet()) {
            Map<Integer, Background> backgrounds = new HashMap<>();
            for (Map.Entry<String, String> property : blockEntry.getValue().entrySet()) {
                if (property.getKey().startsWith("fill")) {
                    if (property.getKey().startsWith("fill-")) {
                        int hits = Integer.parseInt(property.getKey().substring("fill-".length()));
                        backgrounds.put(hits, fillParse(property.getValue()));
                    } else {
                        backgrounds.put(0, fillParse(property.getValue()));
                    }
                }
            }
            addBlocks(blockEntry.getKey(), createBlockCreator(blockEntry.getValue(), backgrounds));
        }
    }

    /**
     * help with creating the blockCreator (Imp of BlockCreator).
     *
     * @param value       value
     * @param backgrounds backgrounds
     * @return BlockCreator
     */
    private BlockCreator createBlockCreator(Map<String, String> value, Map<Integer, Background> backgrounds) {
        return new BlockCreator() {

            /**
             * create block.
             * @param xpos x position
             * @param ypos y position
             * @return block
             */
            @Override
            public Block create(int xpos, int ypos) {
                Block block = new Block(xpos, ypos);
                block.setNumOfHitPoints(Integer.parseInt(value.get("hit_points")));
                block.setWidth(Integer.parseInt(value.get("width")));
                block.setHeight(Integer.parseInt(value.get("height")));
                if (value.containsKey("stroke")) {
                    block.setFrameColor(colorsParser.colorFromString(value.get("stroke")));
                } else {
                    block.setFrameColor(null);
                }
                block.setFillBackgrounds(backgrounds);
                return block;
            }

            /**
             * get width.
             * @return width
             */
            public int getWidth() {
                return Integer.parseInt(value.get("width"));
            }
        };
    }

    /**
     * check if some details need to be completed from the default.
     *
     * @param defaultValues defaultValues
     * @param blocks        blocks
     */
    private void compliteDetails(Map<String, String> defaultValues, Map<String, Map<String, String>> blocks) {
        for (String key : defaultValues.keySet()) {
            for (Map<String, String> block : blocks.values()) {
                if (!block.containsKey(key)) {
                    block.put(key, defaultValues.get(key));
                }
            }
        }
    }

    /**
     * convert string to background.
     *
     * @param s string
     * @return sprite
     */
    public Background fillParse(String s) {
        Background background = null;
        if ((s.startsWith("color(")) && (s.endsWith(")"))) {
            background = new ColorBackground(colorsParser.colorFromString(s));
        } else if ((s.startsWith("image(")) && (s.endsWith(")"))) {
            String name = s.substring("image(".length(), s.length() - ")".length());
            InputStream is = null;
            BufferedImage image = null;
            try {
                is = ClassLoader.getSystemClassLoader().getResourceAsStream(name);
                image = ImageIO.read(is);
                background = new ImageBackground(image);
                is.close();
            } catch (IOException ex) {
                throw new RuntimeException("Failed loading image");
            }
        }
        return background;
    }

    /**
     * add spacer to the map.
     *
     * @param symbol symbol
     * @param width  width
     */
    public void addSpacer(String symbol, int width) {
        this.spacerWidths.put(symbol, width);
    }

    /**
     * add spacer to the map.
     *
     * @param symbol       symbol
     * @param blockCreator block creator
     */
    public void addBlocks(String symbol, BlockCreator blockCreator) {
        this.blockCreators.put(symbol, blockCreator);
    }

    /**
     * split the line to map details.
     *
     * @param line line
     * @return map
     */
    public Map<String, String> mapFromString(String line) {
        Map<String, String> map = new HashMap<>();
        String[] pairs = line.split(" ");
//        if (pairs.length != 2) {
//            throw new RuntimeException("Problem with the format of line: " + line);
//        }
        for (int i = 0; i < pairs.length; i++) {
            String[] keyAndValue = pairs[i].split(":");
            if (keyAndValue.length != 2) {
                throw new RuntimeException("Problem with the format of line: " + line);
            }
            map.put(keyAndValue[0].trim(), keyAndValue[1].trim());
        }
        return map;
    }
}