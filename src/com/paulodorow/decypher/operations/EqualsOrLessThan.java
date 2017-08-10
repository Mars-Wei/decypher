package com.paulodorow.decypher.operations;

import java.util.ArrayList;
import java.util.List;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class EqualsOrLessThan extends Predicate<EqualsOrLessThan> {

	List<IsOperand<?>> operands;
	
	public EqualsOrLessThan() {
		operands = new ArrayList<>();
	}
	
	public EqualsOrLessThan(IsOperand<?> operand1, IsOperand<?> operand2) {
		this();
		equalsOrLessThan(operand1).equalsOrLessThan(operand2);
	}
	
	public EqualsOrLessThan equalsOrLessThan(IsOperand<?> operand) {
		if (operand != null) this.operands.add(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		
		StringBuilder comparison = new StringBuilder();

		for (IsOperand<?> operand : operands) {
			
			if (comparison.length() > 0) comparison.append(" <= ");
			
			comparison.append(context.getInContext(operand).toReturnableString(context));
			
		}
		
		return comparison.toString();
		
	}
	
}
