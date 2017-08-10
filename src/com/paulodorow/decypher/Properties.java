package com.paulodorow.decypher;

import java.util.Iterator;
import java.util.Set;

public class Properties extends Token implements Iterable<Property> {

	public Set<Property> properties;
	private Entity<?> parent;
	private boolean suppressNulls;

	public Properties(Entity<?> parent) {
		properties = new ArrayListSet<>();
		this.parent = parent;
		suppressNullsInPatterns();
	}

	@Override
	public Iterator<Property> iterator() {
		
		return new Iterator<Property>() {
			
			Iterator<Property> iterator = properties.iterator();
			Property next;
			
			@Override
			public Property next() {
				next = iterator.next();
				return next;
			}
			
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}
			
			@Override
			public void remove() {
				iterator.remove();
				next.setParentEntity(null);
			}
			
		};
		
	}

	public void add(Property... properties) {
		
		for (Property property : properties) 
			if (property != null) {
				this.properties.add(property);
				property.setParentEntity(parent);
			}
		
	}

	public Properties with(Property... properties) {
		add(properties);
		return this;
	}

	public void remove(String propertyName) {
		
		Iterator<Property> propertiesIterator = properties.iterator();
		while (propertiesIterator.hasNext()) {
			Property property = propertiesIterator.next();
			property.setParentEntity(null);
			if (propertyName.equals(property.getName())) propertiesIterator.remove();
		}
		
	}

	public void remove(Property property) {
		
		properties.remove(property);
		property.setParentEntity(null);
		
	}

	public boolean isEmpty() {
		return properties.isEmpty();
	}

	public void clear() {
		properties.clear();
	}

	public boolean contains(Property property) {
		return properties.contains(property);
	}

	public boolean contains(String propertyName) {
		
		if (propertyName == null) return false;
		
		Iterator<Property> iterator = iterator();
		while (iterator.hasNext()) if (propertyName.equals(iterator.next().getName())) return true;
		
		return false;
		
	}

	public int count() {
		return properties.size();
	}

	/**
	 * Checks if there are any properties in the collection 
	 * @return true if collection contains properties, false otherwise.
	 */
	public boolean hasProperties() {
		return count() > 0;
	}

	/**
	 * Checks for non-null values in the collection.
	 * @return true if any properties in the collection have a non-null value, false otherwise.
	 */
	public boolean hasNonNullProperties() {
		
		for (Property property : properties) if (!property.getValue().isNull()) return true;
		
		return false;
		
	}
	
	public void suppressNullsInPatterns() {
		suppressNulls = true;
	}
	
	public void enforceNullsInPatterns() {
		suppressNulls = false;
	}

	@Override
	public String toString() {
		return toString(NoContext.get());
	}

	public String toString(HasContext context) {

		StringBuilder propertiesBuilder = new StringBuilder();
		for (Property property : properties) {
			
			if (propertiesBuilder.length() > 0) propertiesBuilder.append(", ");
			
			if (!suppressNulls || !property.getValue().isNull()) propertiesBuilder.append(property.toString(context));
			
		}
		
		if (propertiesBuilder.length() == 0) return "";
		
		StringBuilder result = new StringBuilder().append("{").append(propertiesBuilder).append("}");
		
		return result.toString();

	}
	
}
