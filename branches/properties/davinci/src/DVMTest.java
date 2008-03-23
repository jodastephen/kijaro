/*
  javac  -d Classes  DVMTest.java
  java -esa -ea  -Xbootclasspath/p:$REPO/Classes/bootcp -cp $REPO/Classes  DVMTest

  (dbx) r -ea -esa  -Xbootclasspath/p:$REPO/Classes/bootcp -cp $REPO/Classes  DVMTest
  (dbx) (cd $REPO/Classes; ./MAKE.sh) && r

  known-good JVM internal version:
  java -Xinternalversion
  OpenJDK Server VM (12.0-b01-internal-jvmg) for solaris-x86 JRE (1.7.0), built on Jan 22 2008 00:14:41 by "jrose" with Workshop 5.8
 */

import java.util.*;
import java.io.*;
import java.dyn.AnonymousClassLoader;

/** Test for prototype of anonymous class loader.
 *  Features:
 *  - loads an arbitrary class from bytecodes
 *  - puts class in class hierarchy but *not* in any class loader
 *  - piggybacks on a "host class" as if it were an inner class
 *  - elements of the anonymous constant pool can be patched easily
 *  - string constants can be replaced by arbitrary objects in CP
 *  Why the patching stuff?  Mainly, it makes some use cases much easier.
 *  Second, the constant pool needed some internal patching anyway,
 *  to anonymize the loaded class itself.  Finally, if you are going
 *  to use this seriously, you'll want to build anonymous classes
 *  on top of pre-existing anonymous classes, and that requires patching.
 */
class DVMTest {
    public static void main(String... av) throws Exception {
        System.out.println("hello, world, from DVM!");
        if (Arrays.equals(av, new String[]{ "-raw" }))
            // this will fail if DVMTest is not on the boot class path
            { testRaw(); return; }
        testAPI();
    }

    static void testAPI() throws Exception {
        AnonymousClassLoader acl = new AnonymousClassLoader();
        acl.setClassFile(Temp.class);
        // Make a bunch of copies of Temp, nested in this class.
        Class hostClass = DVMTest.class;
        List<Class> classes = new ArrayList<Class>();
        List<Inter> objects = new ArrayList<Inter>();
        for (int n = 0; n <= 3; n++) {
            Class acls = Temp.class;
            if (n > 0) {
                Object oddity = Arrays.asList("odd", "constant", n, "!");
                // replace Utf8 "dummyStatic" with "foo"+n:
                acl.putSymbolPatch("dummyStatic", "foo"+n);
                // replace Integer 123456 with n*1000:
                acl.putPatch(-123456, n*1000);
                // change name of local routine, just for kicks:
                acl.putSymbolPatch("funny", "hilarious"+n);
                // maybe patch the super class
                acl.putPatch(TempBase.class,
                             (n >= 2) ? classes.get(n-2) : null);
                // change string constant to a pseudo-string:
                acl.putValuePatch("this space for rent",
                                  oddity);
                acls = acl.loadClass();
            }
            System.out.println("loaded "+acls);
            for (int i = 0; i < n; i++)
                assert(classes.get(i) != acls);
            classes.add(acls);
        }
        exercise(classes, objects);
    }

