import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
//        Field f = this#name;
//        System.out.println(f);

//    	Constructor<FCMDirect> c = this#(String);
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

    @Override
    public String toString() {
        return "FCM:" + name;
    }
}
