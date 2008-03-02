
import java.util.Arrays;

/**
 * Comprehension expressions can access local (stack) variables. Same
 * as FreeLocal, but run from an instance.
 */
public class FreeLocalNotStatic {
    public static void main(String[] args) {
        new FreeLocalNotStatic().run();
    }

    private void run() {
        final int aaa = getAaa();
        final int bbb = getBbb();

        Iterable<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        list = [x*x*aaa for int x : list if x >= bbb];

        Utils.assertEquals(list, Arrays.asList(50, 72, 98, 128, 162));
    }

    int getAaa() {
        return 2;
    }

    int getBbb() {
        return 5;
    }
}
