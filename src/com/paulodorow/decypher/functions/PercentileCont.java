package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class PercentileCont extends AggregateFunction<PercentileCont> {

	private IsOperand<?> value;
	private IsOperand<?> percentile;

	public PercentileCont() {
		
	}

	public PercentileCont(IsOperand<?> value, IsOperand<?> percentile) {
		setValue(value);
		setPercentile(percentile);
	}

	public void setValue(IsOperand<?> value) {
		this.value = value;
	}

	public PercentileCont withValue(IsOperand<?> value) {
		setValue(value);
		return this;
	}

	public void setPercentile(IsOperand<?> percentile) {
		this.percentile = percentile;
	}
	
	public PercentileCont withPercentile(IsOperand<?> percentile) {
		setPercentile(percentile);
		return this;
	}
	
	@Override
	public PercentileCont withDistinct(boolean distinct) {
		setDistinct(distinct);
		return this;
	}

	@Override
	public String toReturnableString(HasContext context) {
		
		StringBuilder count = new StringBuilder("percentileCont(");
		
		if (isDistinct()) count.append("DISTINCT ");
		
		count.append(context.getInContext(value).toReturnableString(context)).append(", ").append(context.getInContext(percentile).toReturnableString(context));
		
		count.append(")");
		
		return count.toString();

	}

}
