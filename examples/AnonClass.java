
import java.util.Arrays;
import java.util.Iterator;

/**
 * Has an anonymous class as a source expression. This implies that
 * the comprehension variable is "final".
 */
public class AnonClass {
    interface MapExpr {
        int map();
    }

    interface FilterExpr {
        boolean filter();
    }

    public static void main(String[] args) {
        Iterable<Integer> list = [new MapExpr() {
                public int map() {
                    return x*x;
                }
            }.map() for int x : new Iterable<Integer>() {
                public Iterator<Integer> iterator() {
                    return Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).iterator();
                }
            } if new FilterExpr() {
                public boolean filter() {
                    return x >= 5;
                }
            }.filter()];

        Utils.assertEquals(list, Arrays.asList(25, 36, 49, 64, 81));
    }
}
