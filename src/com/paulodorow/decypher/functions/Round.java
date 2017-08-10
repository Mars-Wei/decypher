package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Round extends Function<Round> {

	private IsOperand<?> operand;

	public Round() {
		
	}
	
	public Round(IsOperand<?> operand) {
		setOperand(operand);
	}

	public void setOperand(IsOperand<?> operand) {
		this.operand = operand;
	}
	
	public IsOperand<?> getOperand() {
		return operand;
	}
	
	public Round withOperand(IsOperand<?> operand) {
		setOperand(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("round(%s)", context.getInContext(operand).toReturnableString(context));
	}
	
}
