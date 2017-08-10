package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Avg extends AggregateFunction<Avg> {

	private IsOperand<?> operand;

	public Avg() {
		super();
	}

	public Avg(IsOperand<?> operand) {
		this();
		setOperand(operand);
	}
	
	public void setOperand(IsOperand<?> operand) {
		this.operand = operand;
	}
	
	public Avg withOperand(IsOperand<?> operand) {
		setOperand(operand);
		return this;
	}
		
	@Override
	public String toReturnableString(HasContext context) {

		StringBuilder count = new StringBuilder("avg(");
		
		if (isDistinct()) count.append("DISTINCT ");
		
		count.append(context.getInContext(operand).toReturnableString(context));
		
		count.append(")");
		
		return count.toString();

	}

}
