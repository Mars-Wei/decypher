package com.paulodorow.decypher;

public class Path extends Token implements ParticipatesInRelationships, HasVariableDecorator<Path>, IsReturnable<Path> {

	public enum Direction {
		
		//TODO implement left to right and right to left directions instead of unidirectional
		UNIDIRECTIONAL,
		BIDIRECTIONAL
		
	}
	
	private static final Node EMPTY_NODE = new Node();
	
	private ParticipatesInRelationships from;
	private ParticipatesInRelationships to;
	private Relationship relationship;
	private Direction direction;
	private Variable variable;
	
	public Path() {
		super();
		direction = Direction.UNIDIRECTIONAL;
		variable = new Variable();
	}

	public Path(String variable) {
		this();
		variable().set(variable);
	}

	public void setFrom(ParticipatesInRelationships from) {
		setDirection(Direction.UNIDIRECTIONAL);
		this.from = from;
	}
	
	public Path from(ParticipatesInRelationships from) {
		setFrom(from);
		return this;
	}
	
	public void clearFrom() {
		setFrom(null);
	}
	
	public ParticipatesInRelationships getFrom() {
		return from == null ? EMPTY_NODE : from;
	}
	
	public void setTo(ParticipatesInRelationships to) {
		setDirection(Direction.UNIDIRECTIONAL);
		this.to = to;
	}

	public Path to(ParticipatesInRelationships to) {
		setTo(to);
		return this;
	}
	
	public void clearTo() {
		setTo(null);
	}
	
	public ParticipatesInRelationships getTo() {
		return to == null ? EMPTY_NODE : to;
	}
	
	public void setRelationship(Relationship relationship) {
		this.relationship = relationship;
	}
	
	public Path withRelationship(Relationship relationship) {
		setRelationship(relationship);
		return this;
	}
	
	public Relationship getRelationship() {
		return this.relationship;
	}
	
	public void clearRelationship() {
		setRelationship(null);
	}
	
	public Path fromTo(ParticipatesInRelationships from, ParticipatesInRelationships to) {
		setFrom(from);
		setTo(to);
		return this;
	}

	public Path between(ParticipatesInRelationships entity1, ParticipatesInRelationships entity2) {
		setFrom(entity1);
		setTo(entity2);
		setDirection(Direction.BIDIRECTIONAL);
		return this;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Path unidirectional() {
		setDirection(Direction.UNIDIRECTIONAL);
		return this;
	}

	public Path bidirectional() {
		setDirection(Direction.BIDIRECTIONAL);
		return this;
	}

	public Direction getDirection() {
		return direction;
	}
	
	public Path direction(Direction direction) {
		setDirection(direction);
		return this;
	}
	
	@Override
	public Variable variable() {
		return variable;
	}


	@Override
	public String toReturnableString(HasContext context) {
		return variable().toString();
	}
	
	protected String toPathString(HasContext context) {

		ParticipatesInRelationships from = getFrom();
		ParticipatesInRelationships to = getTo();

		StringBuilder builder = new StringBuilder();
		
		builder.append(from.toPatternString(context)).append("-");
		
		if (relationship != null) builder.append(relationship);
		
		builder.append("-");
		
		if (Direction.UNIDIRECTIONAL.equals(getDirection())) builder.append(">");
		
		builder.append(to.toPatternString(context));
		
		return builder.toString();

	}

	@Override
	public String toPatternString(HasContext context) {
		
		if (variable().isSet()) 
			return String.format("%s=%s", variable(), toPathString(context));
		else
			return toPathString(context);
		
	}
	
	@Override
	public String toPredicateString(HasContext context) {
		return toPathString(context);
	}
	
	@Override
	public String toString() {
		return toPatternString(NoContext.get()); 
	}
	
}
