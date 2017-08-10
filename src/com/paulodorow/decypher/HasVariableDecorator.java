package com.paulodorow.decypher;

public interface HasVariableDecorator<D> extends Decorator<D> {

	Variable variable();
	
	default D withVariable(String variable) {
		variable().set(variable);
		return getDecorated();
	}

}
