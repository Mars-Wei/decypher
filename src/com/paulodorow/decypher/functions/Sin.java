package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Sin extends Function<Sin> {

	private IsOperand<?> operand;

	public Sin() {
		
	}
	
	public Sin(IsOperand<?> operand) {
		setOperand(operand);
	}

	public void setOperand(IsOperand<?> operand) {
		this.operand = operand;
	}
	
	public IsOperand<?> getOperand() {
		return operand;
	}
	
	public Sin withOperand(IsOperand<?> operand) {
		setOperand(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("sin(%s)", context.getInContext(operand).toReturnableString(context));
	}
	
}
