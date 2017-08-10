package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsOperand;

public class PercentileDisc extends AggregateFunction<PercentileDisc> {

	private IsOperand<?> value;
	private IsOperand<?> percentile;

	public PercentileDisc() {
		
	}

	public PercentileDisc(IsOperand<?> value, IsOperand<?> percentile) {
		setValue(value);
		setPercentile(percentile);
	}

	public void setValue(IsOperand<?> value) {
		this.value = value;
	}

	public PercentileDisc withValue(IsOperand<?> value) {
		setValue(value);
		return this;
	}

	public void setPercentile(IsOperand<?> percentile) {
		this.percentile = percentile;
	}
	
	public PercentileDisc withPercentile(IsOperand<?> percentile) {
		setPercentile(percentile);
		return this;
	}
	
	@Override
	public PercentileDisc withDistinct(boolean distinct) {
		setDistinct(distinct);
		return this;
	}

	@Override
	public String toReturnableString(HasContext context) {
		
		StringBuilder count = new StringBuilder("percentileDisc(");
		
		if (isDistinct()) count.append("DISTINCT ");
		
		count.append(context.getInContext(value).toReturnableString(context)).append(", ").append(context.getInContext(percentile).toReturnableString(context));
		
		count.append(")");
		
		return count.toString();
		
	}

}
