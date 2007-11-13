
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
    }
}
