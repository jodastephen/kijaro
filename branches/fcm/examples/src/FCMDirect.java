import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Random;

public class FCMDirect {

    private String name;

    public FCMDirect(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        try {
            FCMDirect fcm = new FCMDirect("Main");
            fcm.processDirect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    public void processDirect() {

//    	Constructor<String> c = this#(String);
//    	System.out.println(c);

//        Method m = this#handleLocalAction(ActionEvent);
//        System.out.println(m);
        
//        ActionListener lnr0 = this#handleLocalAction(ActionEvent);
//        ActionListener lnr = new Random().nextBoolean() ?
//                this#handleLocalAction(ActionEvent) : lnr0;  // OK
//                lnr0 : this#handleLocalAction(ActionEvent);  // OK
//                this#handleLocalAction(ActionEvent) : this#handleStaticAction(ActionEvent);  // OK
//                this#handleLocalAction(ActionEvent) :  // ERROR, BUG?
//                new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                    }
//                };
        
//        ActionListener[] array = new ActionListener[] {  // OK
//                this#handleLocalAction(ActionEvent),
//        };
//        registerAndCall(this#handleLocalAction(ActionEvent));  // ERROR, AMBIGUOUS
//        registerAndCall((AltListener) this#handleLocalAction(ActionEvent));  // OK
//        Object obj = (String) this#handleLocalAction(ActionEvent);  // ERROR, OK
//        Object obj = (ActionListener) this#handleLocalAction(ActionEvent);  // OK
//        Object obj = this#handleLocalAction(ActionEvent);  // ERROR, OK
        
//        ActionListener lnr = this#handleLocalAction(ActionEvent);
//        registerAndCall(lnr);
    }

    public static void handleStaticAction(ActionEvent ev) {
        System.out.println("Event occurred: " + ev + " StaticAction");
    }

    public void handleLocalAction(ActionEvent ev) {
        System.out.println("Event occurred locally: " + ev + " " + this);
    }

    public Integer convertStringToInteger(String str) {
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException ex) {
            return Integer.valueOf(0);
        }
    }

    public void registerAndCall(ActionListener lnr) {
        System.out.println(lnr);
        lnr.actionPerformed(new ActionEvent("boo", 0, "hoo"));
    }

    public void registerAndCall(AltListener lnr) {
        System.out.println(lnr);
        lnr.action(new ActionEvent("hey", 0, "you"));
    }

    public void registerAndCall(Object lnr) {
        System.out.println(lnr);
        System.out.println("XXX");
    }

    private static <T> Constructor<T> findConstructor(Class<T> cls, Class<?>... types) {
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

    @Override
    public String toString() {
        return "FCM:" + name;
    }
}
