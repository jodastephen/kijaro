/*
 Copyright 2008 Sun Microsystems, Inc.  All Rights Reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

   - Redistributions of source code must retain the above copyright
     notice, this list of conditions and the following disclaimer.

   - Redistributions in binary form must reproduce the above copyright
     notice, this list of conditions and the following disclaimer in the
     documentation and/or other materials provided with the distribution.

   - Neither the name of Sun Microsystems nor the names of its
     contributors may be used to endorse or promote products derived
     from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package java.dyn;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 *  Anonymous class loader.
 *  - loads an arbitrary class from bytecodes
 *  - puts class in class hierarchy but *not* in any class loader
 *  - piggybacks on a "host class" as if it were an inner class
 *  - elements of the anonymous constant pool can be patched easily
 *  - string constants can be replaced by arbitrary objects in CP
 *
 *  <p>
 *  Why the patching stuff?  Mainly, it makes some use cases much easier.
 *  Second, the constant pool needed some internal patching anyway,
 *  to anonymize the loaded class itself.  Finally, if you are going
 *  to use this seriously, you'll want to build anonymous classes
 *  on top of pre-existing anonymous classes, and that requires patching.
 *
 *  <p>%%% TO-DO:
 *   - needs better documentation
 *   - needs more security work (for safe delegation)
 *   - needs a clearer story about error processing
 *   - RFE: patch member references also (use ';' as delimiter char)
 *
 * @author John R. Rose
 * @see http://blogs.sun.com/jrose/entry/anonymous_classes_in_the_vm
 */

public
class AnonymousClassLoader {
    // ignore two warnings on this line:
    static sun.misc.Unsafe unsafe = sun.misc.Unsafe.getUnsafe();
    // preceding line requires that this class be on the boot class path
    static final int bufHeaderLen = unsafe.arrayBaseOffset(byte[].class);  // 12 or 24

    final Class<?> hostClass;

    private static final byte[] NO_CLASS_FILE = {};
    byte[] classFile = NO_CLASS_FILE;

    List<Object> classFileCP;           // built lazily

    Map<Object,Object> patchMap;

    private void setClassFile0(byte[] classFile) {
        this.classFile = classFile;
        // decache:
        classFileCP = null;
    }
    public void setClassFile(byte[] classFile) {
        setClassFile0(Arrays.copyOf(classFile, classFile.length));
    }
    public void setClassFile(byte[] classFile, int offset, int length) {
        if (offset < 0 || length < 0 || offset > classFile.length - length)
            throw new IndexOutOfBoundsException();
        setClassFile0(Arrays.copyOfRange(classFile, offset, length));
    }
    public void setClassFile(Class<?> templateClass) throws IOException {
	String templateName=templateClass.getName();
	int lastDot=templateName.lastIndexOf('.');
	if (lastDot!=-1)
	    templateName=templateName.substring(lastDot+1);
        java.net.URL tcu = templateClass.getResource(templateName+".class");
        java.net.URLConnection tcc = tcu.openConnection();
        int tcl = tcc.getContentLength();
        if (tcl < 0)  die("NYI");
        byte[] classFile = new byte[tcl];
        InputStream tcs = tcc.getInputStream();
        for (int fill = 0, nr; fill < classFile.length; fill += nr) {
            nr = tcs.read(classFile, fill, classFile.length - fill);
            if (nr < 0)  die("NYI");
        }
        setClassFile0(classFile);
    }

    public void setPatchMap(Map<Object,Object> patchMap) {
        for (Object key : patchMap.keySet())
            verifyLegalConstant(key);
        this.patchMap = patchMap;
    }
    public Map<Object,Object> getPatchMap() {
        return patchMap;
    }


    public final static class Constant {
        public final int tag; public final String symbol;
        private Constant(int tag, String symbol) {
            this.tag = tag; this.symbol = symbol;
        }
        static Constant make(int tag, String symbol) {
            return new Constant(tag, symbol);
        }

