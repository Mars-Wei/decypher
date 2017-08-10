package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Substring extends Function<Substring> {

	private IsOperand<?> original;
	private IsOperand<?> begin;
	private IsOperand<?> subLength;

	public Substring() {
		
	}
	
	public Substring(IsOperand<?> original, IsOperand<?> begin, IsOperand<?> sublength) {
		this();
		this.original = original;
		this.begin = begin;
		this.subLength = sublength;
	}

	public IsOperand<?> getOriginal() {
		return original;
	}

	public void setOriginal(IsOperand<?> original) {
		this.original = original;
	}
	
	public Substring withOriginal(IsOperand<?> original) {
		setOriginal(original);
		return this;
	}

	public IsOperand<?> getBegin() {
		return begin;
	}

	public void setBegin(IsOperand<?> begin) {
		this.begin = begin;
	}

	public Substring withBegin(IsOperand<?> begin) {
		setBegin(begin);
		return this;
	}
	
	public IsOperand<?> getSubLength() {
		return subLength;
	}

	public void setSubLength(IsOperand<?> sublength) {
		this.subLength = sublength;
	}
	
	public Substring withSubLength(IsOperand<?> sublength) {
		setSubLength(sublength);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("substring(%s, %s, %s)", 
				context.getInContext(original).toReturnableString(context), 
				context.getInContext(begin).toReturnableString(context), 
				context.getInContext(subLength).toReturnableString(context));
	}
	
}
