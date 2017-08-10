package com.paulodorow.decypher.operations;

import java.util.ArrayList;
import java.util.List;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Xor extends Predicate<Xor> {

	List<IsOperand<?>> operands;
	
	public Xor() {
		operands = new ArrayList<>();
	}
	
	public Xor(IsOperand<?> operand1, IsOperand<?> operand2) {
		this();
		xor(operand1).xor(operand2);
	}
	
	@Override
	public Xor xor(IsOperand<?> operand) {
		if (operand != null) this.operands.add(operand);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		
		StringBuilder comparison = new StringBuilder();

		for (IsOperand<?> operand : operands) {
			
			if (comparison.length() > 0) comparison.append(" XOR ");
			
			comparison.append(context.getInContext(operand).toReturnableString(context));
			
		}
		
		return comparison.toString();
		
	}
	
}
