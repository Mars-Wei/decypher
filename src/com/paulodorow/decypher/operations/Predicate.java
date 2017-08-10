package com.paulodorow.decypher.operations;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperation;
import com.paulodorow.decypher.IsPredicate;
import com.paulodorow.decypher.NoContext;
import com.paulodorow.decypher.Token;

public abstract class Predicate<O> extends Token implements IsOperation<O>, IsPredicate {

	@Override
	public String toPredicateString(HasContext context) {
		return toReturnableString(context);
	}
	
	@Override
	public String toString() {
		return toPredicateString(NoContext.get());
	}
	
}
