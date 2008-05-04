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

package concisecolls;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;

/*
 * @test
 * @summary check initialization of Collections
 * @author shams mahmood
 *
 * @compile CollectionsInitialization.java
 */

public class CollectionsInitialization
{

  public void sampleMethod() {

    java.util.List<String> l1 = new ArrayList<String>(2) ["one", "two"];
    HashSet<Integer> s1 = new HashSet<Integer>() [ 1, 2, 3, ];
    TreeMap<Integer, String> m1 = new TreeMap<Integer, String>() [ 1: "one", 2 : "two"];
    java.util.List<String> l2 = new LinkedList<String>() {
        public boolean add(String s) {
          System.out.println("Adding: " + s);
          return super.add(s);
        };
      } ["one", "two"];
  }
}