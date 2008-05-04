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
/*
 * @test
 * @summary check List and Map access simultaneously with array-like indexing
 * @author shams mahmood
 *
 * @compile ListMapAccess.java
 */

public class ListMapAccess {

  public void accessMapLsy(
      java.util.Map<String, Double> map,
      java.util.List<Double> list ) {
    map["one"] = list[0];
    list[1] = map["two"];
    map["three"] = list[2] + 2.0;
  }

  public void accessHashMapArrayList(
      java.util.HashMap<String, Double> map,
      java.util.ArrayList<Double> list ) {
    map["one"] = list[0];
    list[1] = map["two"];
    map["three"] = list[2] + 2.0;
  }
}