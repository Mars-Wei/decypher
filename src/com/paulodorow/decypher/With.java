package com.paulodorow.decypher;

import com.paulodorow.decypher.operations.Predicate;

public class With implements ReturnsResults<With>, HasPredicateDecorator<With> {
	
	private Returnables returnables;
	private IsPredicate where;
	private Limitations limitations;
	private OrderBy orderBy;
	private boolean distinct;
	private IsStatement chainedStatement;
	
	//TODO  WITH WHERE: match (p:Person) with p where (p:Person) return p
	
	public With() {
		returnables = new Returnables();
		limitations = new Limitations();
		orderBy = new OrderBy();
		distinct = false;
	}
	
	public With(IsReturnable<?>... returnables) {
		this();
		this.returnables.add(returnables);
	}

	public With(IsStatement chainedStatement, IsReturnable<?>... returnable) {
		this();
		returnables.add(returnable);
		setChainedStatement(chainedStatement);
	}

	public With(ReturnsResults<?> returns) {
		this();
		returns.returnables().items().forEachRemaining((item) -> returnables().add(item));
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
	
	public With withDistinct(boolean distinct) {
		setDistinct(distinct);
		return this;
	}

	public void setChainedStatement(IsStatement chainedStatement) {
		this.chainedStatement = chainedStatement;
	}
	
	public IsStatement getChainedStatement() {
		return chainedStatement;
	}
	
	public With withChainedStatement(IsStatement chainedStatement) {
		setChainedStatement(chainedStatement);
		return this;
	}
	
	public boolean hasChainedStatement() {
		return chainedStatement != null;
	}

	@Override
	public void setWhere(IsPredicate where) {
		this.where = where;
	}
	
	@Override
	public IsPredicate getWhere() {
		return where;
	}
	
	public Match match(IsPattern pattern) {
		setChainedStatement(new Match(pattern));
		return (Match) getChainedStatement();
	}
	
	public Match match(IsPattern pattern, Predicate<?> where) {
		setChainedStatement(new Match(pattern).where(where));
		return (Match) getChainedStatement();
	}
	
	public Create create(IsPattern pattern) {
		setChainedStatement(new Create(pattern));
		return (Create) getChainedStatement();
	}
	
	@Override
	public String toString() {
		return toStringInContext(NoContext.get());
	}
	
	@Override
	public String toStringInContext(HasContext context) {
		
		StringBuilder builder = new StringBuilder("WITH ");
		
		if (distinct) builder.append("DISTINCT ");
				
		builder.append(returnables().toStringInContext(context));
		
		if (hasWhere()) builder.append(" WHERE ").append(where.toPredicateString(getEncapsulatedContext(this)));

		if (orderBy().hasOrder()) builder.append(" ").append(orderBy().toStringInContext(getEncapsulatedContext(this)));

		if (limitations().hasLimitations()) builder.append(" ").append(limitations());
		
		if (hasChainedStatement()) builder.append(" ").append(getChainedStatement().toStringInContext(getEncapsulatedContext(context)));
		
		return builder.toString();
		
		
	}
	
}
