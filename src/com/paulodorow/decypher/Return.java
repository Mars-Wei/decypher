package com.paulodorow.decypher;

public class Return implements ReturnsResults<Return> {
	
	private Returnables returnables;
	private Limitations limitations;
	private OrderBy orderBy;
	private boolean distinct;
	
	public Return() {
		returnables = new Returnables();
		limitations = new Limitations();
		orderBy = new OrderBy();
		distinct = false;
	}
	
	public Return(IsReturnable<?>... entities) {
		this();
		returnables.add(entities);
	}
	
	public Return(ReturnsResults<?> returns) {
		this();
		if (returns == null) return;
		returns.returnables().items().forEachRemaining(
			(item) -> { if (item != null) returnables().add(item); }
		);
	}

	@Override
	public Returnables returnables() {
		return returnables;
	}

	@Override
	public Limitations limitations() {
		return limitations;
	}

	@Override
	public OrderBy orderBy() {
		return orderBy;
	}
	
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}
	
	public boolean isDistinct() {
		return distinct;
	}
	
	public Return withDistinct(boolean distinct) {
		setDistinct(distinct);
		return this;
	}

	@Override
	public String toString() {
		return toStringInContext(NoContext.get());
	}

	@Override
	public String toStringInContext(HasContext context) {

		if (!hasReturnables()) return "";
		
		StringBuilder builder = new StringBuilder("RETURN ");
		
		if (distinct) builder.append("DISTINCT ");
				
		HasContext encapsulatedContext = getEncapsulatedContext(context);
		
		//builder.append(returnables().toStringInContext(encapsulatedContext));
		builder.append(returnables().toStringInContext(context));
		
		if (orderBy().hasOrder()) builder.append(" ").append(orderBy().toStringInContext(encapsulatedContext));

		if (limitations().hasLimitations()) builder.append(" ").append(limitations());
		
		return builder.toString();

	}

}
