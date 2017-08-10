package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.NoContext;

public class Pi extends Function<Pi> {

	@Override
	public String toString() {
		return toReturnableString(NoContext.get());
	}

	@Override
	public String toReturnableString(HasContext context) {
		return "pi()";
	}
	
}
