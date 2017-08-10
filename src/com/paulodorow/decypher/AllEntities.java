package com.paulodorow.decypher;

public class AllEntities extends Token implements IsReturnable<AllEntities> {

	public static final AllEntities ALL = new AllEntities();

	public AllEntities() {
		super();
	}
	
	@Override
	public String toString() {
		return toReturnableString(NoContext.get());
	}

	@Override
	public String toReturnableString(HasContext context) {
		return "*";
	}

}
