package basic;

/**
 * Counter is a simple class that is used for counting things.
 */
public class Counter {
    private int count = 0;

    /**
     * add number to current count.
     *
     * @param number number
     */
    public void increase(int number) {
        this.count = this.count + number;
    }

    /**
     * subtract number from current count.
     *
     * @param number number
     */
    public void decrease(int number) {
        this.count = this.count - number;
    }

    /**
     * get current count.
     *
     * @return count
     */
    public int getValue() {
        return this.count;
    }
}