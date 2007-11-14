package property.simple;

/*
 * @test @(#)PropertyAccess.java 1.7 10/06/07
 * @bug 4228585
 * @summary support property getter test
 * @author forax
 *
 * @library ../..
 * @compile PropertyAccess.java
 * @run main property.simple.PropertyAccess
 */
public class PropertyAccess {
    public property String text;
    
    public static void main(String[] args) {
        PropertyAccess access = new PropertyAccess();
        access.text = "hello";
        System.out.println(access.text + " world");
        assert "hello".equals(access.text);
    }
}
