package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;
import com.paulodorow.decypher.NoContext;

public class Log10 extends Function<Log10> {

	private IsOperand<?> operand;

	public Log10() {
		
	}
	
	public Log10(IsOperand<?> operand) {
		setOperand(operand);
	}

	public void setOperand(IsOperand<?> operand) {
		this.operand = operand;
	}
	
	public IsOperand<?> getOperand() {
		return operand;
	}
	
	public Log10 withOperand(IsOperand<?> operand) {
		setOperand(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("log10(%s)", context.getInContext(operand).toReturnableString(context));
	}
	
	@Override
	public String toString() {
		return toReturnableString(NoContext.get());
	}
	
}
