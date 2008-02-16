/**
 * Interface defining an Integer factory
 */
public interface IntegerFactory<T> {

    /**
     * Creates an Integer from input.
     * @param input  the input
     * @return the resulting integer
     */
    Integer create(T input);

}
