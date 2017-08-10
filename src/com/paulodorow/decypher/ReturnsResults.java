package com.paulodorow.decypher;

import java.util.Iterator;

public interface ReturnsResults<R> extends HasLimitationsDecorator<R>, HasReturnablesDecorator<R>, HasOrderByDecorator<R>, IsStatement, HasContext, AcceptsMatches  {

	@Override
	default boolean isInContext(IsReturnable<?> returnable) {

		Iterator<IsReturnable<?>> returnables = returnables().items();
		
		while (returnables.hasNext()) {
			
			IsReturnable<?> item = returnables.next();
			
			if (item instanceof Alias && returnable.equals(((Alias)item).getReturnable())) return true;
			
			if (item.equals(returnable)) return true;
			
		}
		
		return false;

	} 
	
	@Override
	default IsReturnable<?> getInContext(IsReturnable<?> returnable) {
		
		Iterator<IsReturnable<?>> returnables = returnables().items();
		
		while (returnables.hasNext()) {
			
			IsReturnable<?> item = returnables.next();
			
			if (item instanceof Alias && returnable.equals(((Alias)item).getReturnable())) return item;
			
			if (item.equals(returnable)) return item;
			
		}
		
		return returnable;
		
	}
	
}
