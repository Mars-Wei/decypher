package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Abs extends Function<Abs> {

	private IsOperand<?> operand;

	public Abs() {
		
	}
	
	public Abs(IsOperand<?> operand) {
		setOperand(operand);
	}

	public void setOperand(IsOperand<?> operand) {
		this.operand = operand;
	}
	
	public IsOperand<?> getOperand() {
		return operand;
	}
	
	public Abs withOperand(IsOperand<?> operand) {
		setOperand(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		//return String.format("abs(%s)", operand.toReturnableString(context));
		return String.format("abs(%s)", context.getInContext(operand).toReturnableString(context));
	}
	
}
