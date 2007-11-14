package property.simple;

/*
 * @test @(#)RegressionNoPropertyArrayType.java 1.7 10/06/07
 * @bug 4228585
 * @summary support property regression check
 * @author forax
 *
 * @library ../..
 * @compile RegressionNoPropertyArrayType.java
 * @run main property.simple.RegressionNoPropertyArrayType
 */
public class RegressionNoPropertyArrayType {
    private int data[];
    private Object hello[];
    
    public static void main(String[] args) {
        RegressionNoPropertyArrayType rnpat = new RegressionNoPropertyArrayType();
        rnpat.data = new int[1];
        rnpat.hello = new Object[1];
    }
}