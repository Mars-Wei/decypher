package com.paulodorow.decypher;

public class RelationshipDistance extends Token {

	private static final String DISTANCE_HEADER = "*";
	private static final String DISTANCE_SEPARATOR = "..";
	
	private static final int NO_VALUE = 0;

	private int minimum;
	private int maximum;
	private int length;
	private boolean infinite;

	public RelationshipDistance() {
		setMinimum(NO_VALUE);
		setMaximum(NO_VALUE);
		setLength(NO_VALUE);
		setInfinite(false);
	}

	public RelationshipDistance(int minimum, int maximum) {
		this();
		setMinimum(minimum);
		setMaximum(maximum);
	}
	
	public void setMinimum(int minimum) {
		keepDistanceOnly();
		this.minimum = minimum;
	}
	
	public int getMinimum() {
		return minimum;
	}
	
	public RelationshipDistance withMinimum(int minimum) {
		setMinimum(minimum);
		return this;
	}
	
	public void clearMinimum() {
		minimum = NO_VALUE;
	}

	public void setMaximum(int maximum) {
		keepDistanceOnly();
		this.maximum = maximum;
	}
	
	public int getMaximum() {
		return maximum;
	}

	public RelationshipDistance withMaximum(int maximum) {
		setMaximum(maximum);
		return this;
	}

	public void clearMaximum() {
		maximum = NO_VALUE;
	}

	private void keepDistanceOnly() {
		clearLength();
		clearInfinite();
	}
	
	public void clearDistance() {
		clearMinimum();
		clearMaximum();
	}

	public void setLength(int length) {
		clearDistance();
		clearInfinite();
		this.length = length;
	}
	
	public int getLength() {
		return length;
	}
	
	public RelationshipDistance withLength(int length) {
		setLength(length);
		return this;
	}
	
	public void clearLength() {
		length = NO_VALUE;
	}

	public void setInfinite(boolean infinite) {
		clearDistance();
		clearLength();
		this.infinite = infinite;
	}
	
	public boolean isInfinite() {
		return infinite;
	}

	private void clearInfinite() {
		this.infinite = false;
	}
	
	public RelationshipDistance withInfinite(boolean infinite) {
		setInfinite(infinite);
		return this;
	}

	public void clear() {
		clearMinimum();
		clearMaximum();
		clearLength();
		setInfinite(false);
	}

	public RelationshipDistance between(int minimum, int maximum) {
		setMinimum(minimum);
		setMaximum(maximum);
		return this;
	}

	public boolean isSet() {
		return minimum != NO_VALUE || maximum != NO_VALUE || length != NO_VALUE || infinite;
	}
	
	@Override
	public String toString() {
		
		if (length != NO_VALUE) return String.format("*%d", length);
		
		if (isInfinite()) return "*..";
		
		if (minimum == NO_VALUE && maximum == NO_VALUE) return "";
		
		StringBuilder builder = new StringBuilder(DISTANCE_HEADER);

		int minimum = getMinimum();
		int maximum = getMaximum();
		
		if (minimum != NO_VALUE) builder.append(minimum);
	
		builder.append(DISTANCE_SEPARATOR);
		
		if (maximum != NO_VALUE) builder.append(maximum);
		
		return builder.toString();
		
	}

}
