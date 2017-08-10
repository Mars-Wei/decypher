package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsReturnable;

public class Coalesce extends Function<Coalesce> {

	private IsReturnable<?> value;
	private IsReturnable<?> defaultValue;
	
	public Coalesce() {
		super();
	}
	
	public Coalesce(IsReturnable<?> value, IsReturnable<?> defaultValue) {
		this();
		setValue(value);
		setDefaultValue(defaultValue);
	}

	public void setValue(IsReturnable<?> value) {
		this.value = value;
	}
	
	public IsReturnable<?> getValue() {
		return this.value;
	}
	
	public Coalesce withValue(IsReturnable<?> value) {
		setValue(value);
		return this;
	}
	
	public void setDefaultValue(IsReturnable<?> defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public IsReturnable<?> getDefaultValue() {
		return this.defaultValue;
	}
	
	public Coalesce withDefaultValue(IsReturnable<?> defaultValue) {
		setDefaultValue(defaultValue);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("coalesce(%s, %s)", context.getInContext(getValue()).toReturnableString(context), context.getInContext(getDefaultValue()).toReturnableString(context));
	}

}
