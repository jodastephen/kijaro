/*
 * Copyright 2003-2004 Sun Microsystems, Inc.  All Rights Reserved.
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
 */

/*
 * @test
 * @bug 4872708 5058132
 * @summary Improper signature for valueOf
 * @author gafter
 *
 * @compile -source 1.5 ValueOf.java
 * @run main ValueOf
 */

public enum ValueOf {
    a, b, c, d, e;
    public static void main(String[] args) {
        String[] names = {"b", "a"};
        if (valueOf("a") != a) throw new Error();
        if (valueOf("d") != d) throw new Error();
        if (valueOf("e") != e) throw new Error();
        try {
            ValueOf f = valueOf("f");
            throw new Error();
        } catch (IllegalArgumentException ex) {
        }
    }
}