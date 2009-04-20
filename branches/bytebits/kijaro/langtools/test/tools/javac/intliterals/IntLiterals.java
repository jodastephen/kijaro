
import java.util.Arrays;

/*
 * @test
 * 
 * @summary Verify types and values of integer literals
 * @author bruce chapman
 *
 * @run compile IntLiterals.java
 * @run main IntLiterals.java
 */

public class IntLiterals {
    public static void main(String[] args) throws Exception {
        
        // sanity check JLS3 
        assertKind(Type.INT,1);
        assertKind(Type.LONG,1L);
        assertKind(Type.BYTE,(byte)0xff);
        
        // narrow integer literals smallest type which fits it
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
        
        // byte and short suffixes
        assertKind(Type.BYTE, 1y);
        assertKind(Type.BYTE, 0x1Y);
        assertKind(Type.BYTE, 01y);
        
        assertKind(Type.SHORT, 1s);
        assertKind(Type.INT, -1s);  // 1S is unary numeric promoted to an int before unary - operator
        assertKind(Type.SHORT, 01S);
        
        // underscores in numbers
        int[] examples = {52, 5_2, 52_, 0x_52, 0x5_2, 0x52_, 0_52, 05_2, 052_,  0h_5_2_};
        int[] values = { 52,  52,   52, 0x52,  0x52,  0x52,  052,  052,  052,  0x52};
        if(! Arrays.equals(examples, values)) {
            throw new Exception("underscored values are different");
        }
        
        long creditCardNumber = 1234_5678_9012_3456L;
        long socialSecurityNumbers = 999_99_9999L;
        float monetaryAmount = 12_345_132.12;
        long hexBytes = 0xFF_EC_DE_5E;
        long hexWords = 0xFFEC_DE5E;
        long maxLong = 0x7fff_ffff_ffff_ffffL;
        long maxLongDecimal = 9223372036854775807L;
        long alsoMaxLong = 9_223_372_036_854_775_807L;
        double whyWouldYouEverDoThis = 0x1_.ffff_ffff_ffff_fp10_23;
        double whyWouldYouEverDoEvenThis = 0x1.fffffffffffffp1023;


        
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