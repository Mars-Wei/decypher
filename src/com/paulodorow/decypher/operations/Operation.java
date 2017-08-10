package com.paulodorow.decypher.operations;

import com.paulodorow.decypher.IsOperation;
import com.paulodorow.decypher.NoContext;
import com.paulodorow.decypher.Token;

public abstract class Operation<O> extends Token implements IsOperation<O> {

	@Override
	public String toString() {
		return toReturnableString(NoContext.get());
	}
	
}
