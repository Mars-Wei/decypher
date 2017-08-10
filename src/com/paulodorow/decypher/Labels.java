package com.paulodorow.decypher;

import java.util.LinkedHashSet;
import java.util.Set;

public class Labels extends Token {

	public static final String LABEL_PREFIX = ":";

	private Set<String> labels;

	private String separator;

	public Labels() {
		this("");
	}

	public Labels(String separator) {
		this.labels = new LinkedHashSet<>();
		this.separator  = separator;
	}

	public void clear() {
		labels.clear();
	}
	
	/**
	 * 
	 * Adds several labels to collection.
	 * 
	 * Empty and null labels will be ignored.
	 * 
	 * @param labels labels to be added.
	 */
	public void add(String... labels) {
		for (String label : labels) 
			if (label != null && !label.isEmpty()) this.labels.add(label);
	}
	
	public void remove(String label) {
		labels.remove(label);
	}
	
	public boolean has(String label) {
		return labels.contains(label);
	}
	
	public boolean hasLabels() {
		return labels.size() > 0;
	}
	
	public int count() {
		return labels.size();
	}
	
	@Override
	public String toString() {
		
		StringBuilder labelsBuilder = new StringBuilder();
		for (String label : labels) {
			
			if (separator != null && !separator.isEmpty() && labelsBuilder.length() > 0) labelsBuilder.append(separator);
			
			labelsBuilder.append(LABEL_PREFIX).append(label);
			
		}

		return labelsBuilder.toString();
		
	}

	public String getSingleLabelString() {
		if (count() > 0) return labels.iterator().next();
		return "";
	}

}
