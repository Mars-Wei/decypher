package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Atan2 extends Function<Atan2> {

	private IsOperand<?> operand;

	public Atan2() {
		
	}
	
	public Atan2(IsOperand<?> operand) {
		setOperand(operand);
	}

	public void setOperand(IsOperand<?> operand) {
		this.operand = operand;
	}
	
	public IsOperand<?> getOperand() {
		return operand;
	}
	
	public Atan2 withOperand(IsOperand<?> operand) {
		setOperand(operand);
		return this;
	}
		
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("atan2(%s)", context.getInContext(operand).toReturnableString(context));
	}

}