        // the point is to use them as hash table keys:
        public boolean equals(Constant that) {
            return this.tag == that.tag && this.symbol.equals(that.symbol);
        }
        @Override
	public boolean equals(Object that) {
            return that instanceof Constant && equals((Constant)that);
        }
        @Override
	public int hashCode() {
            return (tag * 127) + symbol.hashCode();
        }
        @Override
	public String toString() {
            String pfx = null;
            switch (tag) {
            case CONSTANT_Class:        pfx = "Class:";         break;
            case CONSTANT_String:       pfx = "String:";        break;
            case CONSTANT_NameAndType:  pfx = "NameAndType:";   break;
            case CONSTANT_Fieldref:     pfx = "Fieldref:";      break;
            case CONSTANT_Methodref:    pfx = "Methodref:";     break;
            case CONSTANT_InterfaceMethodref:
                                        pfx = "InterfaceMethodref:"; break;
            default: assert(false);
            }
            return pfx+symbol;
        }

        public String checkTag(int tag) {
            if (tag != this.tag)  die("wrong tag: "+this);
            return symbol;
        }

        // Factory methods:
        public static Constant forClass(String name) {
            verifyClassName(name);
            return make(CONSTANT_Class, name);
        }
        private static void verifyClassName(String name) {
            if (name.indexOf('/') >= 0 || name.indexOf(';') >= 0)
                die("dangerous character in class name: "+name);
        }
        public static Constant forClass(Class<?> cls) {
            return make(CONSTANT_Class, cls.getName());
        }

        public static Constant forString(String symbol)
            { return make(CONSTANT_String, symbol); }

        public static Constant forMember(String className,
                                         boolean isInterface,
                                         String memberName,
                                         boolean isField,
                                         String sig) {
            verifyClassName(className);
            int tag = ( isField     ? CONSTANT_Fieldref
                      : isInterface ? CONSTANT_InterfaceMethodref
                      :               CONSTANT_Methodref );
            if (memberName.indexOf(';') >= 0 ||
                (sig.startsWith("(") == isField))
                die("illegal member reference");
            return make(tag, className + ';' + memberName + ';' + sig);
        }
        public static Constant forField(String cname, String fname, String sig) {
            return forMember(cname, false, fname, true, sig);
        }
        public static Constant forMethod(String cname, String mname, String sig) {
            return forMember(cname, false, mname, false, sig);
        }
        public static Constant forInterfaceMethod(String cname, String mname, String sig) {
            return forMember(cname, true, mname, false, sig);
        }
        public static Constant forNameAndType(String mname, String sig) {
            if (mname.indexOf(';') >= 0)
                die("illegal descriptor");
            return make(CONSTANT_NameAndType, mname + ';' + sig);
        }

        // For completeness, but you can just use normal Java constant syntax:
        public static String forUtf8(String symbol)
            { return symbol; }
        public static Integer forValue(int val)
            { return val; }
        public static Float forValue(float val)
            { return val; }
        public static Long forValue(long val)
            { return val; }
        public static Double forValue(double val)
            { return val; }
        public static Constant forValue(String val)
            { return forString(val); }
    }

    private static final HashSet<Class<?>> LEGAL_CON_CLASSES;
    static {
	HashSet<Class<?>> constantClassSet=
	    new HashSet<Class<?>>();
	Collections.addAll(constantClassSet,
		String.class,       // utf8 symbols
	        Integer.class, Float.class, Long.class, Double.class, // primitive values
	        Constant.class      // everything else
	);
	LEGAL_CON_CLASSES=constantClassSet;
    }
        
    static void verifyLegalConstant(Object con) {
        if (con == null || !LEGAL_CON_CLASSES.contains(con.getClass()))
            die("bad constant "+con);
    }
    public Object putSymbolPatch(String con, String patch) {
        return putPatch0(con, patch);
    }
    public Object putValuePatch(Object con, Object patch) {
        if (con instanceof String)
            con = Constant.forString((String)con);
        return putPatch0(con, patch);
    }
    /** Add the given constant to the patch table.
     *  As a special restriction, the constant cannot be a string,
     *  because it is possibly ambiguous whether it means a Utf8
     *  or a string constant value.
     */
    public Object putPatch(Object con, Object patch) {
        if (con instanceof String)
            die("ambiguous string; use putValuePatch or putSymbolPatch");
        if (con instanceof Class)
            con = Constant.forClass((Class<?>) con);
        return putPatch0(con, patch);
    }
    Object putPatch0(Object con, Object patch) {
        verifyLegalConstant(con);
        if (patchMap == null)
            patchMap = new HashMap<Object,Object>();
        return patchMap.put(con, patch);
    }

