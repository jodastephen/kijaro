/*
 * @test
 * 
 * @summary Verify types and values of integer literals
 * @author maddox (adapted from bug report)
 *
 * @run compile IntLiterals.java
 * @run main IntLiterals.java
 */

public class IntLiterals {
    public static void main(String[] args) throws Exception {
        assertKind(Type.INT,1);
        assertKind(Type.LONG,1L);
        assertKind(Type.BYTE,(byte)0xff);
        
        assertKind(Type.BYTE, 0h1);
        assertKind(Type.BYTE, 0h01);
        assertKind(Type.BYTE, 0hff);
        assertKind(Type.SHORT,0h001);
        assertKind(Type.SHORT,0h0001);
        assertKind(Type.SHORT,0hffff);
        assertKind(Type.INT,  0h00001);
        assertKind(Type.INT,  0h00000001);
        assertKind(Type.INT,  0hffffffff);
        assertKind(Type.LONG, 0h000000001);
        assertKind(Type.LONG, 0h0000000000000001);
        
        assertKind(Type.BYTE, 0b1);
        assertKind(Type.BYTE, 0b00000001);
        assertKind(Type.SHORT,0b000000001);
        
        if(0hff != -1) throw new Exception(0hff + "!=-1");
    }

    enum Type { BYTE,SHORT,INT,LONG }

    private static void assertKind(Type t, byte b) throws Exception {
        if(t != Type.BYTE) throw new Exception("expected " + t + " but was byte");
        if(b != 1 && b != -1) throw new Exception("expected value of 1 or -1 but was " + b);
    }
    private static void assertKind(Type t, short b) throws Exception {
        if(t != Type.SHORT) throw new Exception("expected " + t + " but was short");
        if(b != 1 && b != -1) throw new Exception("expected value of 1 or -1 but was " + b);
    }
    private static void assertKind(Type t, int b) throws Exception {
        if(t != Type.INT) throw new Exception("expected " + t + " but was int");
        if(b != 1 && b != -1) throw new Exception("expected value of 1 or -1 but was " + b);
    }
    private static void assertKind(Type t, long b) throws Exception {
        if(t != Type.LONG) throw new Exception("expected " + t + " but was long");
        if(b != 1 && b != -1) throw new Exception("expected value of 1 or -1 but was " + b);
    }
}