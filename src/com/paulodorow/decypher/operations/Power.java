package com.paulodorow.decypher.operations;

import java.util.ArrayList;
import java.util.List;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Power extends Operation<Power> {

	List<IsOperand<?>> exponents;
	
	public Power() {
		exponents = new ArrayList<>();
	}
	
	public Power(IsOperand<?> base, IsOperand<?> exponent) {
		this();
		exponentiate(base).exponentiate(exponent);
	}
	
	public Power exponentiate(IsOperand<?> exponent) {
		if (exponent != null) this.exponents.add(exponent);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		
		StringBuilder multiplication = new StringBuilder();

		for (IsOperand<?> multiplier : exponents) {
			
			if (multiplication.length() > 0) multiplication.append(" ^ ");
			
			multiplication.append(context.getInContext(multiplier).toReturnableString(context));
			
		}
		
		return multiplication.toString();
		
	}
	
}
