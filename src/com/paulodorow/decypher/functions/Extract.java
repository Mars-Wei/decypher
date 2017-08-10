package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.Entity;
import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;
import com.paulodorow.decypher.NoContext;

public class Extract extends Function<Extract> {

	private Entity<?> entity;
	private ReturnsEntities entityCollection;
	private IsOperand<?> returns;

	public Extract() {
		
	}
	
	 //extract(n IN relationships(p)| n.age) AS extracted
	public Extract(Entity<?> entity, ReturnsEntities entityCollection, IsOperand<?> operand) {
		this();
		setEntity(entity);
		setEntityCollection(entityCollection);
		setReturns(operand);
	}

	public void setEntity(Entity<?> entity) {
		this.entity = entity;
	}
	
	public Entity<?> getEntity() {
		return entity;
	}
	
	public Extract withEntity(Entity<?> entity) {
		setEntity(entity);
		return this;
	}

	public void setEntityCollection(ReturnsEntities entityCollection) {
		this.entityCollection = entityCollection;
	}
	
	public ReturnsEntities getEntities() {
		return entityCollection;
	}
	
	public Extract withEntityCollection(ReturnsEntities entityCollection) {
		setEntityCollection(entityCollection);
		return this;
	}

	public void setReturns(IsOperand<?> operand) {
		this.returns = operand;
	}
	
	public IsOperand<?> getReturns() {
		return returns;
	}
	
	public Extract withReturns(IsOperand<?> operand) {
		setReturns(operand);
		return this;
	}
		
	@Override
	public String toReturnableString(HasContext context) {
		//TODO study how to incroporate context.getInContext(operand) for entity
		return String.format("extract(%1$s IN %2$s | %3$s)", entity.variable().get(), entityCollection.toString(), returns.toReturnableString(context));
	}

	@Override
	public String toString() {
		return toReturnableString(NoContext.get());
	}
	
}
