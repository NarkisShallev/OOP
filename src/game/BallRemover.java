package game;

import basic.Counter;
import basic.HitListener;

/**
 * a BallRemover identify when a ball reaches the bottom of the screen, and remove it from the game.
 */
public class BallRemover implements HitListener {
    // members
    private GameLevel game;
    private Counter remainingBalls;

    /**
     * constructor.
     *
     * @param game         current game
     * @param removedBalls removed blocks
     */
    public BallRemover(GameLevel game, Counter removedBalls) {
        this.game = game;
        this.remainingBalls = removedBalls;
    }

    /**
     * ball that hits should be removed from the game.
     *
     * @param beingHit object that was hit
     * @param hitter   the Ball that's doing the hitting
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.remainingBalls.decrease(1);
    }
}
