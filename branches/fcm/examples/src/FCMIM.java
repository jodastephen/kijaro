
public class FCMIM {

    private String name;

    public FCMIM(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        try {
            FCMIM fcm = new FCMIM("Main");
            fcm.processInnerMethod("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public <T> void processInnerMethod(T in) throws Exception {
//        T out = in;
//        System.out.println(out.toString());
        
        IntegerFactory<String> factory = #(String str) {
            if (str == null) {
                return 1;
            }
            System.out.println("IM:" + this);
            return Integer.parseInt(str);
        };
        System.out.println("01:" + factory);
        System.out.println("02:" + factory.create("7"));
        System.out.println("02:" + factory.create(null));
        
//        handle(#(String str) {
//            if (str == null) {
//                return new Integer(1);
//            }
//            System.out.println("IM:" + this);
//            return new Integer(str);
//        });
        
//        Runnable r = # {
//            System.out.println("HI");
//        };
//        r.run();
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

    public <T> void handle(IntegerFactory<T> factory) {
        System.out.println(factory.create(null) - 3);
    }

    @Override
    public String toString() {
        return "FCM-IM:" + name;
    }
}
