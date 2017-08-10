package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Min extends AggregateFunction<Min> {

	private IsOperand<?> operand;

	public Min() {
		super();
	}

	public Min(IsOperand<?> operand) {
		this();
		setOperand(operand);
	}
	
	public void setOperand(IsOperand<?> operand) {
		this.operand = operand;
	}
	
	public Min withOperand(IsOperand<?> operand) {
		setOperand(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {

		StringBuilder count = new StringBuilder("min(");
		
		if (isDistinct()) count.append("DISTINCT ");
		
		count.append(context.getInContext(operand).toReturnableString(context));
		
		count.append(")");
		
		return count.toString();

	}

}
