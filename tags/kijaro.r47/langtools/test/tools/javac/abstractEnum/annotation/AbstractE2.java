package abstractEnum.annotation;

/**
 * User: freds
 * Date: Jun 22, 2007
 * Time: 9:28:23 PM
 */
public abstract enum AbstractE2/*<E extends Enum<E>> extends Enum<E>*/ {
    public int f() {
        return ordinal();
    }

    public String full() {
        return name() + ":" + ordinal();
    }
}
