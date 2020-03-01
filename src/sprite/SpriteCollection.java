package sprite;

import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;

/**
 * classname: SpriteCollection
 * A list which holds a collection of sprites.
 *
 * @author Narkis Kremizi
 * @version 1.0 19/04/2018
 */
public class SpriteCollection {
    /* Members. */
    private List<Sprite> sprites;

    /**
     * A constructor that initializes the sprite's list.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }

    /**
     * add the given sprite to the list.
     *
     * @param s new sprite
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * remove the given sprite from the list.
     *
     * @param s sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * call timePassed() on all sprites.
     * @param dt frame rate
     */
    public void notifyAllTimePassed(double dt) {
        List<Sprite> tempList = new ArrayList<>(this.sprites);
        for (Sprite current : tempList) {
            current.timePassed(dt);
        }
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d surface
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite current : this.sprites) {
            current.drawOn(d);
        }
    }
}
