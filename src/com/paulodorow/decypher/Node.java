package com.paulodorow.decypher;

public class Node extends Entity<Node> implements ParticipatesInRelationships {
	
	public Node(String label, String variable) {
		this(label);
		variable().set(variable);
	}

	public Node(String... labels) {
		super();
		labels().add(labels);
	}

	@Override
	public String toReturnableString(HasContext context) {
		
		StringBuilder result = new StringBuilder();
		
		IsReturnable<?> contextNode = context.getInContext(this);
		if (context.isInContext(this) && contextNode != this) return contextNode.toReturnableString(context); 
		
		if (variable().isSet()) {
			result.append(variable().toString());
		} else {
			result.append(labels().getSingleLabelString());
		}

		return result.toString();
		
	}

	@Override
	public String toPatternString(HasContext context) {

		StringBuilder builder = new StringBuilder("(");
		
		IsReturnable<?> contextNode = context.getInContext(this);
		if (context.isInContext(this) && contextNode != this) {
		
			builder.append(contextNode.toReturnableString(context));
			
		} else {

			builder.append(variable());
			
			builder.append(labels());
			
			if (properties().hasNonNullProperties()) builder.append(" ").append(properties().toString(context));
		
			
		}

		builder.append(")");
		
		return builder.toString();		

	}
	
	@Override
	public String toPredicateString(HasContext context) {
		return toPatternString(context);
	}
	
	@Override
	public String toString() {
		return toPatternString(NoContext.get());
	}

}
