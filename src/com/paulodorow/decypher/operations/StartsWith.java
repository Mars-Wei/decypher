package com.paulodorow.decypher.operations;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class StartsWith extends Predicate<StartsWith> {

	IsOperand<?> operand;
	IsOperand<?> startsWith;
	
	public StartsWith() {

	}
	
	public StartsWith(IsOperand<?> operand, IsOperand<?> startsWith) {
		this();
		this.operand = operand;
		this.startsWith = startsWith;
	}
	
	public StartsWith withOperand(IsOperand<?> operand) {
		this.operand = operand;
		return this;
	}
	
	public StartsWith startsWith(IsOperand<?> startsWith) {
		this.startsWith = startsWith;
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("%s STARTS WITH %s", context.getInContext(operand).toReturnableString(context), context.getInContext(startsWith).toReturnableString(context));
	}

	
}
