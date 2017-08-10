package com.paulodorow.decypher;

public class Parameter extends Token implements IsOperand<Parameter> {

	private String name;

	public Parameter() {
		
	}
	
	public Parameter(String name) {
		this();
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Parameter name(String name) {
		setName(name);
		return this;
	}

	@Override
	public String toString() {
		return toReturnableString(NoContext.get());
	}

	@Override
	public String toReturnableString(HasContext context) {
		return String.format("{%s}", name);
	}
	
}
