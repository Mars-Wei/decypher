package com.paulodorow.decypher;

public abstract class Token implements Comparable<Token> {

	@Override
	public int compareTo(Token o) {
		return toString().compareTo(o.toString());
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) return false;
		
		//if (!getClass().isInstance(obj)) return false;

		return toString().equals(obj.toString());
		
	}
	
	/**
	 * toString() is re-implemented to avoid infinite call loops
	 * between toString() and hashCode()  
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName();
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

}
