package com.paulodorow.decypher;

/**
 * Implemented by any object that can appear in a <code>RETURN</code> statement:
 * <ul>
 * <li>Node</li>
 * <li>Relationship</li>
 * <li>Property</li>
 * <li>Constant value</li>
 * <li>Parameter</li>
 * <li>List</li>
 * <li>Function</li>
 * <li>Operation</li>
 * </ul>
 * <code>IsReturnable</code> objects keep record of their own alias
 * that is used in <code>RETURN</code> statements.
 * <p><code>&lt;D&gt;</code> is the implementing class itself.</p>
 */
public interface IsReturnable<D> extends Decorator<D> {


	/**
	 * Returns string appropriate for use in <code>RETURN</code> statements.
	 * <p>If entity has an alias it should be included as well.</p> 
	 * <p>A default implementation is provided but can be overridden 
	 * in inheriting classes to provide return string.</p> 
	 * <p><code>RETURN {some_data} AS {Alias}</code></p>
	 * @return 
	 */
	String toReturnableString(HasContext context);
	
	default Alias asAlias(String alias) {
		return new Alias(this, alias);
	}
	
}
