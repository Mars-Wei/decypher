package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Cot extends Function<Cot> {

	private IsOperand<?> operand;

	public Cot() {
		
	}
	
	public Cot(IsOperand<?> operand) {
		setOperand(operand);
	}

	public void setOperand(IsOperand<?> operand) {
		this.operand = operand;
	}
	
	public IsOperand<?> getOperand() {
		return operand;
	}
	
	public Cot withOperand(IsOperand<?> operand) {
		setOperand(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("cot(%s)", context.getInContext(operand).toReturnableString(context));
	}
	
}
