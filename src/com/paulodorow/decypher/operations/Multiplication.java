package com.paulodorow.decypher.operations;

import java.util.ArrayList;
import java.util.List;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Multiplication extends Operation<Multiplication> {

	List<IsOperand<?>> multipliers;
	
	public Multiplication() {
		multipliers = new ArrayList<>();
	}

	public Multiplication(IsOperand<?> multiplicand) {
		this();
		multiply(multiplicand);
	}

	public Multiplication(IsOperand<?> multiplicand, IsOperand<?> multiplier) {
		this();
		multiply(multiplicand).multiply(multiplier);
	}
	
	public Multiplication multiply(IsOperand<?> multiplier) {
		if (multiplier != null) this.multipliers.add(multiplier);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		
		StringBuilder multiplication = new StringBuilder();

		for (IsOperand<?> multiplier : multipliers) {
			
			if (multiplication.length() > 0) multiplication.append(" * ");
			
			multiplication.append(context.getInContext(multiplier).toReturnableString(context));
			
		}
		
		return multiplication.toString();
		
	}
	
}
