package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.Menu;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * display the menu on screen.
 *
 * @param <T> generic type
 */
public class MenuAnimation<T> implements Menu<T> {
    // members
    private String title;
    private List<String> keysList;
    private List<String> messagesList;
    private List<T> returnValuesList;
    private List<Menu<T>> subMenus;
    private T status;
    private AnimationRunner runner;
    private KeyboardSensor keyboard;
    private boolean stop;
    private List<Boolean> isSubMenu;

    /**
     * constructor.
     *
     * @param title    title
     * @param runner   runner
     * @param keyboard keyboard
     */
    public MenuAnimation(String title, AnimationRunner runner, KeyboardSensor keyboard) {
        this.title = title;
        this.keysList = new ArrayList<>();
        this.messagesList = new ArrayList<>();
        this.returnValuesList = new ArrayList<>();
        this.subMenus = new ArrayList<>();
        this.status = null;
        this.runner = runner;
        this.keyboard = keyboard;
        this.stop = false;
        this.isSubMenu = new ArrayList<>();
    }

    /**
     * A menu with the selections.
     *
     * @param key       key to wait for
     * @param message   line to print
     * @param returnValue what to return
     */
    public void addSelection(String key, String message, T returnValue) {
        this.keysList.add(key);
        this.messagesList.add(message);
        this.returnValuesList.add(returnValue);
        this.subMenus.add(null);
        this.isSubMenu.add(false);
    }

    /**
     * return the returnVal.
     *
     * @return returnVal
     */
    public T getStatus() {
        return this.status;
    }

    /**
     * doOneFrame(DrawSurface) is in charge of the logic.
     *
     * @param d  drawSurface
     * @param dt frame rate
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.YELLOW.darker());
        d.drawText(50, 50, this.title, 35);
        d.setColor(Color.GREEN.darker());
        for (int i = 0; i < this.messagesList.size(); ++i) {
            d.drawText(90, 120 + i * 30, "(" + this.keysList.get(i) + ") " + this.messagesList.get(i), 25);
        }
        for (int i = 0; i < this.returnValuesList.size(); i++) {
            if (this.keyboard.isPressed(this.keysList.get(i))) {
                if (this.isSubMenu.get(i)) {
                    this.runner.run(this.subMenus.get(i));
                    this.status = this.subMenus.get(i).getStatus();
                    this.stop = true;
                } else {
                    this.status = this.returnValuesList.get(i);
                    this.stop = true;
                }
                break;
            }
        }
    }

    /**
     * shouldStop() is in charge of stopping condition.
     *
     * @return T or F
     */
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     * set status.
     *
     * @param newStatus status
     */
    public void setStatus(T newStatus) {
        this.status = newStatus;
    }

    /**
     * set stop.
     *
     * @param newStop stop
     */
    public void setStop(boolean newStop) {
        this.stop = newStop;
    }

    /**
     * add sub menu.
     *
     * @param key     key
     * @param message message
     * @param subMenu sub menu
     */
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.keysList.add(key);
        this.messagesList.add(message);
        this.subMenus.add(subMenu);
        this.returnValuesList.add(null);
        this.isSubMenu.add(true);
    }
}