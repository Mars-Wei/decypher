package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;
import com.paulodorow.decypher.NoContext;

public class RTrim extends Function<RTrim> {

	private IsOperand<?> operand;

	public RTrim() {
		
	}
	
	public RTrim(IsOperand<?> operand) {
		setOperand(operand);
	}

	public void setOperand(IsOperand<?> operand) {
		this.operand = operand;
	}
	
	public IsOperand<?> getOperand() {
		return operand;
	}
	
	public RTrim withOperand(IsOperand<?> operand) {
		setOperand(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("rtrim(%s)", context.getInContext(operand).toReturnableString(context));
	}
	
	@Override
	public String toString() {
		return toReturnableString(NoContext.get());
	}
	
}
