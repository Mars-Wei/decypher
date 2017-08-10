package com.paulodorow.decypher;

public interface Decorator<D> {

	@SuppressWarnings("unchecked")
	default D getDecorated() {
		return (D) this;
	}
	
}
