// This work is Copyright(c) 2007 Neal M Gafter.  All rights reserved.
/*
 * @test  /nodynamiccopyright/
 * @summary Test explicit type parameters on generic enum constructor
 * @author gafter
 *
 * @clean GenericEnum
 * @compile GenericEnum.java
 */

import java.util.Collection;
import java.util.Collections;

public enum GenericEnum {
    <Integer> A (Integer.class, Collections.<Integer>emptySet())
    ;

    <T> GenericEnum(Class<T> clazz, Collection<T> coll) {}
}
// This work is Copyright(c) 2007 Neal M Gafter.  All rights reserved.
/*
 * @test  /nodynamiccopyright/
 * @summary Test explicit type parameters on generic enum constructor
 * @author gafter
 *
 * @clean GenericEnum
 * @compile GenericEnum.java
 */

import java.util.Collection;
import java.util.Collections;

public enum GenericEnum {
    <Integer> A (Integer.class, Collections.<Integer>emptySet())
    ;

    <T> GenericEnum(Class<T> clazz, Collection<T> coll) {}
}
