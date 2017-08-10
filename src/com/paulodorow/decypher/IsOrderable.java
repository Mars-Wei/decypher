package com.paulodorow.decypher;

public interface IsOrderable<D> extends IsReturnable<D> {
	
	default String toOrderableString(HasContext context) {
		return toReturnableString(context);
	}

}
