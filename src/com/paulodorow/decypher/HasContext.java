package com.paulodorow.decypher;

public interface HasContext {

	boolean isInContext(IsReturnable<?> returnable);
	
	IsReturnable<?> getInContext(IsReturnable<?> returnable);
	
	default HasContext getEncapsulatedContext(HasContext context) {
		
		final HasContext thisContext = this;
		
		return new HasContext() {
			
			@Override
			public boolean isInContext(IsReturnable<?> returnable) {
				
				if (thisContext.isInContext(returnable)) {
					return true;
				} else {
					return context.isInContext(returnable);
				}
				
			}
			
			@Override
			public IsReturnable<?> getInContext(IsReturnable<?> returnable) {
				
				if (thisContext.isInContext(returnable)) {
					return thisContext.getInContext(returnable);
				} else {
					return context.getInContext(returnable);
				}
				
			}
		};
		
	}
	
}

 
