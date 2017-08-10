package com.paulodorow.decypher.operations;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class EndsWith extends Predicate<EndsWith> {

	IsOperand<?> operand;
	IsOperand<?> endsWith;
	
	public EndsWith() {

	}
	
	public EndsWith(IsOperand<?> operand, IsOperand<?> endsWith) {
		this();
		this.operand = operand;
		this.endsWith = endsWith;
	}
	
	public EndsWith withOperand(IsOperand<?> operand) {
		this.operand = operand;
		return this;
	}
	
	public EndsWith endsWith(IsOperand<?> endsWith) {
		this.endsWith = endsWith;
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("%s ENDS WITH %s", context.getInContext(operand).toReturnableString(context), context.getInContext(endsWith).toReturnableString(context));
	}

}
