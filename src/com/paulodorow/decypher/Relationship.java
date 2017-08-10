package com.paulodorow.decypher;

public class Relationship extends Entity<Relationship> {

	private static final String LABEL_SEPARATOR = "|";
	public static final String AUTOMATIC_LABEL_PREFIX = "r";
	
	private RelationshipDistance distance;
	
	public Relationship() {
		
		super(LABEL_SEPARATOR);
	}
	
	public Relationship(String... labels) {
		this();
		labels().add(labels);
	}
	
	public void setDistance(RelationshipDistance distance) {
		this.distance = distance;
	}

	public RelationshipDistance getDistance() {
		return distance;
	}
	
	public Relationship distance(RelationshipDistance distance) {
		setDistance(distance);
		return this;
	}
	
	public void clearDistance() {
		setDistance(null);
	}

	private String generateVariableName() {
		String label = labels().getSingleLabelString();
		if (label.isEmpty()) label = AUTOMATIC_LABEL_PREFIX;
		String id = Integer.toHexString((int)(Math.random() * Integer.MAX_VALUE));
		return label.concat("_").concat(id);
	}
	
	public void enforceVariableName() {
		if (!variable().isSet()) variable().set(generateVariableName());
	}

	@Override
	public String toReturnableString(HasContext context) {
		
		enforceVariableName();
		
		if (context.isInContext(this)) return context.getInContext(this).toReturnableString(context);

		return variable().toString();
		
	}

	@Override
	public String toString() {

		boolean hasVariable = variable().isSet();
		boolean hasLabels = labels().hasLabels();
		boolean hasDistance = distance != null && distance.isSet();
		boolean hasProperties = properties().hasProperties();
		boolean hasContent = hasVariable || hasLabels || hasDistance || hasProperties;

		StringBuilder builder = new StringBuilder();

		if (hasContent) {
			
			builder.append("[");

			if (hasVariable) builder.append(variable());
			
			if (hasLabels) builder.append(labels());
			
			if (hasDistance) {
				if (hasVariable || hasLabels) builder.append(" ");
				builder.append(distance);
			}
			
			if (hasProperties) {
				String propertiesStr = properties().toString();
				boolean hasContents = (hasVariable || hasLabels || hasDistance);
				if (hasContents && !propertiesStr.isEmpty()) builder.append(" ");
				builder.append(propertiesStr);
			}
		
			builder.append("]");
			
		}

		return builder.toString();
		
	}
	
}
