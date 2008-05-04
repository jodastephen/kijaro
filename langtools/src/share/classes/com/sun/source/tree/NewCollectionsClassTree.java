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

package com.sun.source.tree;

/**
 * A tree node to declare a new instance of a class followed by a collection initializer.
 *
 * For example:
 * <pre>
 *   new <em>identifier</em> ( ) [ <em>initializers</em> ]
 *
 *   new <em>identifier</em> ( <em>arguments</em> ) [ <em>initializers</em> ]
 *
 *   new <em>typeArguments</em> <em>identifier</em> ( <em>arguments</em> )
 *       <em>classBody-with-initilizers</em> [ <em>initializers</em> ]
 *
 *   <em>enclosingExpression</em>.new <em>identifier</em> ( <em>arguments</em> ) [ <em>initializers</em> ]
 * </pre>
 *
 * @author Shams Mahmood
 * @since unknown
 */
public interface NewCollectionsClassTree
  extends NewClassTree
{
  CollectionsInitializerTree getCollectionsInitializer();
}
