package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class ToFloat extends Function<ToFloat> {

	private IsOperand<?> operand;

	public ToFloat() {
		
	}
	
	public ToFloat(IsOperand<?> operand) {
		setOperand(operand);
	}

	public void setOperand(IsOperand<?> operand) {
		this.operand = operand;
	}
	
	public IsOperand<?> getOperand() {
		return operand;
	}
	
	public ToFloat withOperand(IsOperand<?> operand) {
		setOperand(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("toFloat(%s)", context.getInContext(operand).toReturnableString(context));
	}
	
}
