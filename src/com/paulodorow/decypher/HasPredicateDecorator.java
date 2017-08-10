package com.paulodorow.decypher;

public interface HasPredicateDecorator<D> extends Decorator<D> {

	void setWhere(IsPredicate where);
	
	IsPredicate getWhere();
	
	default D where(IsPredicate where) {
		setWhere(where);
		return getDecorated();
	}
	
	default boolean hasWhere() {
		return getWhere() != null;
	}
	
}
