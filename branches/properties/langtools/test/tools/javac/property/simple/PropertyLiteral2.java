
public class PropertyLiteral2 {
    static class A {
	public property String test;
    }
     static class B extends A {
	 
     }
    
    
    public static void main(String[] args) {
	Property<A, String> prop = B#test;
    }
}
