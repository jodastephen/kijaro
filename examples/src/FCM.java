import java.lang.reflect.Method;

public class FCM {

    static Dummy DUMMY = new Dummy("A");
    
    Dummy dummy = new Dummy("B");
    Dummy[] dummys = new Dummy[] {new Dummy("C")};

    public static void main(String[] args) {
        try {
            new FCM().process();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    public void process() throws Exception {
//        Dummy dummy = dummy;
        System.out.println("Hello FCM");
//        Method m = Dummy#fixed(String);  // succeeds = OK
//        Method m = Dummy#fixed(boolean);  // succeeds = OK
        Method m = Dummy#fixed(String,boolean);  // succeeds = OK
//        Method m = new Dummy("D")#fixed(String);  // fails = OK
//        Method m = dummy#fixed(String);  // succeeds = OK
//        Method m = this#process();  // succeeds = OK
//        Method m = super#clone();  // fails = OK
//        Method m = dummys[0]#fixed(String);  // succeeds = OK
//        Method m = dummys#hashCode();  // succeeds = OK
//        Method m = Dummy.Inner.class#getDeclaredMethods();  // succeeds = OK
//        Method m = FCM.DUMMY#fixed(String);  // succeeds = OK
//        Method m = Dummy.Inner#inner(String);  // succeeds = OK
        System.out.println(m.getName());
        m.invoke(dummy, "Hello");
    }

    private static Method findMethod(Class<?> cls, String name, Class<?>... types) {
        try {
            return cls.getDeclaredMethod(name, types);
        } catch (SecurityException ex) {
            throw new NoSuchMethodError(ex.getMessage());
        } catch (NoSuchMethodException ex) {
            throw new NoSuchMethodError(ex.getMessage());
        }
    }

}
