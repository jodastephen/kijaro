public class Dummy {
    private final String text;
    public Dummy(String text) {
        this.text = text;
    }

    public static void fixed(String message) {
        System.out.println("Message from fixed: " + message);
    }

    public static void fixed(boolean flag) {
        System.out.println("Message from fixed: " + flag);
    }

    public static void fixed(String message, boolean flag) {
        System.out.println("Message from fixed: " + message + " " + flag);
    }

    private static void foxed(String message) {
        System.out.println("Message from foxed: " + message);
    }

    public static void faxed(Object message) {
        System.out.println("Message from faxed(Object): " + message);
    }

    public static void faxed(String message) {
        System.out.println("Message from faxed(String): " + message);
    }

    public static class Inner {
        public Inner() {
            super();
        }
        public static void inner(String message) {
            System.out.println("Message from inner: " + message);
        }
    }

}
