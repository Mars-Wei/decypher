package com.paulodorow.decypher;

public interface HasOrderByDecorator<D> extends Decorator<D> {

	OrderBy orderBy();
	
	default D withOrderBy(IsOrderable<?>... orderBy) {
		orderBy().add(orderBy);
		return getDecorated();
	}
	
}
