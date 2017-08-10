package com.paulodorow.decypher;

public abstract class Entity<D>  extends Token implements HasLabelsDecorator<D>, HasVariableDecorator<D>, HasPropertiesDecorator<D>, IsReturnable<D> {

	private Labels labels;
	private Variable variable;
	private Properties properties;

	public Entity() {
		this("");
	}

	public Entity(String labelsSeparator) {
		labels = new Labels(labelsSeparator);
		variable = new Variable();
		properties = new Properties(this);
	}
	
	@Override
	public Labels labels() {
		return labels;
	}

	@Override
	public Variable variable() {
		return variable;
	}
	
	@Override
	public Properties properties() {
		return properties;
	}
	
	public void suppressPatternNulls() {
		properties().suppressNullsInPatterns();
	}

	public void enforcePatternNulls() {
		properties().enforceNullsInPatterns();
	}

}
