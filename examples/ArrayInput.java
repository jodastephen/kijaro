
import java.util.Arrays;

/**
 * Comprehensions can take arrays as input, just like the Foreach loop.
 */
public class ArrayInput {
    public static void main(String[] args) {
        float[] array = new float[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Iterable<Double> list = [Math.pow(x, 2) for float x : array if x >= 5];

        Utils.assertEquals(list, Arrays.asList(25d, 36d, 49d, 64d, 81d));
    }
}

