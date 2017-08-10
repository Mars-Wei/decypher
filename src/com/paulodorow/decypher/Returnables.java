package com.paulodorow.decypher;

import java.util.Iterator;
import java.util.Set;

public class Returnables extends Token {

	private Set<IsReturnable<?>> returnables;
	
	public Returnables() {
		returnables = new ArrayListSet<>();
	}
	
	public void add(IsReturnable<?>... returnables) {
		
		for (IsReturnable<?> returnable : returnables) {
			
			if (returnable == null) continue;
			
			if (isReturningAll()) clear();
			this.returnables.add(returnable);
			
		}
		
	}
	
	public void remove(String alias) {
		returnables.remove(alias);
	}
	
	public void remove(IsReturnable<?> returnable) {
		returnables.remove(returnable);
	}
	
	public void clear() {
		returnables.clear();
	}
	
	public int count() {
		return returnables.size();
	}
	
	public boolean hasItems() {
		return count() > 0;
	}
	
	Iterator<IsReturnable<?>> items() {
		return returnables.iterator();
	}

	public boolean contains(IsReturnable<?> returnable) {
		return returnables.contains(returnable);
	}

	public void returnAll() {
		clear();
		add(new AllEntities());
	}

	public boolean isReturningAll() {
		return count() == 1 && returnables.iterator().next() instanceof AllEntities;
	}

	@Override
	public String toString() {
		return toStringInContext(NoContext.get());
	}
	
	public String toStringInContext(HasContext context) {
		
		ListBuilder listBuilder = new ListBuilder();
		
		for (IsReturnable<?> expression : returnables) {
			listBuilder.appendToList(context.getInContext(expression).toReturnableString(context));  
		}

		return listBuilder.toString();

	}
	
}
