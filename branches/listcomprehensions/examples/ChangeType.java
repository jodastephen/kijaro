
import java.util.Arrays;

/**
 * Comprehensions can generate an iterable that's a different type
 * than the input iterable. Here we convert from string to float.
 */
public class ChangeType {
    public static void main(String[] args) throws NumberFormatException {
        Iterable<String> stringList = Arrays.asList("0", "123", "5.75");
        Iterable<Float> floatList =
            [Float.valueOf(x) for String x : stringList];

        Utils.assertEquals(floatList, Arrays.asList(0f, 123f, 5.75f));
    }
}
