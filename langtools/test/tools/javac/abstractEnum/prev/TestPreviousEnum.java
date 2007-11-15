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
package abstractEnum.prev;

/**
 * User: freds
 * Date: Jun 13, 2007
 * Time: 6:03:36 PM
 *
 * @test @(#)TestPreviousEnum.java 1.7 10/06/07
 * @bug 6507006
 * @summary support for abstract enum - non regression anonymous enum test
 * @author freds
 *
 * @library ../..
 * @compile PreviousTC.java PreviousTCIfc.java PreviousEnum.java PreviousEnumIfc.java TestPreviousEnum.java
 * @run main/othervm -ea abstractEnum.prev.TestPreviousEnum
 */
public class TestPreviousEnum {
    public static void main(String[] args) {
        PreviousTC tc = PreviousTC.one;
        assert !tc.getClass().isEnum();
        assert tc.getDeclaringClass().isEnum();
        assert 1 == tc.f();
        assert 2 == PreviousTC.two.f();
        switch (tc) {
            case one:
                System.out.println("Anonymous Got 1 ");
                break;
            case two:
                assert false;
        }
        PreviousEnum en = PreviousEnum.one;
        switch (en) {
            case one:
                System.out.println("Got 1 ");
                break;
            case two:
                assert false;
        }
        assert PreviousEnum.valueOf("one") == en;
        assert Enum.valueOf(PreviousEnum.class,"two").ordinal() == 1;
    }
}
