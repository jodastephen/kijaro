package property.simple;

/*
 * @test @(#)InvalidPropertyDeclaration.java 1.7 10/06/07
 * @bug 4228585
 * @summary support property syntax check
 * @author freddy33
 *
 * @library ../..
 * @compile/fail/ref=InvalidPropertyDeclaration.out -XDstdout -XDdiags=%b:%l:%_%m InvalidPropertyDeclaration.java
 */
public class InvalidPropertyDeclaration {

    public property String ok;
    public property error;
    public property int good;

}