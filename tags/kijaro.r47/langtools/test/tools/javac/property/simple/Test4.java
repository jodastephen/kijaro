package property.simple;

/*
 * @test @(#)Test4.java 1.7 10/06/07
 * @bug 4228585
 * @summary support property syntax check
 * @author forax
 *
 * @library ../..
 * @compile Test4.java
 * @run main property.simple.Test4
 */
public class Test4 {
    class Inner {
        public property int test;
    }
    
    public static void main(String[] args) {
        System.out.println(Test4.Inner.test());
        System.out.println(java.util.Arrays.toString(Test4.Inner.properties()));
    }

}
