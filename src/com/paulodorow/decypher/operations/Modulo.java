package com.paulodorow.decypher.operations;

import java.util.ArrayList;
import java.util.List;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Modulo extends Operation<Modulo> {

	List<IsOperand<?>> divisors;
	
	public Modulo() {
		divisors = new ArrayList<>();
	}
	
	public Modulo(IsOperand<?> dividend, IsOperand<?> divisor) {
		this();
		mod(dividend).mod(divisor);
	}
	
	public Modulo mod(IsOperand<?> divisor) {
		if (divisor != null) this.divisors.add(divisor);
		return this;
	}
		
	@Override
	public String toReturnableString(HasContext context) {
		
		if (divisors.size() == 1) return divisors.get(0).toString();

		StringBuilder division = new StringBuilder();

		for (IsOperand<?> divisor : divisors) {
			
			if (division.length() > 0) division.append(" % ");
			
			division.append(context.getInContext(divisor).toReturnableString(context));
			
		}
		
		return division.toString();
		
	}

}
