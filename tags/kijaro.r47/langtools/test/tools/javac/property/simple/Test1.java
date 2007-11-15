package property.simple;

/*
 * @test @(#)Test1.java 1.7 10/06/07
 * @bug 4228585
 * @summary support property syntax check
 * @author forax
 *
 * @library ../..
 * @compile Test1.java
 */
public class Test1 {

    public property int x1;
    public property int x2 bound;
    public property int x3 get;
    public property int x4 set;
    public property int x5 bound get;
    public property int x6 bound set;
    
    protected <B,T> void propertyChanged(java.lang.Property<B,T> property,Object oldValue,Object newValue) {
        System.out.println("property changed "+property+" "+oldValue+" "+newValue);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }

}
