package property.bound;

/*
 * @test @(#)SimpleBound.java 1.7 10/06/07
 * @bug 4228585
 * @summary support property decalaration binding
 * @author forax
 *
 * @library ../..
 * @compile SimpleBound.java
 * @run main property.bound.SimpleBound
 */
public class SimpleBound {
    public property String test bound;
    private static String received;

    protected <B,T> void propertyChanged(java.lang.Property<B,T> property,Object oldValue,Object newValue) {
        System.out.println("property changed "+property+" "+oldValue+" "+newValue);
        received = (String) newValue;
    }
    
    public static void main(String[] args) {
        SimpleBound simple=new SimpleBound();
        simple.test="toto";
        assert received != null;
        assert received.equals("toto");
    }
}
