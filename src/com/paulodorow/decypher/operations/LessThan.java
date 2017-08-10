package com.paulodorow.decypher.operations;

import java.util.ArrayList;
import java.util.List;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class LessThan extends Predicate<LessThan> {

	List<IsOperand<?>> operands;
	
	public LessThan() {
		operands = new ArrayList<>();
	}
	
	public LessThan(IsOperand<?> operand1, IsOperand<?> operand2) {
		this();
		lessThan(operand1).lessThan(operand2);
	}
	
	public LessThan lessThan(IsOperand<?> operand) {
		if (operand != null) this.operands.add(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		
		StringBuilder comparison = new StringBuilder();

		for (IsOperand<?> operand : operands) {
			
			if (comparison.length() > 0) comparison.append(" < ");
			
			comparison.append(context.getInContext(operand).toReturnableString(context));
			
		}
		
		return comparison.toString();
		
	}
	
}
