package com.paulodorow.decypher;


public class ListBuilder {
			
	StringBuilder builder = new StringBuilder();

	ListBuilder appendToList(String item) {
	
	if (builder.length() > 0) builder.append(", ");
		builder.append(item);
		
		return this;
		
	}
	
	@Override
	public String toString() {
		return builder.toString();
	}
	
}
