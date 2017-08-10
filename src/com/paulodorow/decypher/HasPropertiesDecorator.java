package com.paulodorow.decypher;

public interface HasPropertiesDecorator<D> extends Decorator <D> {

	Properties properties();
	
	default D withProperties(Property... properties) {
		properties().add(properties);
		return getDecorated();
	}
	
}
