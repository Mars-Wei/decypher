package com.paulodorow.decypher.operations;

import java.util.ArrayList;
import java.util.List;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Addition extends Operation<Addition> {

	List<IsOperand<?>> addends;
	
	public Addition() {
		addends = new ArrayList<>();
	}
	
	public Addition(IsOperand<?>...addends) {
		this();
		add(addends);
	}
	
	public Addition add(IsOperand<?>... addends) {
		for (IsOperand<?> addend : addends) if (addend != null) this.addends.add(addend);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {

		StringBuilder addition = new StringBuilder();

		for (IsOperand<?> addend : addends) {
			
			if (addition.length() > 0) addition.append(" + ");
			
			addition.append(context.getInContext(addend).toReturnableString(context));
			
		}
		
		return addition.toString();

	}
	
}
