package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.Entity;
import com.paulodorow.decypher.HasContext;

public class Id extends Function<Id> {

	private Entity<?> entity;

	public Id() {
		
	}
	
	public Id(Entity<?> entity) {
		this();
		setEntity(entity);
	}

	public void setEntity(Entity<?> entity) {
		this.entity = entity;
	}
	
	public Entity<?> getEntity() {
		return entity;
	}
	
	public Id withEntity(Entity<?> entity) {
		setEntity(entity);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("id(%s)", context.getInContext(entity).toReturnableString(context));
	}
	
}
