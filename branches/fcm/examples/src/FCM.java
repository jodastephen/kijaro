import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class FCM {

    static FCM stat = new FCM("Static");
    static Dummy DUMMY = new Dummy("A");
    
    Dummy dummy = new Dummy("B");
    Dummy[] dummys = new Dummy[] {new Dummy("C")};
    FCM inner = null;
    private String name;

    public FCM(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        try {
            FCM fcm = new FCM("Main");
            fcm.inner = new FCM("MainInner");
            System.out.println(fcm);
            System.out.println(fcm.inner);
            fcm.process();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    public void process() throws Exception {
//        Dummy dummy = dummy;
        System.out.println("Hello FCM");
        
        ActionListener lnr = FCM#handleStaticAction(ActionEvent);  // StaticAction
        System.out.println(lnr);
        lnr.actionPerformed(new ActionEvent("src", 0, "cmd"));
        
//        Class<?> cls1 = Class.forName("FCM$1");
//        Constructor<?>[] cons1 = cls1.getDeclaredConstructors();
//        System.out.println(Arrays.toString(cons1));
        
        FCM local = new FCM("Local");
        local.inner = new FCM("LocalInner");
//        ActionListener lnrLocal = FCM.this#handleLocalAction(ActionEvent);  // Main
//        ActionListener lnrLocal = this#handleLocalAction(ActionEvent);  // Main
//        ActionListener lnrLocal = #handleLocalAction(ActionEvent);  // NOT IMPLEMENTED
//        ActionListener lnrLocal = FCM.this.inner#handleLocalAction(ActionEvent);  // MainInner
//        ActionListener lnrLocal = this.inner#handleLocalAction(ActionEvent);  // MainInner
//        ActionListener lnrLocal = inner#handleLocalAction(ActionEvent);  // MainInner
        ActionListener lnrLocal = local#handleLocalAction(ActionEvent);  // Local
//        ActionListener lnrLocal = local.inner#handleLocalAction(ActionEvent);  // LocalInner
//        ActionListener lnrLocal = FCM.stat#handleLocalAction(ActionEvent);  // Static
//        ActionListener lnrLocal = stat#handleLocalAction(ActionEvent);  // Static
//        ActionListener lnrLocal = local#handleStaticAction(ActionEvent);  // StaticAction
        System.out.println(lnrLocal);
        lnrLocal.actionPerformed(new ActionEvent("src", 0, "cmdLocal"));
        
//        Class<?> cls2 = Class.forName("FCM$2");
//        Constructor<?>[] cons2 = cls2.getDeclaredConstructors();
//        System.out.println(Arrays.toString(cons2));
//        
//        ActionListener lnrA = new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                FCM.super.getClass();
//            }
//        };
        
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
        
//        #(String(int,Object)) mt = null;
//        #(String(int,#(void()))) mt = null;
//        
//        {int,{=> void} => String} mt = null;
//        #(String(int,#(void()))) mt = null;
//        Fn#String(int,Fn#void()) mt = null;
//        Fn<String(int,Fn<void()>)> mt = null;
//        Fn<int, Fn<return void> return String> mt = null;
////        Fn<int, Fn<=> void> => String> mt = null;
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

    public static void handleStaticAction(ActionEvent ev) {
        System.out.println("Event occurred: " + ev + " StaticAction");
    }

    public void handleLocalAction(ActionEvent ev) {
        System.out.println("Event occurred locally: " + ev + " " + this);
    }

    @Override
    public String toString() {
        return "FCM:" + name;
    }
}
