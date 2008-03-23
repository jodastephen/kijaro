package java.lang;

public class NoSuchPropertyException extends Exception {
    private static final long serialVersionUID = -1490118501249593881L;

    public NoSuchPropertyException() {
        super();
    }

    public NoSuchPropertyException(String message) {
        super(message);
    }

    public NoSuchPropertyException(Throwable cause) {
        super(cause);
    }

    public NoSuchPropertyException(String message,Throwable cause) {
        super(message,cause);
    }
}
