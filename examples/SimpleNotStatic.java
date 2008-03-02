
import java.util.Arrays;

/**
 * A simple integer test with mapping, filtering, and boxing/unboxing. Same
 * as SimpleInteger, but runs in an instance.
 */
public class SimpleNotStatic {
    public static void main(String[] args) {
        new SimpleNotStatic().run();
    }

    private void run() {
        Iterable<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        list = [x*x for int x : list if x >= 5];

        Utils.assertEquals(list, Arrays.asList(25, 36, 49, 64, 81));
    }
}
