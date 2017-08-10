package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Sqrt extends Function<Sqrt> {

	private IsOperand<?> operand;

	public Sqrt() {
		
	}
	
	public Sqrt(IsOperand<?> operand) {
		setOperand(operand);
	}

	public void setOperand(IsOperand<?> operand) {
		this.operand = operand;
	}
	
	public IsOperand<?> getOperand() {
		return operand;
	}
	
	public Sqrt withOperand(IsOperand<?> operand) {
		setOperand(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("sqrt(%s)", context.getInContext(operand).toReturnableString(context));
	}
	
}
