
public class Test4 {
    class Inner {
        public property int test;
    }
    
    public static void main(String[] args) {
        System.out.println(Test4.Inner.test());
        System.out.println(java.util.Arrays.toString(Test4.Inner.properties()));
    }

}
