package com.paulodorow.decypher.operations;

import java.util.ArrayList;
import java.util.List;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class NotEquals extends Predicate<NotEquals> {

	List<IsOperand<?>> operands;
	
	public NotEquals() {
		operands = new ArrayList<>();
	}
	
	public NotEquals(IsOperand<?> operand1, IsOperand<?> operand2) {
		this();
		notEquals(operand1).notEquals(operand2);
	}
	
	public NotEquals notEquals(IsOperand<?> operand) {
		if (operand != null) this.operands.add(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		
		StringBuilder comparison = new StringBuilder();

		for (IsOperand<?> operand : operands) {
			
			if (comparison.length() > 0) comparison.append(" <> ");
			
			comparison.append(context.getInContext(operand).toReturnableString(context));
			
		}
		
		return comparison.toString();
		
	}
	
}
