package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsReturnable;

public class Collect extends AggregateFunction<Collect> {

	private IsReturnable<?> expression;

	public Collect() {

	}

	public Collect(IsReturnable<?> expression) {
		this();
		setExpression(expression);
	}
	
	public void setExpression(IsReturnable<?> expression) {
		this.expression = expression;
	}
	
	public Collect withExpression(IsReturnable<?> expression) {
		setExpression(expression);
		return this;
	}
	
	@Override
	public Collect withDistinct(boolean distinct) {
		setDistinct(distinct);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {

		StringBuilder count = new StringBuilder("collect(");
		
		if (isDistinct()) count.append("DISTINCT ");
		
		count.append(context.getInContext(expression).toReturnableString(context));
		
		count.append(")");
		
		return count.toString();

	}

}
