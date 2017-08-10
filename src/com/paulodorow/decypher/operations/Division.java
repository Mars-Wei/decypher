package com.paulodorow.decypher.operations;

import java.util.ArrayList;
import java.util.List;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Division extends Operation<Division> {

	List<IsOperand<?>> divisors;
	
	public Division() {
		divisors = new ArrayList<>();
	}

	public Division(IsOperand<?> dividend) {
		this();
		divideBy(dividend);
	}

	public Division(IsOperand<?> dividend, IsOperand<?> divisor) {
		this();
		divideBy(dividend).divideBy(divisor);
	}
	
	public Division divideBy(IsOperand<?> divisor) {
		if (divisor != null) this.divisors.add(divisor);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {

		StringBuilder division = new StringBuilder();

		for (IsOperand<?> divisor : divisors) {
			
			if (division.length() > 0) division.append(" / ");
			
			division.append(context.getInContext(divisor).toReturnableString(context));
			
		}
		
		return division.toString();

	}
	
}
