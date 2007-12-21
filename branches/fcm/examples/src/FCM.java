import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
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
//        Method m = new Dummy("Hi")#fixed(String,boolean);  // fails = BUG
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
        m.invoke(dummy, "Hello", true);
        
        Field f = Dummy#message;  // succeeds = OK
        System.out.println(f.getName());
        System.out.println(f.get(dummy));
        
//        Constructor c = Dummy#(String);  // succeeds = OK
//        Constructor<?> c = Dummy#(String);  // succeeds = OK
        Constructor<Dummy> c = Dummy#(String);  // succeeds = OK
        System.out.println(c.getName());
        Dummy created = (Dummy) c.newInstance("great");
        created.shout("This is ");
    }

    private static Field findField(Class<?> cls, String name) {
        try {
            return cls.getDeclaredField(name);
        } catch (SecurityException ex) {
            throw new NoSuchMethodError(ex.getMessage());
        } catch (NoSuchFieldException ex) {
            throw new NoSuchMethodError(ex.getMessage());
        }
    }

    private static Constructor findConstructor(Class<?> cls, Class<?>... types) {
        try {
            return cls.getDeclaredConstructor(types);
        } catch (SecurityException ex) {
            throw new NoSuchMethodError(ex.getMessage());
        } catch (NoSuchMethodException ex) {
            throw new NoSuchMethodError(ex.getMessage());
        }
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
