
import java.util.Arrays;

/**
 * Comprehension expressions can access local (stack) variables. This
 * is currently not supported, so this test will fail to compile.
 */
public class FreeLocal {
    public static void main(String[] args) {
        int a = 2;
        int b = 5;

        Iterable<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        list = [x*x*a for int x : list if x >= b];

        Utils.assertEquals(list, Arrays.asList(50, 72, 98, 128, 162));
    }
}
