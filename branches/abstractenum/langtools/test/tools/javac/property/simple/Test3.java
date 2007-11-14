package property.simple;

/*
 * @test @(#)Test3.java 1.7 10/06/07
 * @bug 4228585
 * @summary support property syntax check
 * @author forax
 *
 * @library ../..
 * @compile Test3.java
 * @run main property.simple.Test3
 */
public class Test3 {
    private String value;
    public property String test
        get {
            return value;
        }
        set(String t) {
            value = t;
        };
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }

}
