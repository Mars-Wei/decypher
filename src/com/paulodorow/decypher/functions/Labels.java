package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.Node;

public class Labels extends Function<Labels> {

	private Node node;

	public Labels() {
		
	}
	
	public Labels(Node node) {
		this();
		setNode(node);
	}
	
	public void setNode(Node node) {
		this.node = node;
	}
	
	public Node getNode() {
		return node;
	}
	
	public Labels withNode(Node node) {
		setNode(node);
		return this;
	}

	@Override
	public String toReturnableString(HasContext context) {
		return String.format("labels(%s)", context.getInContext(getNode()).toReturnableString(context));
	}

}
