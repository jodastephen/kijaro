package test;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TestFCM {

    private static TestFCM staticInstance;
    TestFCM inner = null;
    private String name;
    private static ActionEvent event;
    private static String output;

    public TestFCM(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        try {
            TestFCM fcm = null;
            
            fcm = new TestFCM("Main");
            fcm.testMethodReference_This();
            fcm = new TestFCM("Main");
            fcm.testMethodReference_QualifiedThis();
            fcm = new TestFCM("Main");
            fcm.testMethodReference_Inner();
            fcm = new TestFCM("Main");
            fcm.testMethodReference_ThisInner();
            fcm = new TestFCM("Main");
            fcm.testMethodReference_QualifiedThisInner();
            fcm = new TestFCM("Main");
            fcm.testMethodReference_Static();
            fcm = new TestFCM("Main");
            fcm.testMethodReference_QualifiedStatic();
            fcm = new TestFCM("Main");
            fcm.testMethodReference_StaticInner();
            fcm = new TestFCM("Main");
            fcm.testMethodReference_QualifiedStaticInner();
            fcm = new TestFCM("Main");
            fcm.testMethodReference_Local();
            fcm = new TestFCM("Main");
            fcm.testMethodReference_LocalInner();
            System.out.println("OK");
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    //-----------------------------------------------------------------------
//    public void testMethodReference_() throws Exception {
//        TestFCM fcm = new TestFCM("Main");
//        
//        ActionListener lnr = #handleInstanceAction(ActionEvent);
//        assert lnr != null;
//        ActionEvent input = new ActionEvent("src", 0, "testMethodReference_This");
//        lnr.actionPerformed(input);
//        assert input == event;
//        assert output.equals("Main");
//    }

    public void testMethodReference_This() throws Exception {
        ActionListener lnr = this#handleInstanceAction(ActionEvent);
        assert lnr != null;
        ActionEvent input = new ActionEvent("src", 0, "testMethodReference_This");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("Main");
    }

    public void testMethodReference_QualifiedThis() throws Exception {
        ActionListener lnr = TestFCM.this#handleInstanceAction(ActionEvent);
        assert lnr != null;
        ActionEvent input = new ActionEvent("src", 0, "testMethodReference_QualifiedThis");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("Main");
    }

    //-----------------------------------------------------------------------
    public void testMethodReference_Inner() throws Exception {
        inner = new TestFCM("MainInner");
        
        ActionListener lnr = inner#handleInstanceAction(ActionEvent);
        assert lnr != null;
        ActionEvent input = new ActionEvent("src", 0, "testMethodReference_Inner");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("MainInner");
    }

    public void testMethodReference_ThisInner() throws Exception {
        inner = new TestFCM("MainInner");
        
        ActionListener lnr = this.inner#handleInstanceAction(ActionEvent);
        assert lnr != null;
        ActionEvent input = new ActionEvent("src", 0, "testMethodReference_ThisInner");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("MainInner");
    }

    public void testMethodReference_QualifiedThisInner() throws Exception {
        inner = new TestFCM("MainInner");
        
        ActionListener lnr = TestFCM.this.inner#handleInstanceAction(ActionEvent);
        assert lnr != null;
        ActionEvent input = new ActionEvent("src", 0, "testMethodReference_QualifiedThisInner");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("MainInner");
    }

    //-----------------------------------------------------------------------
    public void testMethodReference_Static() throws Exception {
        staticInstance = new TestFCM("Static");
        
        ActionListener lnr = staticInstance#handleInstanceAction(ActionEvent);
        assert lnr != null;
        ActionEvent input = new ActionEvent("src", 0, "testMethodReference_Static");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("Static");
    }

    public void testMethodReference_QualifiedStatic() throws Exception {
        staticInstance = new TestFCM("Static");
        
        ActionListener lnr = TestFCM.staticInstance#handleInstanceAction(ActionEvent);
        assert lnr != null;
        ActionEvent input = new ActionEvent("src", 0, "testMethodReference_QualifiedStatic");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("Static");
    }

    //-----------------------------------------------------------------------
    public void testMethodReference_StaticInner() throws Exception {
        staticInstance = new TestFCM("Static");
        staticInstance.inner = new TestFCM("StaticInner");
        
        ActionListener lnr = staticInstance.inner#handleInstanceAction(ActionEvent);
        assert lnr != null;
        ActionEvent input = new ActionEvent("src", 0, "testMethodReference_StaticInner");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("StaticInner");
    }

    public void testMethodReference_QualifiedStaticInner() throws Exception {
        staticInstance = new TestFCM("Static");
        staticInstance.inner = new TestFCM("StaticInner");
        
        ActionListener lnr = TestFCM.staticInstance.inner#handleInstanceAction(ActionEvent);
        assert lnr != null;
        ActionEvent input = new ActionEvent("src", 0, "testMethodReference_QualifiedStaticInner");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("StaticInner");
    }

    //-----------------------------------------------------------------------
    public void testMethodReference_Local() throws Exception {
        TestFCM local = new TestFCM("Local");
        
        ActionListener lnr = local#handleInstanceAction(ActionEvent);
        assert lnr != null;
        ActionEvent input = new ActionEvent("src", 0, "testMethodReference_Local");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("Local");
    }

    public void testMethodReference_LocalInner() throws Exception {
        TestFCM local = new TestFCM("Local");
        local.inner = new TestFCM("LocalInner");
        
        ActionListener lnr = local.inner#handleInstanceAction(ActionEvent);
        assert lnr != null;
        ActionEvent input = new ActionEvent("src", 0, "testMethodReference_LocalInner");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("LocalInner");
    }

    //-----------------------------------------------------------------------
    public static void handleStaticAction(ActionEvent ev) {
        System.out.println("Event occurred: " + ev);
    }

    public void handleInstanceAction(ActionEvent ev) {
        event = ev;
        output = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
