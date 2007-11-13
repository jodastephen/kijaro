
public class PropertyLiteral {
    public property String test;
    
    public static void main(String[] args) {
        System.out.println(PropertyLiteral#test);

        PropertyLiteral pl = new PropertyLiteral();
        PropertyLiteral#test.set(pl, "hello");
        System.out.println(PropertyLiteral#test.get(pl) + " world");
    }
}
