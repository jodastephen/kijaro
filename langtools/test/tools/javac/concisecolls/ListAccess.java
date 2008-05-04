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
 * @summary check List access with array-like indexing
 * @author shams mahmood
 *
 * @compile ListAccess.java
 */

public class ListAccess {

  public void accessList(
      java.util.List<String> list ) {
    String s1 = list[0];
    String s2 = "two";
    list[1] = s2;
  }

  public void accessArrayList(
      java.util.ArrayList<String> list ) {
    String s1 = list[0];
    String s2 = "two";
    list[1] = s2;
  }

}