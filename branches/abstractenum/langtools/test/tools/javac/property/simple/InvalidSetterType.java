package property.simple;

/*
 * @test @(#)InvalidSetterType.java 1.7 10/06/07
 * @bug 4228585
 * @summary support property syntax check
 * @author forax
 *
 * @library ../..
 * @compile/fail/ref=InvalidSetterType.out -XDstdout -XDdiags=%b:%l:%_%m InvalidSetterType.java
 */
public class InvalidSetterType {

    public property double x
      get {
          return 0;
      }
      set(int v) {

      };
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }

}
