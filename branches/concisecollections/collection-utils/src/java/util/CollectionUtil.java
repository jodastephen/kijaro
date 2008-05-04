/*
 * Copyright 2008 Shams Mahmood.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  s
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
 * Please contact shams.mahmood@gmail.com if you need additional information or
 * have any questions.
 */

package java.util;

/**
 * @author <a href="mailto:shams.mahmood@gmail.com">shams.mahmood@gmail.com</a>
 * @created May 1, 2008 1:15:28 PM
 */

public class CollectionUtil
{

  public static <C extends java.util.Collection<E>, E extends Object>
  C fillCollection(C pCollection, E[] pElements) {

    if(pCollection != null && pElements != null) {
      // Add elements to the Collection
      for(int i = 0; i < pElements.length; i++) {
        pCollection.add( pElements[i] );
      }
    }
    return pCollection;
  }

  public static <M extends java.util.Map<K, V>, K extends Object, V extends Object>
  M fillMap(M pMap, K[] pKeys, V[] pValues) {

    if(pMap != null && pKeys != null && pValues != null) {

      if (pKeys.length != pValues.length) {
        throw new IllegalArgumentException("The Key-Value pairs have a mismatch in length");
      }
      // Add key-value pairs to the Map
      for(int i = 0; i < pKeys.length; i++) {
        pMap.put( pKeys[i], pValues[i] );
      }
    }
    return pMap;
  }

}
