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