    static void testRaw() throws Exception {
        // ignore two warnings on this line:
        sun.misc.Unsafe unsafe = sun.misc.Unsafe.getUnsafe();

        final int bufHeaderLen = unsafe.arrayBaseOffset(byte[].class);  // 12 or 24

        // Load up the bytecodes of Temp (our template class).
        Class tempClass = DVMTest.Temp.class;
        byte[] tempBytes = readFile(tempClass.getResourceAsStream(tempClass.getName()+".class"));
        System.out.println("read "+tempBytes.length+" bytes from "+tempClass.getName());

        // Make a malloc version, for defineClass:
        long tempBytesMalloc = unsafe.allocateMemory(tempBytes.length);
        unsafe.copyMemory(tempBytes, bufHeaderLen, null, tempBytesMalloc, tempBytes.length);

        // Make a bunch of copies of Temp, nested in this class.
        Class hostClass = DVMTest.class;
        List<Class> classes = new ArrayList<Class>();
        List<Inter> objects = new ArrayList<Inter>();
        for (int n = 0; n <= 3; n++) {
            Class acls = tempClass;
            if (n > 0) {
                Object oddity = Arrays.asList("odd", "constant", n, "!");
                Object[] cpPatches
                    = makePatches(tempBytes,
                                  // replace Utf8 "dummyStatic" with "foo"+n:
                                  "dummyStatic", "foo"+n,
                                  // replace Integer 123456 with n*1000:
                                  -123456, n*1000,
                                  // change name of local routine, just for kicks:
                                  "funny", "hilarious"+n,
                                  // maybe patch the super class
                                  TempBase.class,
                                  (n >= 2) ? classes.get(n-2) : null,
                                  // change string constant to a pseudo-string:
                                  Arrays.asList("String", "this space for rent"),
                                  oddity
                                  );
                acls = unsafe.defineAnonymousClass(hostClass,
                                                   tempBytesMalloc, tempBytes.length,
                                                   cpPatches);
            }
            System.out.println("loaded "+acls);
            for (int i = 0; i < n; i++)
                assert(classes.get(i) != acls);
            classes.add(acls);
        }
        exercise(classes, objects);
    }

    static void exercise(List<Class> classes,
                  List<Inter> objects) throws Exception {
        /*
        for (Class acls : classes) {
            unsafe.ensureClassInitialized(acls);
            System.out.println("initialized "+acls);
        }
        */
        for (Class acls : classes) {
            Inter obj = (Inter) acls.newInstance();
            assert(obj.getClass() == acls);
            System.out.println("created "+obj);
            objects.add(obj);
        }
        System.out.println("================");
        for (Inter obj : objects) {
            obj.foo();
            System.out.println("----------------");
        }
        System.out.println("================");

        new TempBase();         // don't ask why; has to do with access$ methods
    }

    private static Object[] makePatches(byte[] classFile, Object... patches) {
        Object[] cp = CPParser.resolve(CPParser.parse(classFile));
        //System.out.println(Arrays.deepToString(cp));
        /* Sample output:
           ---
           [[637, 10, 9, 7, ...],
            [Methodref, java/lang/Object, <init>, ()V],
            [Fieldref, java/lang/System, out, Ljava/io/PrintStream;],
            [Class, java/lang/StringBuilder], ...,
            [Fieldref, DVMTest, dummyStatic, Ljava/lang/String;],
            [Class, DVMTest$Temp], ...,
            toString, java/io/PrintStream, println, (Ljava/lang/String;)V,
            dummyStatic, Ljava/lang/String;]
           ---
        */

        for (int j = 0; j < patches.length; j += 2) {
            Object pattern = patches[j];
            if (pattern instanceof Class) {
                patches[j] = Arrays.asList("Class", ((Class)pattern).getName());
            }
        }

        Object[] cpPatches = new Object[cp.length];
        for (int i = 1; i < cp.length; i++) {
            Object obj = cp[i];
            Object patch = null;
            for (int j = 0; j < patches.length; j += 2) {
                Object pattern = patches[j];
                if (pattern.equals(obj)) {
                    patch = patches[j+1];
                    break;
                }
            }
            cpPatches[i] = patch;
        }

        //System.out.println(Arrays.deepToString(cpPatches));
        /* Sample output:
           ---
           [null, null, null, ...,
           foo2, null]
           ---
        */
        return cpPatches;
    }

