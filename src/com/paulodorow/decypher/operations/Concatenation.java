package com.paulodorow.decypher.operations;

import java.util.ArrayList;
import java.util.List;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsReturnable;

public class Concatenation extends Operation<Concatenation> { 
	
	List<IsReturnable<?>> elements;
	
	public Concatenation() {
		elements = new ArrayList<>();
	}
	
	public Concatenation(IsReturnable<?>...addends) {
		this();
		concatenate(addends);
	}
	
	public Concatenation concatenate(IsReturnable<?>... elements) {
		for (IsReturnable<?> element : elements) if (element != null) this.elements.add(element);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		
		StringBuilder addition = new StringBuilder();

		for (IsReturnable<?> element : elements) {
			
			if (addition.length() > 0) addition.append(" + ");
			
			addition.append(context.getInContext(element).toReturnableString(context));
			
		}
		
		return addition.toString();
		
	}
	

}
