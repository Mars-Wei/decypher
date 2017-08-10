package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Sum extends AggregateFunction<Sum> {

	private IsOperand<?> operand;

	public Sum() {
		super();
	}

	public Sum(IsOperand<?> operand) {
		this();
		setOperand(operand);
	}
	
	public void setOperand(IsOperand<?> operand) {
		this.operand = operand;
	}
	
	public Sum withOperand(IsOperand<?> operand) {
		setOperand(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {

		StringBuilder count = new StringBuilder("sum(");
		
		if (isDistinct()) count.append("DISTINCT ");
		
		count.append(context.getInContext(operand).toReturnableString(context));
		
		count.append(")");
		
		return count.toString();

	}

}
