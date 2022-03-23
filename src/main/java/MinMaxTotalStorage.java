/**
 * Small storage class for containing the min, max, and total.
 * When setting the min or max, it will only set for max if the
 * value is more than the current max, and will only set for min
 * if the value is le less than the min.
 *
 * @author Cosmo Colman (K21090628)
 * @version 22.03.2022
 */
public class MinMaxTotalStorage {

    private int total;
    private int max;
    private int min;

    /**
     * Constructor for the storage class. Assigns 0 to min and the largest integer value to max.
     */
    public MinMaxTotalStorage() {
        total = 0;
        max = 0;
        min = Integer.MAX_VALUE;
    }

    /**
     * Set a new value to the object, if the value is less than the current min it
     * will replace it, and if the value is larger than the current max it will replace it.
     * And regardless it will be added to the total.
     * @param value Value to be set.
     */
    public void set(int value){
        total += value;
        max = Math.max(value, max);
        min = Math.min(value, min);
    }

    /**
     * Get the total of all set values.
     * @return The total of all set values.
     */
    public int total() {
        return total;
    }

    /**
     * Get the largest of all set values.
     * @return The largest of all set values.
     */
    public int max() {
        return max;
    }

    /**
     * Get the smallest of all set values.
     * @return The smallest of all set values.
     */
    public int min() {
        return min;
    }
}