    /** Read the given stream until EOF.  Return everything in an array. */
    // Ouch, why isn't this in rt.jar already??
    static byte[] readFile(InputStream in) throws IOException {
        byte[][] bufs  = new byte[7][];
        int      nbufs = 0;
        int      totalLen = 0;
        byte[]   lastBuf;
        int      lastLen;
        int      buflen = (1 << 6);
    mainLoop:
        for (;;) {
            // fill up one buffer at a time
            int avail = (int)in.available();
            if (avail <= buflen) {
                avail = buflen; // better guess, in case in.avail is broken
                if (buflen < (1 << 14))  buflen <<= 1;
            }
            byte[] buf = new byte[avail];
            int    len = 0;
            while (len < avail) {
                int nr = in.read(buf, len, avail-len);
                if (nr <= 0) {
                    if (len == 0 && nbufs > 0) {
                        lastBuf = bufs[--nbufs];
                        lastLen = lastBuf.length;
                    } else {
                        lastBuf = buf;
                        lastLen = len;
                        totalLen += lastLen;
                    }
                    break mainLoop;
                }
                len += nr;
            }
            // add the buffer to the list so we can look for more
            if (nbufs == bufs.length)
                bufs = Arrays.copyOf(bufs, nbufs << 2);
            bufs[nbufs++] = buf;
            totalLen += avail;
        }
        in.close();
        if (nbufs == 0) {
            if (totalLen == lastBuf.length)
                return lastBuf; // common case, if in.avail was correct
            else
                return Arrays.copyOf(lastBuf, totalLen);
        }
        byte[] result = new byte[totalLen];
        int    fill   = 0;
        for (int i = 0; i < nbufs; i++) {
            byte[] buf = bufs[i];
            int    len = buf.length;
            System.arraycopy(buf, 0, result, fill, len);
            fill += len;
        }
        if (lastLen > 0) {
            System.arraycopy(lastBuf, 0, result, fill, lastLen);
        }
        assert(fill + lastLen == totalLen);
        return result;
    }

    private static String hostPrivateGreeting = "Hello";  // private but accessible

    static String dummyStatic = "DUMMY!";
    private static String foo1 = "foo1 string";
    private static String foo2 = "foo2 different string";
    private static String foo3 = "foo3 still different string";

    interface Inter {
        void foo();
    }

    static private class TempBase {
        TempBase() { } // it is package-private, but can access from host class
        public void foo() {
            System.out.println("...end of foo stuff");
        }
    }

    static private class Temp extends TempBase implements Inter {
        Temp() { } // it is package-private, but can access from host class
        public void foo() {
            System.out.println(hostPrivateGreeting + " from foo "+this);
            System.out.println("secret string is "+dummyStatic);
            System.out.println("funny number is "+funny());
            Object oddity = "this space for rent";
            System.out.println("wierd constant class is "+(oddity.getClass())+" for "+oddity.toString());
            super.foo();
        }
        private int funny() {
            int n = -123456;
            if (n >= 3000)
                throw new RuntimeException("that's not funny");
            return n;
        }
    }
}


/*
  Sample output:
----
hello, world, from DVM!
read 1345 bytes from DVMTest$Temp
loaded class DVMTest$Temp
loaded class DVMTest$Temp/18296328
loaded class DVMTest$Temp/13577344
loaded class DVMTest$Temp/24287316
created DVMTest$Temp@6eb38a
created DVMTest$Temp/18296328@1cd2e5f
created DVMTest$Temp/13577344@19f953d
created DVMTest$Temp/24287316@1fee6fc
================
Hello from foo DVMTest$Temp@6eb38a
secret string is DUMMY!
funny number is -123456
wierd constant class is class java.lang.String for this space for rent
...end of foo stuff
----------------
Hello from foo DVMTest$Temp/18296328@1cd2e5f
secret string is foo1 string
funny number is 1000
wierd constant class is class java.util.Arrays$ArrayList for [odd, constant, 1, !]
...end of foo stuff
----------------
Hello from foo DVMTest$Temp/13577344@19f953d
secret string is foo2 different string
funny number is 2000
wierd constant class is class java.util.Arrays$ArrayList for [odd, constant, 2, !]
Hello from foo DVMTest$Temp/13577344@19f953d
secret string is DUMMY!
funny number is -123456
wierd constant class is class java.lang.String for this space for rent
...end of foo stuff
----------------
Hello from foo DVMTest$Temp/24287316@1fee6fc
secret string is foo3 still different string
Exception in thread "main" java.lang.RuntimeException: that's not funny
	at DVMTest$Temp/24287316.hilarious3(DVMTest.java:237)
	at DVMTest$Temp/24287316.foo(DVMTest.java:229)
	at DVMTest.main(DVMTest.java:89)
----
*/
