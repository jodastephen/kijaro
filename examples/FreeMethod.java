
import java.util.Arrays;

/**
 * Comprehension expressions can access methods. This is currently not
 * supported, so this test will fail to compile.
 */
public class FreeMethod {
    public static void main(String[] args) {
        new FreeMethod().run();
    }

    private void run() {
        Iterable<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        list = [x*x*getA() for int x : list if x >= getB()];

        Utils.assertEquals(list, Arrays.asList(50, 72, 98, 128, 162));
    }

    protected int getA() {
        return 2;
    }

    protected int getB() {
        return 5;
    }
}
