
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

/**
 * The comprehension expression is nested in a foreach loop.
 */
public class NestedInForeach {
    public static void main(String[] args) {
        Iterable<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        List<Integer> newList = new LinkedList<Integer>();

        for (Integer i : [x*x for int x : list if x >= 5]) {
            newList.add(i);
        }

        Utils.assertEquals(newList, Arrays.asList(25, 36, 49, 64, 81));
    }
}
