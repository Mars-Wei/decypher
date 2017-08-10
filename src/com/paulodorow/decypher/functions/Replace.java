package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class Replace extends Function<Replace> {

	private IsOperand<?> original;
	private IsOperand<?> search;
	private IsOperand<?> replacement;

	public Replace() {
		
	}
	
	public Replace(IsOperand<?> original, IsOperand<?> search, IsOperand<?> replacement) {
		this();
		this.original = original;
		this.search = search;
		this.replacement = replacement;
	}

	public IsOperand<?> getOriginal() {
		return original;
	}

	public void setOriginal(IsOperand<?> original) {
		this.original = original;
	}
	
	public Replace withOriginal(IsOperand<?> original) {
		setOriginal(original);
		return this;
	}

	public IsOperand<?> getSearch() {
		return search;
	}

	public void setSearch(IsOperand<?> search) {
		this.search = search;
	}

	public Replace withSearch(IsOperand<?> search) {
		setSearch(search);
		return this;
	}
	
	public IsOperand<?> getReplacement() {
		return replacement;
	}

	public void setReplacement(IsOperand<?> replacement) {
		this.replacement = replacement;
	}
	
	public Replace withReplacement(IsOperand<?> replacement) {
		setReplacement(replacement);
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("replace(%s, %s, %s)", context.getInContext(original).toReturnableString(context), 
				context.getInContext(search).toReturnableString(context), 
				context.getInContext(replacement).toReturnableString(context));
	}
	
}
