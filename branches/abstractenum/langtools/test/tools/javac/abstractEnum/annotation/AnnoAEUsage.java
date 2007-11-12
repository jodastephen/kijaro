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
package abstractEnum.annotation;

import java.lang.reflect.Field;

/*
 * @test @(#)AnnoAEUsage.java 1.7 08/06/07
 * @bug 6507006
 * @summary support for abstract enum
 * @author freds
 *
 * @library ../..
 * @compile AnnoAEUsage.java AnnoAETest.java AbstractE2.java E21.java E22.java
 * @run main abstractEnum.annotation.AnnoAEUsage
 */
public class AnnoAEUsage {
    @AnnoAETest(ae2 = E21.one)
    private String first;

    public static void main(String[] args) throws Exception {
        assert !Enum.class.isEnum();
        assert AbstractE2.class.isEnum();
        assert E21.class.isEnum();
        E21 e21 = Enum.valueOf(E21.class, "one");
        switch (e21) {
            case one:
                System.out.println("Got 1 ");
                break;
            default:
                assert false;
        }
        Field field = AnnoAEUsage.class.getDeclaredField("first");
        AnnoAETest anno = field.getAnnotation(AnnoAETest.class);
        AbstractE2 ae2 = anno.ae2();
        assert "one:0".equals(ae2.full());
    }
}
