/**
 * Small storage class for containing the min, max, and total.
 *
 * @author Cosmo Colman (K21090628)
 * @version 22.03.2022
 */
public class MinMaxTotalStorage {

    private int total;
    private int max;
    private int min;

    public MinMaxTotalStorage() {
        total = 0;
        max = 0;
        min = Integer.MAX_VALUE;
    }

    public void set(int value){
        total += value;
        max = Math.max(value, max);
        min = Math.min(value, min);
    }

    public int total() {
        return total;
    }
    public int max() {
        return max;
    }
    public int min() {
        return min;
    }
}
