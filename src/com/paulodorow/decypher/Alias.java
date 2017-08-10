	package com.paulodorow.decypher;

public class Alias extends Token implements IsOperand<Alias> {

	public static Alias create(IsReturnable<?> returnable, String alias) {
		return new Alias(returnable, alias);
	}

	private IsReturnable<?> returnable;
	private String alias;

	public IsReturnable<?> getReturnable() {
		return returnable;
	}

	public Alias(IsReturnable<?> returnable, String alias) {
		setReturnable(returnable);
		setAlias(alias);
	}

	public Alias(IsReturnable<?> returnable) {
		this(returnable, "");
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getAlias() {
		return alias;
	}

	public void clear() {
		setAlias("");
	}

	public void setReturnable(IsReturnable<?> returnable) {
		this.returnable = returnable;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		
		if (context.isInContext(this)) return alias;
		
		return returnable.toReturnableString(context).concat(alias == null || alias.isEmpty() ? "" : " AS ").concat(alias);
		
	}

	@Override
	public String toOrderableString(HasContext context) {
		return alias;
	}
	
	@Override
	public String toString() {
		return toReturnableString(NoContext.get());
	}
	
}
