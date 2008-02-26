
import java.util.Arrays;

/**
 * Comprehension expressions can access static member variables.
 */
public class FreeStaticMember {
    private static int a = getA();
    private static int b = getB();

    public static void main(String[] args) {
        Iterable<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        list = [x*x*a for int x : list if x >= b];

        Utils.assertEquals(list, Arrays.asList(50, 72, 98, 128, 162));
    }

    private static int getA() {
        return 2;
    }

    private static int getB() {
        return 5;
    }
}
