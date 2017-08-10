package com.paulodorow.decypher.operations;

import java.util.ArrayList;
import java.util.List;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Equals extends Predicate<Equals> {
	List<IsOperand<?>> operands;

	
	public Equals() {
		operands = new ArrayList<>();
	}
	
	public Equals(IsOperand<?> operand1, IsOperand<?> operand2) {
		this();
		equals(operand1).equals(operand2);
	}
	
	public Equals equals(IsOperand<?> operand) {
		if (operand != null) this.operands.add(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		
		StringBuilder comparison = new StringBuilder();

		for (IsOperand<?> operand : operands) {
			
			if (comparison.length() > 0) comparison.append(" = ");
			
			comparison.append(context.getInContext(operand).toReturnableString(context));
			
		}
		
		return comparison.toString();
		
	}
	
}
