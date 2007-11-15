package property.simple;

/*
 * @test @(#)PropertyLiteral2.java 1.7 10/06/07
 * @bug 4228585
 * @summary support property literal inheritance
 * @author forax
 *
 * @library ../..
 * @compile PropertyLiteral2.java
 * @run main property.simple.PropertyLiteral2
 */
public class PropertyLiteral2 {
    static class A {
        public property String test;
    }
     static class B extends A {
     }
    
    
    public static void main(String[] args) {
        Property<A, String> prop = B#test;
        assert prop.getType() == String.class;
    }
}
