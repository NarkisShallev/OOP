package basic;

/**
 * way to tell the menu in advances what we would like to happen when the selection is made,
 * and then, when a user makes a selection, the menu will return to us the action we wanted to happen.
 *
 * @param <T> generic type
 */
public interface Task<T> {
    /**
     * run the task.
     *
     * @return value
     */
    T run();
}
