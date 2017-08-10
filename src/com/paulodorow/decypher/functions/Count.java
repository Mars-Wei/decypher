package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.AllEntities;
import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsReturnable;

public class Count extends AggregateFunction<Count> {

	private IsReturnable<?> expression;

	public Count() {
		super();
		countAll();
	}

	public Count(IsReturnable<?> expression) {
		this();
		setExpression(expression);
	}
	
	public void setExpression(IsReturnable<?> expression) {
		this.expression = expression;
	}

	public Count withExpression(IsReturnable<?> expression) {
		this.expression = expression;
		return this;
	}
	
	public Count countAll() {
		setExpression(AllEntities.ALL);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		
		StringBuilder count = new StringBuilder("count(");
		
		if (isDistinct()) count.append("DISTINCT ");
		
		count.append(context.getInContext(expression).toReturnableString(context));
		
		count.append(")");
		
		return count.toString();
		
	}
	
}
