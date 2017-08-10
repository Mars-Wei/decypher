package com.paulodorow.decypher;

public class NoContext implements HasContext {

	private static final NoContext singleton = new NoContext();
	
	public static HasContext get() {
		return singleton;
	}

	@Override
	public boolean isInContext(IsReturnable<?> returnable) {
		return false;
	}

	@Override
	public IsReturnable<?> getInContext(IsReturnable<?> returnable) {
		return returnable;
	}

}
