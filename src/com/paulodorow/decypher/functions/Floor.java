package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Floor extends Function<Floor> {

	private IsOperand<?> operand;

	public Floor() {
		
	}
	
	public Floor(IsOperand<?> operand) {
		setOperand(operand);
	}

	public void setOperand(IsOperand<?> operand) {
		this.operand = operand;
	}
	
	public IsOperand<?> getOperand() {
		return operand;
	}
	
	public Floor withOperand(IsOperand<?> operand) {
		setOperand(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("floor(%s)", context.getInContext(operand).toReturnableString(context));
	}
	
}
