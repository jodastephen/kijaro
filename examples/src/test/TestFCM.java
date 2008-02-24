package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestFCM {

    private static TestFCM staticInstance;
    private static ActionEvent event;
    private static String output;
    private String name;
    TestFCM inner = null;

    public TestFCM(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        try {
            int ok = 0;
            int errors = 0;
            Method[] methods = TestFCM.class.getMethods();
            for (Method method : methods) {
                if (method.getReturnType() == Void.TYPE &&
                        method.getParameterTypes().length == 0 &&
                        method.getName().startsWith("test")) {
                    
                    TestFCM fcm = new TestFCM("Main");
                    try {
                        method.invoke(fcm);
                        System.out.println("OK in " + method.getName());
                        ok++;
                    } catch (InvocationTargetException ex) {
                        System.out.println("Error in " + method.getName());
                        ex.getCause().printStackTrace(System.out);
                        errors++;
                    }
                    staticInstance = null;
                    event = null;
                    output = null;
                }
            }
            System.out.println(" Complete: " + (ok + errors) + " tests, " + ok + " succeeded, " + errors + " failed");
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    public void testBoundMethodReference_() throws Exception {
        ActionListener lnr = #handleInstanceAction(ActionEvent);
        assert lnr != null;
        ActionEvent input = new ActionEvent("src", 0, "testBoundMethodReference_This");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("Main");
    }

    public void testBoundMethodReference_This() throws Exception {
        ActionListener lnr = this#handleInstanceAction(ActionEvent);
        assert lnr != null;
        ActionEvent input = new ActionEvent("src", 0, "testBoundMethodReference_This");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("Main");
    }

    public void testBoundMethodReference_QualifiedThis() throws Exception {
        ActionListener lnr = TestFCM.this#handleInstanceAction(ActionEvent);
        assert lnr != null;
        ActionEvent input = new ActionEvent("src", 0, "testBoundMethodReference_QualifiedThis");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("Main");
    }

    //-----------------------------------------------------------------------
    public void testBoundMethodReference_Inner() throws Exception {
        inner = new TestFCM("MainInner");
        
        ActionListener lnr = inner#handleInstanceAction(ActionEvent);
        assert lnr != null;
        inner = null;  // inner should be captured
        ActionEvent input = new ActionEvent("src", 0, "testBoundMethodReference_Inner");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("MainInner");
    }

    public void testBoundMethodReference_ThisInner() throws Exception {
        inner = new TestFCM("MainInner");
        
        ActionListener lnr = this.inner#handleInstanceAction(ActionEvent);
        assert lnr != null;
        inner = null;  // inner should be captured
        ActionEvent input = new ActionEvent("src", 0, "testBoundMethodReference_ThisInner");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("MainInner");
    }

    public void testBoundMethodReference_QualifiedThisInner() throws Exception {
        inner = new TestFCM("MainInner");
        
        ActionListener lnr = TestFCM.this.inner#handleInstanceAction(ActionEvent);
        assert lnr != null;
        inner = null;  // inner should be captured
        ActionEvent input = new ActionEvent("src", 0, "testBoundMethodReference_QualifiedThisInner");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("MainInner");
    }

    //-----------------------------------------------------------------------
    public void testBoundMethodReference_Static() throws Exception {
        staticInstance = new TestFCM("Static");
        
        ActionListener lnr = staticInstance#handleInstanceAction(ActionEvent);
        assert lnr != null;
        staticInstance = null;  // staticInstance should be captured
        ActionEvent input = new ActionEvent("src", 0, "testBoundMethodReference_Static");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("Static");
    }

    public void testBoundMethodReference_QualifiedStatic() throws Exception {
        staticInstance = new TestFCM("Static");
        
        ActionListener lnr = TestFCM.staticInstance#handleInstanceAction(ActionEvent);
        assert lnr != null;
        staticInstance = null;  // staticInstance should be captured
        ActionEvent input = new ActionEvent("src", 0, "testBoundMethodReference_QualifiedStatic");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("Static");
    }

    //-----------------------------------------------------------------------
    public void testBoundMethodReference_StaticInner() throws Exception {
        staticInstance = new TestFCM("Static");
        staticInstance.inner = new TestFCM("StaticInner");
        
        ActionListener lnr = staticInstance.inner#handleInstanceAction(ActionEvent);
        assert lnr != null;
        staticInstance.inner = null;  // staticInstance.inner should be captured
        ActionEvent input = new ActionEvent("src", 0, "testBoundMethodReference_StaticInner");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("StaticInner");
    }

    public void testBoundMethodReference_QualifiedStaticInner() throws Exception {
        staticInstance = new TestFCM("Static");
        staticInstance.inner = new TestFCM("StaticInner");
        
        ActionListener lnr = TestFCM.staticInstance.inner#handleInstanceAction(ActionEvent);
        assert lnr != null;
        staticInstance.inner = null;  // staticInstance.inner should be captured
        ActionEvent input = new ActionEvent("src", 0, "testBoundMethodReference_QualifiedStaticInner");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("StaticInner");
    }

    //-----------------------------------------------------------------------
    public void testBoundMethodReference_Local() throws Exception {
        TestFCM local = new TestFCM("Local");
        
        ActionListener lnr = local#handleInstanceAction(ActionEvent);
        assert lnr != null;
        local = null;  // local should be captured
        ActionEvent input = new ActionEvent("src", 0, "testBoundMethodReference_Local");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("Local");
    }

    public void testBoundMethodReference_LocalInner() throws Exception {
        TestFCM local = new TestFCM("Local");
        local.inner = new TestFCM("LocalInner");
        
        ActionListener lnr = local.inner#handleInstanceAction(ActionEvent);
        assert lnr != null;
        local.inner = null;  // local.inner should be captured
        ActionEvent input = new ActionEvent("src", 0, "testBoundMethodReference_LocalInner");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("LocalInner");
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    public void testStaticMethodReference() throws Exception {
        ActionListener lnr = TestFCM#handleStaticAction(ActionEvent);
        assert lnr != null;
        ActionEvent input = new ActionEvent("src", 0, "testStaticMethodReference");
        lnr.actionPerformed(input);
        assert input == event;
    }

    //-----------------------------------------------------------------------
    public void testStaticMethodReference_returnSubclass() throws Exception {
        TestStringNumberFactory factory = TestFCM#staticParseInteger(String);
        assert factory != null;
        Integer expected = new Integer(6);
        assert expected.equals(factory.create("6"));
    }

    //-----------------------------------------------------------------------
    public void testStaticMethodReference_returnPrimitive() throws Exception {
        TestStringIntFactory factory = TestFCM#staticParseInt(String);
        assert factory != null;
        assert factory.create("6") == 6;
    }

    //-----------------------------------------------------------------------
    public void testStaticMethodReference_returnUnboxed() throws Exception {
        TestStringIntFactory factory = TestFCM#staticParseInteger(String);
        assert factory != null;
        assert factory.create("6") == 6;
    }

    //-----------------------------------------------------------------------
    public void testStaticMethodReference_returnBoxed() throws Exception {
        TestStringIntegerFactory factory = TestFCM#staticParseInt(String);
        assert factory != null;
        Integer expected = new Integer(6);
        assert expected.equals(factory.create("6"));
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    public void testConstructorReference() throws Exception {
        TestStringIntegerFactory factory = Integer#(String);
        assert factory != null;
        Integer expected = new Integer(6);
        assert expected.equals(factory.create("6"));
    }

    //-----------------------------------------------------------------------
    public void testConstructorReference_returnSubclass() throws Exception {
        TestStringNumberFactory factory = Integer#(String);
        assert factory != null;
        Integer expected = new Integer(6);;
        assert expected.equals(factory.create("6"));
    }

    //-----------------------------------------------------------------------
    public void testConstructorReference_returnUnboxed() throws Exception {
        TestStringIntFactory factory = Integer#(String);
        assert factory != null;
        assert factory.create("6") == 6;
    }

//    //-----------------------------------------------------------------------
//    public void testConstructorReference_simpleGenericFactory() throws Exception {
//        TestIntegerFactory<String> factory = Integer#(String);
//        assert factory != null;
//        Integer expected = new Integer(6);;
//        assert expected.equals(factory.create("6"));
//    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    public void testMethodLiteral_static() throws Exception {
        Method meth = TestFCM#handleStaticAction(ActionEvent);
        assert meth != null;
        assert meth.getParameterTypes().length == 1;
        assert meth.getParameterTypes()[0] == ActionEvent.class;
        assert meth.getReturnType() == Void.TYPE;
        assert meth.getName().equals("handleStaticAction");
        ActionEvent input = new ActionEvent("src", 0, "testMethodLiteral_Static");
        meth.invoke(null, input);
        assert input == event;
    }

    //-----------------------------------------------------------------------
    public void testMethodLiteral_instance() throws Exception {
        Method meth = TestFCM#handleInstanceAction(ActionEvent);
        assert meth != null;
        assert meth.getParameterTypes().length == 1;
        assert meth.getParameterTypes()[0] == ActionEvent.class;
        assert meth.getReturnType() == Void.TYPE;
        assert meth.getName().equals("handleInstanceAction");
        ActionEvent input = new ActionEvent("src", 0, "testMethodLiteral_Instance");
        meth.invoke(this, input);
        assert input == event;
    }

    //-----------------------------------------------------------------------
    public void testMethodLiteral_instance_asObject() throws Exception {
        Object obj = TestFCM#handleInstanceAction(ActionEvent);
        assert obj instanceof Method;
        Method meth = (Method) obj;
        assert meth.getParameterTypes().length == 1;
        assert meth.getParameterTypes()[0] == ActionEvent.class;
        assert meth.getReturnType() == Void.TYPE;
        assert meth.getName().equals("handleInstanceAction");
        ActionEvent input = new ActionEvent("src", 0, "testMethodLiteral_Instance");
        meth.invoke(this, input);
        assert input == event;
    }

    //-----------------------------------------------------------------------
    public void testMethodLiteral_instance_expressionMethodAccess() throws Exception {
        Class<?> cls = TestFCM#handleInstanceAction(ActionEvent).getReturnType();
        assert cls == Void.TYPE;
    }

    //-----------------------------------------------------------------------
    public void testMethodLiteral_instance_expressionFieldAccess() throws Exception {
        int i = TestFCM#handleInstanceAction(ActionEvent).PUBLIC;
        assert i == Method.PUBLIC;
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    public void testConstructorLiteral() throws Exception {
        Constructor<TestFCM> con = TestFCM#(String);
        assert con != null;
        assert con.getParameterTypes().length == 1;
        assert con.getParameterTypes()[0] == String.class;
        TestFCM test = con.newInstance("Test");
        assert "Test".equals(test.name);
    }

    //-----------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public void testConstructorLiteral_asObject() throws Exception {
        Object obj = TestFCM#(String);
        assert obj instanceof Constructor;
        Constructor<TestFCM> con = (Constructor<TestFCM>) obj;
        assert con.getParameterTypes().length == 1;
        assert con.getParameterTypes()[0] == String.class;
        TestFCM test = con.newInstance("Test");
        assert "Test".equals(test.name);
    }

    //-----------------------------------------------------------------------
    public void testConstructorLiteral_expressionMethodAccess() throws Exception {
        Class<?>[] cls = TestFCM#(String).getParameterTypes();
        assert cls.length == 1;
        assert cls[0] == String.class;
    }

    //-----------------------------------------------------------------------
    public void testConstructorLiteral_expressionFieldAccess() throws Exception {
        int i = TestFCM#(String).PUBLIC;
        assert i == Method.PUBLIC;
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    public void testFieldLiteral_static() throws Exception {
        Field field = TestFCM#staticInstance;
        assert field != null;
        assert field.getType() == TestFCM.class;
        assert field.getName().equals("staticInstance");
        staticInstance = new TestFCM("Test");
        assert field.get(null) == staticInstance;
    }

    //-----------------------------------------------------------------------
    public void testFieldLiteral_instance() throws Exception {
        Field field = TestFCM#name;
        assert field != null;
        assert field.getType() == String.class;
        assert field.getName().equals("name");
        assert "Main".equals(field.get(this));
    }

    //-----------------------------------------------------------------------
    public void testFieldLiteral_instance_asObject() throws Exception {
        Object obj = TestFCM#name;
        assert obj instanceof Field;
        Field field = (Field) obj;
        assert field.getType() == String.class;
        assert field.getName().equals("name");
        assert "Main".equals(field.get(this));
    }

    //-----------------------------------------------------------------------
    public void testFieldLiteral_instance_expressionMethodAccess() throws Exception {
        Class<?> cls = TestFCM#name.getType();
        assert cls == String.class;
    }

    //-----------------------------------------------------------------------
    public void testFieldLiteral_instance_expressionFieldAccess() throws Exception {
        int i = TestFCM#name.PUBLIC;
        assert i == Method.PUBLIC;
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    public void testInnerMethod() throws Exception {
        ActionListener lnr = #(ActionEvent ev) {
            event = ev;
            output = name;
        };
        assert lnr != null;
        ActionEvent input = new ActionEvent("src", 0, "testInnerMethod");
        lnr.actionPerformed(input);
        assert input == event;
        assert output.equals("Main");
    }

    //-----------------------------------------------------------------------
    public void testInnerMethod_returnVoid() throws Exception {
        Runnable r = #() {
            output = "Called";
        };
        assert r != null;
        r.run();
        assert output.equals("Called");
    }

    //-----------------------------------------------------------------------
    public void testInnerMethod_noArgsNoParams() throws Exception {
        Runnable r = #{
            output = "Called";
        };
        assert r != null;
        r.run();
        assert output.equals("Called");
    }

    //-----------------------------------------------------------------------
    public void testInnerMethod_returnBoxed() throws Exception {
        TestStringIntegerFactory factory = #(String str) {
            return Integer.parseInt(str);  // int - boxed
        };
        assert factory != null;
        Integer expected = new Integer(6);
        assert expected.equals(factory.create("6"));
    }

    //-----------------------------------------------------------------------
    public void testInnerMethod_returnUnboxed() throws Exception {
        TestStringIntFactory factory = #(String str) {
            return new Integer(str);  // Integer - unboxed
        };
        assert factory != null;
        assert factory.create("6") == 6;
    }

    //-----------------------------------------------------------------------
    public void testInnerMethod_returnMixedBoxed() throws Exception {
        TestStringIntegerFactory factory = #(String str) {
            if (str == null) {
                return new Integer(0);  // Integer
            }
            return Integer.parseInt(str);  // int - boxed
        };
        assert factory != null;
        Integer expected1 = new Integer(6);
        assert expected1.equals(factory.create("6"));
        Integer expected2 = new Integer(0);
        assert expected2.equals(factory.create(null));
    }

    //-----------------------------------------------------------------------
    public void testInnerMethod_returnMixedUnboxed() throws Exception {
        TestStringIntFactory factory = #(String str) {
            if (str == null) {
                return new Integer(0);  // Integer - unboxed
            }
            return Integer.parseInt(str);  // int
        };
        assert factory != null;
        assert factory.create("6") == 6;
        assert factory.create(null) == 0;
    }

    //-----------------------------------------------------------------------
    public void testInnerMethod_simpleGenericParameter() throws Exception {
        TestIntegerFactory<String> factory = #(String str) {
            return Integer.parseInt(str);
        };
        assert factory != null;
        assert factory.create("6") == 6;
    }

    //-----------------------------------------------------------------------
    public void testInnerMethod_passToMethod() throws Exception {
        int result = invoker(#(String str) {
            return Integer.parseInt(str);
        }, "6");
        assert result == 6;
    }

    //-----------------------------------------------------------------------
    public static void testInnerMethod_createInStatic() throws Exception {
        TestStringIntFactory factory = #(String str) {
            return Integer.parseInt(str);
        };
        assert factory != null;
        assert factory.create("6") == 6;
    }

    //-----------------------------------------------------------------------
    public static void testInnerMethod_createInStatic_returnVoid() throws Exception {
        Runnable r = #() {
            output = "Called";
        };
        assert r != null;
        r.run();
        assert output.equals("Called");
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    public void handleInstanceAction(ActionEvent ev) {
        event = ev;
        output = name;
    }

    public static void handleStaticAction(ActionEvent ev) {
        event = ev;
    }

    public static int staticParseInt(String str) {
        return Integer.parseInt(str);
    }

    public static Integer staticParseInteger(String str) {
        return new Integer(str);
    }

    public int invoker(TestStringIntFactory factory, String str) {
        return factory.create(str);
    }

    //-----------------------------------------------------------------------
    interface TestStringIntegerFactory {
        Integer create(String str);
    }

    interface TestStringNumberFactory {
        Number create(String str);
    }

    interface TestStringIntFactory {
        int create(String str);
    }

    interface TestIntegerFactory<T> {
        Integer create(T input);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
        return name;
    }
}
