package com.paulodorow.decypher.operations;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Contains extends Predicate<Contains> {

	IsOperand<?> operand;
	IsOperand<?> contains;
	
	public Contains() {

	}
	
	public Contains(IsOperand<?> operand, IsOperand<?> contains) {
		this();
		this.operand = operand;
		this.contains = contains;
	}
	
	public Contains withOperand(IsOperand<?> operand) {
		this.operand = operand;
		return this;
	}
	
	public Contains contains(IsOperand<?> contains) {
		this.contains = contains;
		return this;
	}

	@Override
	public String toReturnableString(HasContext context) {
		return String.format("%s CONTAINS %s", context.getInContext(operand).toReturnableString(context), context.getInContext(contains).toReturnableString(context));
	}
	
}
