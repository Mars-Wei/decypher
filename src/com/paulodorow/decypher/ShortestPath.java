package com.paulodorow.decypher;

public class ShortestPath extends Path {

	@Override
	public String toPatternString(HasContext context) {
		
		if (variable().isSet()) 
			return String.format("%s=shortestPath(%s)", variable(), toPathString(context));
		else
			return String.format("shortestPath(%s)", toPathString(context));
		
	}
	
}