    // Note: Do not refactor the calls to checkHostClass unless you
    //       also adjust this constant:
    private static int CHC_CALLERS = 3;
    public AnonymousClassLoader() {
        this.hostClass = checkHostClass(null);
    }
    public AnonymousClassLoader(Class<?> hostClass) {
        this.hostClass = checkHostClass(hostClass);
    }
    public AnonymousClassLoader(Class<?> hostClass, byte[] classFile) {
        this.hostClass = checkHostClass(hostClass);
        setClassFile(classFile);
    }
    public AnonymousClassLoader(Class<?> hostClass, byte[] classFile, int offset, int length) {
        this.hostClass = checkHostClass(hostClass);
        setClassFile(classFile, 0, classFile.length);
    }
    public AnonymousClassLoader clone() {
        return new AnonymousClassLoader(this);
    }
    private AnonymousClassLoader(AnonymousClassLoader orig) {
        this.hostClass = orig.hostClass;
        this.classFile = orig.classFile;
        this.patchMap = orig.patchMap;
        this.classFileCP = orig.classFileCP;
    }

    private static Class<?> checkHostClass(Class<?> hostClass) {
        // called only from the constructor
        // does a context-sensitive check on caller class
        // CC[0..3] = {Reflection, this.checkHostClass, this.<init>, caller}
        Class<?> caller = sun.reflect.Reflection.getCallerClass(CHC_CALLERS);

        if (caller == null) {
            // called from the JVM directly
            if (hostClass == null)
                return AnonymousClassLoader.class; // anything central will do
            return hostClass;
        }

        if (hostClass == null)
            hostClass = caller; // default value is caller itself

        // anonymous class will access hostClass on behalf of caller
        Class<?> callee = hostClass;

        if (caller == callee)
            // caller can always nominate itself to grant caller's own access rights
            return hostClass;

        // normalize caller and callee to their top-level classes:
        for (Class<?> outer = caller.getDeclaringClass(); outer != null;
             caller = outer) {
            outer= outer.getDeclaringClass();
        }
        for (Class<?> outer = callee.getDeclaringClass(); outer != null;
             callee = outer) {
            outer= outer.getDeclaringClass();
        }
        if (caller == callee)
            return caller;

        ClassLoader callerCL = caller.getClassLoader();
        if (callerCL == null) {
            // caller is trusted code, so accept the proposed hostClass
            return hostClass;
        }

        // %%% should do something with doPrivileged, because trusted
        // code should have a way to execute on behalf of
        // partially-trusted clients

        // Does the caller have the right to access the private
        // members of the callee?  If not, raise an error.
        final int ACC_PRIVATE = 2;
        try {
            sun.reflect.Reflection.ensureMemberAccess(caller, callee, null, ACC_PRIVATE);
        } catch (IllegalAccessException ee) {
            throw new IllegalArgumentException(ee);
        }

        return hostClass;
    }

    public Class<?> loadClass() {
        // Make a malloc version, for defineClass:
        long classFileMalloc = unsafe.allocateMemory(classFile.length);
        unsafe.copyMemory(classFile, bufHeaderLen, null, classFileMalloc, classFile.length);
        try {
            return unsafe.defineAnonymousClass(hostClass,
                                               classFileMalloc, classFile.length,
                                               makePatchArray());

        } finally {
            unsafe.freeMemory(classFileMalloc);
        }
    }

    Object[] makePatchArray() {
        if (patchMap == null ||
            patchMap.isEmpty())
            return null;

        
        //System.out.println("pm = "+patchMap);
        ensureCP();
        
        //DEBUG
        //for(int index=0;index<classFileCP.size();index++)
        //    System.out.println(index+" "+classFileCP.get(index));
        
        //System.out.println("cp = "+classFileCP);
        Object[] pa = new Object[classFileCP.size()];
        for (Map.Entry<Object,Object> e : patchMap.entrySet()) {
            Object con   = e.getKey();
            Object patch = e.getValue();
            int i = classFileCP.indexOf(con);
            //System.out.println("constant pool index "+i+ " "+con);
            
            if (i >= 0) {
        	//System.err.println("patch index "+i+ " "+patch);
                pa[i] = patch;
            }
        }
        //System.out.println("pa = "+Arrays.asList(pa));
        return pa;
    }

