package com.paulodorow.decypher;

public interface HasReturnablesDecorator<D> extends Decorator<D> {

	Returnables returnables();
	
	default D withReturnables(IsReturnable<?>... returnables) {
		addReturnables(returnables);
		return getDecorated();
	}

	default void addReturnables(IsReturnable<?>... returnables) {
		returnables().add(returnables);
	}

	default D all() {
		returnables().returnAll();
		return getDecorated();
	}
	
	default boolean isReturningAll() {
		return returnables().isReturningAll();
	}
	
	default boolean hasReturnables() {
		return returnables().hasItems();
	}

}
