package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Split extends Function<Split> {

	private IsOperand<?> original;
	private IsOperand<?> delimiter;

	public Split() {
		
	}
	
	public Split(IsOperand<?> original, IsOperand<?> delimiter) {
		this();
		this.original = original;
		this.delimiter = delimiter;
	}

	public IsOperand<?> getOriginal() {
		return original;
	}

	public void setOriginal(IsOperand<?> original) {
		this.original = original;
	}
	
	public Split withOriginal(IsOperand<?> original) {
		setOriginal(original);
		return this;
	}
	
	public IsOperand<?> getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(IsOperand<?> delimiter) {
		this.delimiter = delimiter;
	}
	
	public Split withDelimiter(IsOperand<?> delimiter) {
		setDelimiter(delimiter);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("split(%s, %s)", 
				context.getInContext(getOriginal()).toReturnableString(context), 
				context.getInContext(getDelimiter()).toReturnableString(context));
	}
	
}
