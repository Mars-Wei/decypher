package com.paulodorow.decypher;

public class Variable extends Token {

	private String variable;

	public void set(String variable) {
		this.variable = variable;
	}
	
	public String get() {
		return this.variable;
	}
	
	public void clear() {
		set(null);
	}
	
	public Variable with(String variable) {
		set(variable);
		return this;
	}
	
	public boolean isSet() {
		return variable != null && !variable.isEmpty();
	}

	@Override
	public String toString() {
		return variable == null ? "" : variable; 
	}
	
}
