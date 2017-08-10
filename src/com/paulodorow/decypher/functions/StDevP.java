package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class StDevP extends AggregateFunction<StDevP> {

	private IsOperand<?> operand;

	public StDevP() {

	}

	public StDevP(IsOperand<?> operand) {
		this();
		setOperand(operand);
	}
	
	public void setOperand(IsOperand<?> operand) {
		this.operand = operand;
	}
	
	public StDevP withOperand(IsOperand<?> operand) {
		setOperand(operand);
		return this;
	}
	
	@Override
	public StDevP withDistinct(boolean distinct) {
		setDistinct(distinct);
		return this;
	}

	@Override
	public String toReturnableString(HasContext context) {

		StringBuilder count = new StringBuilder("stdevp(");
		
		if (isDistinct()) count.append("DISTINCT ");
		
		count.append(context.getInContext(operand).toReturnableString(context));
		
		count.append(")");
		
		return count.toString();
		
	}

}
