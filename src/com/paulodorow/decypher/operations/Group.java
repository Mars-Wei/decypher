package com.paulodorow.decypher.operations;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperation;
import com.paulodorow.decypher.NoContext;

public class Group extends Operation<Group> {

	IsOperation<?> operation;
	
	public Group() {
		
	}
	
	public Group(IsOperation<?> operation) {
		this.operation = operation;
	}
	
	public Group withOperation(IsOperation<?> operation) {
		this.operation = operation;
		return this;
	}
	
	@Override
	public String toString() {
		return toReturnableString(NoContext.get());
	}

	@Override
	public String toReturnableString(HasContext context) {
		return "(".concat(context.getInContext(operation).toReturnableString(context)).concat(")");
	}
	
}
