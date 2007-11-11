
public class PropertyAccess {
    public property String text;
    
    public static void main(String[] args) {
	    PropertyAccess access = new PropertyAccess();
	    access.text = "hello";
	    System.out.println(access.text + " world");
    }
}
