/*
 * Copyright (c) 2007 Frederic Simon.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 *
 */
package abstractEnum.ae3;

/*
 * User: freds
 * Date: Jun 10, 2007
 * Time: 12:45:17 PM
 *
 * @test @(#)AE3.java 1.7 08/06/07
 * @bug 6507006
 * @summary support for abstract enum
 * @author freds
 *
 * @library ../..
 * @compile AE3.java
 * @run main abstractEnum.ae3.AE3
 */
public class AE3 {
    AbstractE3 ae3;

    public static void main(String[] args) {
        System.out.println("Before");
        AE3 t = new AE3();
        t.ae3 = E31.one;
        System.out.println("Youpi " + t.ae3.f() + "  " + t.ae3.full());
        t.ae3 = E32.twenty2;
        System.out.println("Youpi " + t.ae3.full());
        System.out.println("After");
    }
}

enum E31 extends AbstractE3 {
    one(11, "eleven"), two(12, "douze");

    E31(int v, String p) {
        super(v, p);
    }
}

abstract enum AbstractE3 {
    int i;
    String n;

/*
    AbstractE3() {
        i = -1;
        n = "unknown";
    }
*/

    AbstractE3(int v, String p) {
        i = v;
        n = p;
    }

    public int f() {
        return i;
    }

    public String full() {
        return n + ":" + ordinal();
    }
}

enum E32 extends AbstractE3 {
    twenty2, twenty3;

    E32() {
        super(-39, "coucou");
    }
}

