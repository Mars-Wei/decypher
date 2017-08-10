package com.paulodorow.decypher;

public interface IsPattern extends IsPredicate {

	String toPatternString(HasContext context);
	
}
