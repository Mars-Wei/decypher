package com.paulodorow.decypher.operations;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class RegExp extends Predicate<RegExp> {

	IsOperand<?> operand;
	IsOperand<?> regExp;
	
	public RegExp() {

	}
	
	public RegExp(IsOperand<?> operand, IsOperand<?> regExp) {
		this();
		this.operand = operand;
		this.regExp = regExp;
	}
	
	public RegExp withOperand(IsOperand<?> operand) {
		this.operand = operand;
		return this;
	}
	
	public RegExp withRegExp(IsOperand<?> regExp) {
		this.regExp = regExp;
		return this;
	}
	
	@Override
	public String toReturnableString(HasContext context) {
		return String.format("%s ~= %s", context.getInContext(operand).toReturnableString(context), context.getInContext(regExp).toReturnableString(context));
	}

}
