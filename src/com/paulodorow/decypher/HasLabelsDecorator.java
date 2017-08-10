package com.paulodorow.decypher;

public interface HasLabelsDecorator<D> extends Decorator<D> {

	Labels labels();
	
	default D withLabels(String... labels) {
		labels().add(labels);
		return getDecorated();
	}
	
}
