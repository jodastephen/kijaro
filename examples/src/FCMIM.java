
public class FCMIM {

    private String name;

    public FCMIM(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        try {
            FCMIM fcm = new FCMIM("Main");
            fcm.processInnerMethod();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    public void processInnerMethod() throws Exception {
        StringIntegerFactory factory = #(String str) {
            System.out.println("IM:" + this);
            return Integer.parseInt(str);
        };
        System.out.println("01:" + factory);
        System.out.println("02:" + factory.create("7"));
    }

//    public void processInnerMethod() throws Exception {
//        IntegerFactory<String> factory = #(String str) {
//            System.out.println("IM:" + this);
//            return Integer.parseInt(str);
//        };
//        System.out.println("01:" + factory);
//        System.out.println("02:" + factory.create("7"));
//    }

    public Integer handle(String str) {
        return 6;
    }
    @Override
    public String toString() {
        return "FCM-IM:" + name;
    }
}
