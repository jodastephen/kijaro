
import java.util.Arrays;

/**
 * Comprehensions can take arrays as input, just like the Foreach loop.
 * Note that this is currently not supported, so this test will fail.
 */
public class ArrayInput {
    public static void main(String[] args) {
        int[] intArray = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Iterable<Integer> list = [x*x for int x : intArray if x >= 5];

        Utils.assertEquals(list, Arrays.asList(25, 36, 49, 64, 81));
    }
}

