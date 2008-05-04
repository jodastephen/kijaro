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
import java.util.List;
import java.util.TreeMap;

/*
 * @test
 * @summary check initialization of Collections classes and passing as arguments to methods
 * @author shams mahmood
 *
 * @compile CollectionsInitialization2.java
 */

public class CollectionsInitialization2
{

  public void sample() {
    sample2(new ArrayList<String>(4) ["1", "2", "3", "4"] );
  }

  public void sample2( List<String> l ) {
    System.out.println( l );
  }

  public void sample3() {
    boolean b = new HashSet<String>() ["1", "2", "3", "4"].add( "5" );
    boolean b2 = new HashMap<String, String>() ["1":"one", "2":"two"].add( "1" );
  }

  public static void sample4() {
    sample5(new ArrayList<String>(4) ["1", "2", "3", "4"] );
  }

  public static void sample5( List<String> l ) {
    System.out.println( l );
  }

  public static void sample6() {
    boolean b = new HashSet<String>() ["1", "2", "3", "4"].add( "5" );
  }
}