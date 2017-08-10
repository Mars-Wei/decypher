package com.paulodorow.decypher.functions;

public abstract class AggregateFunction<A> extends Function<A> {

	private boolean distinct;

	public AggregateFunction() {
		super();
		distinct = false;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}
	
	public boolean isDistinct() {
		return distinct;
	}
	
	@SuppressWarnings("unchecked")
	public A withDistinct(boolean distinct) {
		setDistinct(distinct);
		return (A) this;
	}

}
