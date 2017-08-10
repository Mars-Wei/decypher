package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.NoContext;

public class Rand extends Function<Rand> {

	@Override
	public String toString() {
		return toReturnableString(NoContext.get());
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return "rand()";
	}
	
}
