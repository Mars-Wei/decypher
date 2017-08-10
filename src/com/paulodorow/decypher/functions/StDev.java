package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class StDev extends AggregateFunction<StDev> {

	private IsOperand<?> operand;

	public StDev() {

	}

	public StDev(IsOperand<?> operand) {
		this();
		setOperand(operand);
	}
	
	public void setOperand(IsOperand<?> operand) {
		this.operand = operand;
	}
	
	public StDev withOperand(IsOperand<?> operand) {
		setOperand(operand);
		return this;
	}
	
	@Override
	public StDev withDistinct(boolean distinct) {
		setDistinct(distinct);
		return this;
	}

	@Override
	public String toReturnableString(HasContext context) {

		StringBuilder count = new StringBuilder("stdev(");
		
		if (isDistinct()) count.append("DISTINCT ");
		
		count.append(context.getInContext(operand).toReturnableString(context));
		
		count.append(")");
		
		return count.toString();

	}

}
