package com.paulodorow.decypher.operations;

import java.util.ArrayList;
import java.util.List;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class And extends Predicate<And> {

	List<IsOperand<?>> operands;
	
	public And() {
		operands = new ArrayList<>();
	}
	
	public And(IsOperand<?> operand1, IsOperand<?> operand2) {
		this();
		and(operand1).and(operand2);
	}
	
	@Override
	public And and(IsOperand<?> operand) {
		if (operand != null) this.operands.add(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {

		StringBuilder comparison = new StringBuilder();

		for (IsOperand<?> operand : operands) {
			
			if (comparison.length() > 0) comparison.append(" AND ");
			
			comparison.append(context.getInContext(operand).toReturnableString(context));
			
		}
		
		return comparison.toString();

	}
	
}
