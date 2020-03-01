package game;

/**
 * a factory-object that is used for creating blocks.
 */
public interface BlockCreator {
    /**
     * Create a block at the specified location.
     *
     * @param xpos x position
     * @param ypos y position
     * @return new block
     */
    Block create(int xpos, int ypos);

    /**
     * get width.
     * @return width
     */
    int getWidth();
}
