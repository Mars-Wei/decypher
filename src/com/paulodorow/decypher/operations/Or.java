package com.paulodorow.decypher.operations;

import java.util.ArrayList;
import java.util.List;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;
 
public class Or extends Predicate<Or> {

	List<IsOperand<?>> operands;
	
	public Or() {
		operands = new ArrayList<>();
	}
	
	public Or(IsOperand<?> operand1, IsOperand<?> operand2) {
		this();
		or(operand1).or(operand2);
	}
	
	@Override
	public Or or(IsOperand<?> operand) {
		if (operand != null) this.operands.add(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		
		StringBuilder comparison = new StringBuilder();

		for (IsOperand<?> operand : operands) {
			
			if (comparison.length() > 0) comparison.append(" OR ");
			
			comparison.append(context.getInContext(operand).toReturnableString(context));
			
		}
		
		return comparison.toString();
		
	}
	
}
