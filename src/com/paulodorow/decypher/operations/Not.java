package com.paulodorow.decypher.operations;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Not extends Predicate<Not> {

	IsOperand<?> operand;
	
	public Not() {

	}
	
	public Not(IsOperand<?> operand) {
		this();
		not(operand);
	}
	
	public Not not(IsOperand<?> operand) {
		this.operand = operand;
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return "NOT ".concat(context.getInContext(operand).toReturnableString(context));
	}
	
}
