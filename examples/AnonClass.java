
import java.util.Arrays;
import java.util.Iterator;

/**
 * Has an anonymous class as a source expression. This fails because the type
 * of the expression is "Iterable<Integer>" in Lower.java, but the constructor
 * of the comprehension expects just "Iterable". I don't know why the expression
 * wasn't erased in TransTypes.java. Look into that next.
 *
 * The type is a ClassType, where name.len == 0 and ((ClassType)tsym.type).interfaces_field.head
 * is not erased.
 *
 * Even if we fix that we'll have other problems with free variables.
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
            }.map() for final int x : new Iterable<Integer>() {
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