    static void die(String msg) {
	throw new IllegalArgumentException(msg);
    }
    static void die(String msg, Throwable cause) {
	throw new IllegalArgumentException(msg, cause);
    }

    private void ensureCP() {
        if (classFileCP == null)
            // parse class file and cache the result
            new CPParser();
    }

    // small parser lives here, loaded on demand:
    private class CPParser {
        Object[] cpVals;
        byte[]   cpTags;

        CPParser() {
            parse();
            resolve();
            // pass results to outer instance:
            classFileCP = Arrays.asList(cpVals);
        }

        void parse() {
            try {
                DataInputStream in = new DataInputStream(new ByteArrayInputStream(classFile));
                int magic = in.readInt();
                if (magic != 0xCAFEBABE)  die("magic number");
                int maj = in.readUnsignedShort(), min = in.readUnsignedShort();
                int len = in.readUnsignedShort();
                if (!(len >= 1))  die("cp length");
                cpVals = new Object[len];
                cpTags = new byte[len];
                for (int i = 1, nexti; i < len; i = nexti) {
                    nexti = i+1;
                    byte tag = cpTags[i] = in.readByte();
                    Object obj = null;
                    switch (tag) {
                    case CONSTANT_Utf8:     obj = in.readUTF(); break;
                    case CONSTANT_Integer:  obj = in.readInt(); break;
                    case CONSTANT_Float:    obj = in.readFloat(); break;
                    case CONSTANT_Long:     obj = in.readLong(); ++nexti; break;
                    case CONSTANT_Double:   obj = in.readDouble(); ++nexti; break;
                    case CONSTANT_Class:    // fall through:
                    case CONSTANT_String:   obj = new int[] { in.readUnsignedShort() };
                                            break;

                    case CONSTANT_Fieldref:           // fall through:
                    case CONSTANT_Methodref:          // fall through:
                    case CONSTANT_InterfaceMethodref: // fall through:
                    case CONSTANT_NameAndType:
                        obj = new int[] { in.readUnsignedShort(), in.readUnsignedShort() };
                        break;
                    }
                    cpVals[i] = obj;
                }
            } catch (IOException ee) {
                // format problem
                die(ee.toString(), ee);
            }
        }

        void resolve() {
            // clean out the int[] values, which are temporary
            for (int beg = 1, end = cpVals.length-1, beg2, end2;
                 beg <= end;
                 beg = beg2, end = end2) {
                beg2 = end; end2 = beg-1;
                //System.out.println("CP resolve pass: "+beg+".."+end);
                for (int i = beg; i <= end; i++) {
                    Object obj = cpVals[i];
                    int    tag = cpTags[i];
                    if (!(obj instanceof int[]))  continue;
                    int[] pair = (int[]) obj;
                    String name1, name2;
                    switch (tag) {
                    case CONSTANT_String:
                        name1 = (String) cpVals[pair[0]];
                        obj = Constant.forString(name1);
                        break;
                    case CONSTANT_Class:
                        name1 = (String) cpVals[pair[0]];
                        // use the external form favored by Class.forName:
                        name1 = name1.replace('/', '.');
                        obj = Constant.forClass(name1);
                        break;
                    case CONSTANT_NameAndType:
                        name1 = (String) cpVals[pair[0]];
                        name2 = (String) cpVals[pair[1]];
                        obj = Constant.forNameAndType(name1, name2);
                        break;
                    case CONSTANT_Fieldref:           // fall through:
                    case CONSTANT_Methodref:          // fall through:
                    case CONSTANT_InterfaceMethodref: // fall through:
                        Object cls = cpVals[pair[0]];
                        Object des = cpVals[pair[1]];
                        if (!(cls instanceof Constant) ||
                            !(des instanceof Constant)) {
                            // one more pass is needed
                            if (beg2 > i)  beg2 = i;
                            if (end2 < i)  end2 = i;
                            continue;
                        }
                        name1 = ((Constant)cls).checkTag(CONSTANT_Class);
                        name2 = ((Constant)des).checkTag(CONSTANT_NameAndType);
                        obj = Constant.make(tag, name1 + ';' + name2);
                        break;
                    default:
                        continue;
                    }
                    cpVals[i] = obj;
                }
            }
        }
    }
    private static final int
        CONSTANT_Utf8 = 1,
        //CONSTANT_Unicode = 2,               /* unused */
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
}
