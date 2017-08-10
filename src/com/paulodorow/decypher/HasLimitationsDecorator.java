package com.paulodorow.decypher;

public interface HasLimitationsDecorator<D> extends Decorator<D> {

	Limitations limitations();
	
	default D withSkip(Value skip) {
		limitations().setSkip(skip);
		return getDecorated();
	}
	
	default D withLimit(Value limit) {
		limitations().setLimit(limit);
		return getDecorated();
	}
	
	
	
}
