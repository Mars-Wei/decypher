package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsReturnable;

public class Length extends Function<Length> {

	private IsReturnable<?> operand;

	public Length() {
		super();
	}
	
	public Length(IsReturnable<?> operand) {
		this();
		setOperand(operand);
	}
	
	public void setOperand(IsReturnable<?> operand) {
		this.operand = operand;
	}
	
	public IsReturnable<?> getOperand() {
		return this.operand;
	}
	
	public Length withOperand(IsReturnable<?> operand) {
		setOperand(operand);
		return this;
	}

	@Override
	public String toReturnableString(HasContext context) {
		return String.format("length(%s)", context.getInContext(getOperand()).toReturnableString(context));
	}

}
