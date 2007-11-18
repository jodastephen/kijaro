package com.sun.source.tree;

/**
 * A tree node for a contract expression.
 *
 * For example:
 * <pre>
 *   static <em>type</em>
 * </pre>
 *
 */
public interface ContractTree extends Tree {
	
	Tree getType();

}
