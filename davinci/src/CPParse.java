// toy constant pool parser

import java.io.*;
import java.util.*;

class CPParser {
    private CPParser() {}

    public static Object[] parse(byte[] bytes) {
        return parse(bytes, 0, bytes.length);
    }
    public static Object[] parse(byte[] bytes, int offset, int length) {
        try {
            ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytes, offset, offset + length);
            DataInputStream in = new DataInputStream(bytesIn);
            int magic = in.readInt();
            if (magic != 0xCAFEBABE)  die("magic number");
            int maj = in.readUnsignedShort(), min = in.readUnsignedShort();
            int len = in.readUnsignedShort();
            if (!(len >= 1))  die("cp length");
            Object[] objects = new Object[len];
            int[] tags = new int[len];
            for (int i = 1, nexti; i < len; i = nexti) {
                nexti = i+1;
                int tag = tags[i] = in.readUnsignedByte();
                Object obj = null;
                switch (tag) {
                case CONSTANT_Utf8:     obj = in.readUTF(); break;
                case CONSTANT_Integer:  obj = in.readInt(); break;
                case CONSTANT_Float:    obj = in.readFloat(); break;
                case CONSTANT_Long:     obj = in.readLong(); ++nexti; break;
                case CONSTANT_Double:   obj = in.readDouble(); ++nexti; break;
                case CONSTANT_Class:    // fall through:
                case CONSTANT_String:   obj = (int) in.readUnsignedShort(); break;

                case CONSTANT_Fieldref:           // fall through:
                case CONSTANT_Methodref:          // fall through:
                case CONSTANT_InterfaceMethodref: // fall through:
                case CONSTANT_NameAndType:
                    obj = new int[] { in.readUnsignedShort(), in.readUnsignedShort() };
                    break;
                }
                objects[i] = obj;
            }
            // pass back the tags inside the objects array:
            objects[0] = tags;
            // pass back the new position within the byte array argument:
            tags[0] = offset + length - (int) bytesIn.available();
            return objects;
        } catch (IOException ee) {
            die(ee.toString());
            return null;
        }
    }

    public static Object[] resolve(Object[] objects) {
        objects = objects.clone();
        int[] tags = (int[]) objects[0];
        for (int i = 1; i < objects.length; i++) {
            Object obj = objects[i];
            int tag = tags[i];
            switch (tag) {
            case CONSTANT_Class:
            case CONSTANT_String:
                obj = Arrays.asList(tagNames[tag], objects[(Integer)obj]);
                break;
            case CONSTANT_Fieldref:           // fall through:
            case CONSTANT_Methodref:          // fall through:
            case CONSTANT_InterfaceMethodref: // fall through:
                int[] pair1 = (int[]) obj;
                Object cls = objects[pair1[0]];
                if (cls instanceof Integer)
                    cls = objects[(Integer)cls];
                else if (cls instanceof Object[])
                    cls = ((Object[])cls)[1];
                int[] pair2 = (int[]) objects[pair1[1]];
                Object name = objects[pair2[0]];
                Object sig = objects[pair2[1]];
                obj = Arrays.asList(tagNames[tag], cls, name, sig);
                break;
            }
            objects[i] = obj;
        }
        return objects;
    }

    static void die(String msg) {
        throw new RuntimeException("bad CP: "+msg);
    }

    private static final int
        CONSTANT_Utf8 = 1,
        CONSTANT_Unicode = 2,               /* unused */
        CONSTANT_Integer = 3,
        CONSTANT_Float = 4,
        CONSTANT_Long = 5,
        CONSTANT_Double = 6,
        CONSTANT_Class = 7,
        CONSTANT_String = 8,
        CONSTANT_Fieldref = 9,
        CONSTANT_Methodref = 10,
        CONSTANT_InterfaceMethodref = 11,
        CONSTANT_NameAndType = 12;
    private static String[] tagNames  = {
        "null",
        "Utf8",
        "Unicode",
        "Integer",
        "Float",
        "Long",
        "Double",
        "Class",
        "String",
        "Fieldref",
        "Methodref",
        "InterfaceMethodref",
        "NameAndType"
    };
}
