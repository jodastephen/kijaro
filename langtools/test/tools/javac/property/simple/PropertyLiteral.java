package property.simple;

/*
 * @test @(#)PropertyLiteral.java 1.7 10/06/07
 * @bug 4228585
 * @summary support property literal access
 * @author forax
 *
 * @library ../..
 * @compile PropertyLiteral.java
 * @run main property.simple.PropertyLiteral
 */
public class PropertyLiteral {
    public property String test;
    
    public static void main(String[] args) {
        System.out.println(PropertyLiteral#test);

        PropertyLiteral pl = new PropertyLiteral();
        PropertyLiteral#test.set(pl, "hello");
        System.out.println(PropertyLiteral#test.get(pl) + " world");
        assert "hello".equals(PropertyLiteral#test.get(pl));
    }
}
