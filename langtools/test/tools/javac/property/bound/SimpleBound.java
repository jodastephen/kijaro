public class SimpleBound {
    public property String test bound;
    
    protected <B,T> void propertyChanged(java.lang.Property<B,T> property,Object oldValue,Object newValue) {
	System.out.println("property changed "+property+" "+oldValue+" "+newValue);
    }
    
    public static void main(String[] args) {
	SimpleBound simple=new SimpleBound();
	simple.test="toto";
    }
}
