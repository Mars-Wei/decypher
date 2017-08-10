package com.paulodorow.decypher.operations;

import java.util.ArrayList;
import java.util.List;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Subtraction extends Operation<Subtraction> {

	List<IsOperand<?>> subtraends;
	
	public Subtraction() {
		subtraends = new ArrayList<>();
	}
	
	public Subtraction(IsOperand<?> minuend) {
		this();
		subtract(minuend);
	}
	
	public Subtraction(IsOperand<?> minuend, IsOperand<?> subtraend) {
		this();
		subtract(minuend, subtraend);
	}
	
	public Subtraction subtract(IsOperand<?>... subtraends) {
		for (IsOperand<?> subtraend : subtraends) if (subtraend != null) this.subtraends.add(subtraend);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		
		StringBuilder subtraction = new StringBuilder();

		for (IsOperand<?> subtraend : subtraends) {
			
			if (subtraction.length() > 0) subtraction.append(" - ");
			
			subtraction.append(context.getInContext(subtraend).toReturnableString(context));
			
		}
		
		return subtraction.toString();
		
	}
	
}
