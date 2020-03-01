package game;

import basic.Counter;
import basic.HitListener;

/**
 * a BlockRemover is in charge of removing blocks from the game, as well as keeping count of the number of blocks
 * that remain.
 */
public class BlockRemover implements HitListener {
    // members
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * constructor.
     *
     * @param game          current game
     * @param removedBlocks removed blocks
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * Blocks that are hit and reach 0 hit-points should be removed from the game.
     *
     * @param beingHit object that was hit
     * @param hitter   the Ball that's doing the hitting
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeFromGame(this.game);
            this.remainingBlocks.decrease(1);
        }
    }
}