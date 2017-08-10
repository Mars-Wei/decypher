package com.paulodorow.decypher;

public class AllShortestPaths extends Path {

	@Override
	public String toPatternString(HasContext context) {
		
		if (variable().isSet()) 
			return String.format("%s=allShortestPaths(%s)", variable(), toPathString(context));
		else
			return String.format("allShortestPaths(%s)", toPathString(context));
		
	}
	
}
