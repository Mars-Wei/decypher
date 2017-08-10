package com.paulodorow.decypher;

public class Value extends Token implements IsOperand<Value> {

	public static final Value EMPTY_STRING = new Value("");
	public static final Value ZERO = new Value(0);
	
	private Object value;

	public Value() {
		
	}
	
	public Value(Object value) {
		this();
		this.value = value;
	}
	
	public Object get() {
		return value;
	}
	
	public void set(Object value) {
		this.value = value;
	}
	
	public Value as(Object value) {
		set(value);
		return this;
	}

	public void clear() {
		set(null);
	}

	public boolean isNull() {
		return get() == null;
	}

	@Override
	public String toReturnableString(HasContext context) {
		if (value == null) return "null";
		else if (value instanceof String) return String.format("'%s'", value);
		else if (value instanceof Integer) return String.valueOf(value);
		else if (value instanceof IsReturnable<?>) return ((IsReturnable<?>)value).toReturnableString(context);
		else return value.toString();
	}

	@Override
	public String toString() {
		return toReturnableString(NoContext.get());
	}

	@Override
	public boolean equals(Object obj) {
		
		Object thisValue = get();
		Object otherValue = obj instanceof Value ? ((Value)obj).get() : obj;
		
		if (otherValue == null && thisValue == null) return true;
		
		return (thisValue != null && thisValue.equals(otherValue));
		
	}

}
