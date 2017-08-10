package com.paulodorow.decypher;

public class Limitations extends Token {

	private Value skip;
	private Value limit;

	public Value getSkip() {
		return skip;
	}

	public void setSkip(Value skip) {
		this.skip = skip;
	}
	
	public Limitations withSkip(Value skip) {
		setSkip(skip);
		return this;
	}

	public boolean hasSkip() {
		return skip != null && !skip.isNull();
	}

	public Value getLimit() {
		return limit;
	}

	public void setLimit(Value limit) {
		this.limit = limit;
	}
	
	public  Limitations withLimit(Value limit) {
		this.limit = limit;
		return this;
	}

	public void clearSkip() {
		this.skip = null;
	}

	public void clearLimit() {
		this.limit = null;
	}

	public void clear() {
		clearSkip();
		clearLimit();
	}

	public boolean hasLimit() {
		return limit != null && !limit.isNull();
	}
	
	public boolean hasLimitations() {
		return hasSkip() || hasLimit();
	}

	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		if (hasSkip()) builder.append("SKIP").append(" ").append(skip); 

		if (hasLimit()) {
			
			if (hasSkip()) builder.append(" ");
			
			builder.append("LIMIT").append(" ").append(limit);
			
		}
		
		return builder.toString();
		
	}
	
}
