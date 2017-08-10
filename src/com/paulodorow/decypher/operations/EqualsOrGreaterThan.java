package com.paulodorow.decypher.operations;

import java.util.ArrayList;
import java.util.List;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class EqualsOrGreaterThan extends Predicate<EqualsOrGreaterThan> {

	List<IsOperand<?>> operands;
	
	public EqualsOrGreaterThan() {
		operands = new ArrayList<>();
	}
	
	public EqualsOrGreaterThan(IsOperand<?> operand1, IsOperand<?> operand2) {
		this();
		equalsOrGreaterThan(operand1).equalsOrGreaterThan(operand2);
	}
	
	public EqualsOrGreaterThan equalsOrGreaterThan(IsOperand<?> operand) {
		if (operand != null) this.operands.add(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		
		StringBuilder comparison = new StringBuilder();

		for (IsOperand<?> operand : operands) {
			
			if (comparison.length() > 0) comparison.append(" >= ");
			
			comparison.append(context.getInContext(operand).toReturnableString(context));
			
		}
		
		return comparison.toString();
		
	}
	
}
