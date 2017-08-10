package com.paulodorow.decypher;

public class Property extends Token implements IsOperand<Property> {

	private String name;
	private Value value;
	private Entity<?> parent;
	
	public Property() {
		setValue(new Value(null));
	}
	
	public Property(String name, Value value) {
		this();
		setName(name);
		setValue(value);
	}

	public Property(String name) {
		this();
		setName(name);
	}

	public void setParentEntity(Entity<?> parent) {
		this.parent = parent;
	}

	public Property withParentEntity(Entity<?> parent) {
		setParentEntity(parent);
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Property name(String name) {
		setName(name);
		return this;
	}
	
	private String getParentName(HasContext context) {
		String name = parent == null ? "" : parent.toReturnableString(context);
		return name.isEmpty() ? ""  : name.concat(".");
	}

	public void setValue(Value value) {
		this.value = value == null ? new Value(null) : value;
	}
	
	public Value getValue() {
		return this.value;
	}
	
	public Property value(Value value) {
		setValue(value);
		return this;
	}

	@Override
	public String toString() {
		return toString(NoContext.get());
	}

	public String toString(HasContext context) {

		StringBuilder builder = new StringBuilder();
		
		builder.append(getName());
		
		if (value == null || value.isNull())
			builder.append(" ").append("IS NULL");
		else
			builder.append(": ").append(context.getInContext(getValue()).toReturnableString(context));
		
		return builder.toString();
		
	}

	@Override
	public String toReturnableString(HasContext context) {
		
		//IsReturnable<?> contextProperty = context.getInContext(this);
		//if (contextProperty != this) return contextProperty.toReturnableString(context); 
		
		return getParentName(context).concat(getName());
	}
 
	@Override
	public String toOrderableString(HasContext context) {
		return toReturnableString(context);
		//return context.getInContext(this).toReturnableString(context);
	}

	private String toValueAssignmentString(HasContext context) {
		//return context.getInContext(this).toReturnableString(context).concat("=").concat(getValue().toReturnableString(context));
		return toReturnableString(context).concat("=").concat(context.getInContext(getValue()).toReturnableString(context));
	}
	
	@Override
	public boolean equals(Object obj) {

		if (obj == null) return false;
		
		if (obj instanceof Property) return toValueAssignmentString(NoContext.get()).equals(((Property)obj).toValueAssignmentString(NoContext.get()));
		
		return toReturnableString(NoContext.get()).equals(obj.toString());

	}
	
	@Override
	public int hashCode() {
		return toValueAssignmentString(NoContext.get()).hashCode();
	}
	
}
