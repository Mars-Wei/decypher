package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Right extends Function<Right> {

	private IsOperand<?> original;
	private IsOperand<?> sublength;

	public Right() {
		
	}
	
	public Right(IsOperand<?> original, IsOperand<?> sublength) {
		this();
		this.original = original;
		this.sublength = sublength;
	}

	public IsOperand<?> getOriginal() {
		return original;
	}

	public void setOriginal(IsOperand<?> original) {
		this.original = original;
	}
	
	public Right withOriginal(IsOperand<?> original) {
		setOriginal(original);
		return this;
	}
	
	public IsOperand<?> getSubLength() {
		return sublength;
	}

	public void setSubLength(IsOperand<?> sublength) {
		this.sublength = sublength;
	}
	
	public Right withSubLength(IsOperand<?> sublength) {
		setSubLength(sublength);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("right(%s, %s)", 
				context.getInContext(getOriginal()).toReturnableString(context), 
				context.getInContext(getSubLength()).toReturnableString(context));
	}
	
}
