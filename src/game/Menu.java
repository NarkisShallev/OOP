package game;

import animation.Animation;

/**
 * generic Menu interface.
 *
 * @param <T> generic type
 */
public interface Menu<T> extends Animation {
    /**
     * A menu with the selections.
     *
     * @param key       key to wait for
     * @param message   line to print
     * @param returnVal what to return
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * return the returnVal.
     *
     * @return returnVal
     */
    T getStatus();

    /**
     * add sub menu.
     *
     * @param key     key
     * @param message message
     * @param subMenu sub menu
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}
