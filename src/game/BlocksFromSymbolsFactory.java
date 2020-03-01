package game;

import java.util.Map;

/**
 * a mechanism (object) with a method that will get a symbol and create the desired block.
 */
public class BlocksFromSymbolsFactory {
    // members
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * constructor.
     *
     * @param spacerWidths  spacer widths
     * @param blockCreators block creators
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    /**
     * returns true if 's' is a valid space symbol.
     *
     * @param s space symbol
     * @return true if 's' is a valid space symbol
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }

    /**
     * returns true if 's' is a valid block symbol.
     *
     * @param s block symbol
     * @return true if 's' is a valid block symbol
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }

    /**
     * Return a block according to the definitions associated with symbol s. The block will be located at position
     * (xpos, ypos).
     *
     * @param s    symbol
     * @param xpos x position
     * @param ypos y position
     * @return block
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }

    /**
     * get spacer widths.
     *
     * @return spacer widths
     */
    public Map<String, Integer> getSpacerWidths() {
        return spacerWidths;
    }

    /**
     * return the width space.
     *
     * @param s key
     * @return width
     */
    public int getSpaceWidth(String s) {
        if (!this.spacerWidths.containsKey(s)) {
            return -1;
        }
        return this.spacerWidths.get(s);
    }

    /**
     * return the width block.
     *
     * @param s key
     * @return width
     */
    public int getBlockWidth(String s) {
        if (!this.blockCreators.containsKey(s)) {
            return -1;
        }
        return this.blockCreators.get(s).getWidth();
    }
}
