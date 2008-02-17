
import java.util.Iterator;

public class Utils {
    public static <E> void assertEquals(Iterable<E> a, Iterable<E> b) {
        Iterator<E> aItr = a.iterator();
        Iterator<E> bItr = b.iterator();

        while (aItr.hasNext() && bItr.hasNext()) {
            E aElement = aItr.next();
            E bElement = bItr.next();

            if (!aElement.equals(bElement)) {
                System.err.println("Content of iterables is different.");
                System.exit(1);
            }
        }

        if (aItr.hasNext() || bItr.hasNext()) {
            System.err.println("Lengths of iterables are different.");
            System.exit(1);
        }
    }
}
