package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.Path;

public class Nodes extends PathFunction<Nodes> implements ReturnsEntities {

	public Nodes() {
		
	}
	
	public Nodes(Path path) {
		super(path);
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("nodes(%s)", context.getInContext(getPath()).toReturnableString(context));
	}
	
}
