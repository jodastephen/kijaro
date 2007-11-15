package property.simple;

/*
 * @test @(#)PropertyInheritance.java 1.7 10/06/07
 * @bug 4228585
 * @summary support property inheritance
 * @author forax
 *
 * @library ../..
 * @compile PropertyInheritance.java
 * @run main property.simple.PropertyInheritance
 */
public class PropertyInheritance {
    static class A {
        public property String test;
    }
    static class B extends A {
        public property String test;
    }
    
    
    public static void main(String[] args) {
        A a = new A();
        a.test = "testA";
        B b = new B();
        b.test = "testB";

        A ab = b;
        System.out.println(ab.test);
        assert "testB".equals(ab.test);
    }
}
