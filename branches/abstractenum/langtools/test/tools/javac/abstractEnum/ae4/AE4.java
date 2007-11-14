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
package abstractEnum.ae4;

/*
 * User: freds
 * Date: Jun 10, 2007
 * Time: 12:45:17 PM
 *
 * @test @(#)AE4.java 1.7 10/06/07
 * @bug 6570766
 * @summary support untyped generics in abstract enum
 * @author freds
 *
 * @library ../..
 * @compile AE4.java
 * @run main abstractEnum.ae4.AE4
 */
public class AE4 {
    AbstractE4 ae2;

    public static void main(String[] args) {
        AbstractE4<String,Long> tt = E21.two;
        AE4 t = new AE4();
        t.ae2 = E21.one;
        assert 0 == t.ae2.f();
        assert "one:0".equals(t.ae2.full());
        assert "E21".equals(t.ae2.get());
        t.ae2 = E22.twenty2;
        assert "twenty2:0".equals(t.ae2.full());
        assert 22 == t.ae2.get();
        E21 e21 = E21.one;
        switch (e21) {
            case one:
                System.out.println("Got 1 ");
                break;
            case two:
                assert false;
                break;
        }
    }
}

abstract enum AbstractE4<T,V> {
    public V f() {
        // Bug to solve about E21 not being abstract
        return null;
   }

    public String full() {
        return name() + ":" + ordinal();
    }

    public abstract T get();
}

enum E21<V> extends AbstractE4<String,V> {
    <Short> one {
        public Short f() {
            return new Short((short)11);
        }},
    <Long> two {
        public Long f() {
            return new Long(22);
        }};

    public String get() {
        return "E21";
    }
}

enum E22 extends AbstractE4<Integer,Integer> {
    twenty2, twenty3;

    public Integer get() {
        if (this == twenty2)
            return 22;
        return 23;
    }

    protected Object innerF() {
        return get();
    }
}

