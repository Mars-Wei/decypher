package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.IsOperation;
import com.paulodorow.decypher.NoContext;
import com.paulodorow.decypher.Token;

public abstract class Function<F> extends Token implements IsOperation<F> {

	@Override
	public String toString() {
		return toReturnableString(NoContext.get());
	}
	
}
