package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.NoContext;
import com.paulodorow.decypher.Path;

public class Relationships extends PathFunction<Relationships> implements ReturnsEntities {

	public Relationships() {
		
	}
	
	public Relationships(Path path) {
		super(path);
	}
	
	@Override
	public String toString() {
		return toReturnableString(NoContext.get());
	}

	@Override
	public String toReturnableString(HasContext context) {
		//TODO study how to incorporate context.getInContext(operand) with getPath()
		return String.format("relationships(%s)", getPath().variable().get());
	}
	
}
