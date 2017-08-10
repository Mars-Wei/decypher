package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.Property;

public class Exists extends Function<Exists> {

	private Property property;

	public Exists() {

	}

	public Exists(Property property) {
		this();
		setProperty(property);
	}
	
	public void setProperty(Property property) {
		this.property = property;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("exists(%s)", context.getInContext(property).toReturnableString(context));
	}
	
}
