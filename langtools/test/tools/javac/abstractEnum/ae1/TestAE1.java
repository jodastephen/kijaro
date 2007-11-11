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
package abstractEnum.ae1;

/**
 * User: freds
 * Date: Jun 15, 2007
 * Time: 11:52:34 PM
 *
 * @author freds
 * @test @(#)TestAE1.java 1.7 08/06/07
 * @bug 6507006
 * @summary support for abstract enum
 * @library ../..
 * @compile AE1.java E1.java TestAE1.java
 * @run main abstractEnum.ae1.TestAE1
 */
public class TestAE1 {
    AE1 ae1;

    public static void main(String[] args) {
        TestAE1 t = new TestAE1();
        t.ae1 = E1.one;
        System.out.println("Youpi " + t.ae1.getI());
    }
}