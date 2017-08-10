package com.paulodorow.decypher;

public interface HasReturnDecorator<D> extends Decorator<D> {

	Return returns();
	
	default D returns(IsReturnable<?>... returnables) {
		returns().addReturnables(returnables);
		return getDecorated();
	}

	With with();

	default With with(IsReturnable<?>... returnables) {
		with().addReturnables(returnables);
		return with();
	}

	@SuppressWarnings("unchecked")
	default <S extends IsStatement> S with(S chainedStatement, IsReturnable<?>... returnables) {
		return (S) with().withChainedStatement(chainedStatement).withReturnables(returnables).getChainedStatement();
	}

	Match match();
	
	default Match match(IsPattern... patterns) {
		match().addPatterns(patterns);
		return match();
	}

	Create create();

	default Create create(IsPattern... patterns) {
		create().addPatterns(patterns);
		return create();
	}

}
