
import java.util.Arrays;

/**
 * A chained string map and filter to process an input file.
 */
public class SimpleString {
    public static void main(String[] args) {
        Iterable<String> lines = Arrays.asList(
                "  # first line with comment\n",
                "\n",
                "Real line\n",
                "# My comment!\n",
                "   bunches of whitespace   \n");

        // Remove end-of-line character.
        lines = [line.substring(0, line.length() - 1) for String line : lines];

        // Strip leading and trailing whitespace.
        lines = [line.trim() for String line : lines];

        // Remove blank lines.
        lines = [line for String line : lines if !line.isEmpty()];

        // Remove comments.
        lines = [line for String line : lines if !line.startsWith("#")];

        Utils.assertEquals(lines,
                Arrays.asList("Real line", "bunches of whitespace"));
    }
}
