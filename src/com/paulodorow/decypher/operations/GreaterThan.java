package com.paulodorow.decypher.operations;

import java.util.ArrayList;
import java.util.List;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class GreaterThan extends Predicate<GreaterThan> {

	List<IsOperand<?>> operands;
	
	public GreaterThan() {
		operands = new ArrayList<>();
	}
	
	public GreaterThan(IsOperand<?> operand1, IsOperand<?> operand2) {
		this();
		greaterThan(operand1).greaterThan(operand2);
	}
	
	public GreaterThan greaterThan(IsOperand<?> operand) {
		if (operand != null) this.operands.add(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		
		StringBuilder comparison = new StringBuilder();

		for (IsOperand<?> operand : operands) {
			
			if (comparison.length() > 0) comparison.append(" > ");
			
			comparison.append(context.getInContext(operand).toReturnableString(context));
			
		}
		
		return comparison.toString();
		
	}
	
}
