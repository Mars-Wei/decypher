package com.paulodorow.decypher;

import java.util.ArrayList;

public class List implements IsReturnable<List> {

	private ArrayList<IsReturnable<?>> list;

	public List() {
		list = new ArrayList<>();
	}
	
	public List(IsReturnable<?>...elements) {
		this();
		addElements(elements);
	}
	
	public void addElements(IsReturnable<?>... elements) {
		for (IsReturnable<?> element : elements) list.add(element);
	}
	
	public List withElements(IsReturnable<?>... elements) {
		addElements(elements);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		
		StringBuilder builder = new StringBuilder();
		
		for (IsReturnable<?> element : list) {
			
			if (builder.length() > 0) builder.append(", ");
			
			builder.append(context.getInContext(element).toReturnableString(context));
			
		}
		
		builder.insert(0, "[").append("]");
		
		return builder.toString();
		
	}
	
	@Override
	public String toString() {
		return toReturnableString(NoContext.get());
	}

}
