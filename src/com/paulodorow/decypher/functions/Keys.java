package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.Entity;
import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperation;
import com.paulodorow.decypher.NoContext;
import com.paulodorow.decypher.Token;

//TODO this type also needs to support Maps
public class Keys extends Token implements IsOperation<Keys> {
	
	private Entity<?> entity;
	
	public Keys() {
		super();
	}
	
	public Keys(Entity<?> entity) {
		this();
		setEntity(entity);
	}

	public void setEntity(Entity<?> entity) {
		this.entity = entity;
	}
	
	public Entity<?> getEntity() {
		return entity;
	}
	
	public Keys withEntity(Entity<?> entity) {
		setEntity(entity);
		return this;
	}
		
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("keys(%s)", context.getInContext(entity).toReturnableString(context));
	}

	@Override
	public String toString() {
		return toReturnableString(NoContext.get());
	}
	
}
