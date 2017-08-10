package com.paulodorow.decypher.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.paulodorow.decypher.Node;
import com.paulodorow.decypher.Properties;
import com.paulodorow.decypher.Property;
import com.paulodorow.decypher.Value;

public class PropertiesTests {

	@Test
	public void isEmptyTest() {
		
		Properties properties = new Properties(null);
		assertTrue(properties.isEmpty());
		
		Property property = new Property("name", null);

		properties.add(property);
		assertFalse(properties.isEmpty());
		
		properties.remove(property);
		assertTrue(properties.isEmpty());

		properties.add(property);
		assertFalse(properties.isEmpty());

		properties.clear();
		assertTrue(properties.isEmpty());
		
	}
	
	@Test
	public void clearTest() {

		Properties properties = new Properties(null);
		Property property = new Property("name", null);

		properties.add(property);
		properties.clear();
		
		assertTrue(properties.isEmpty());

	}

	@Test
	public void containsTest() {

		Properties properties = new Properties(null);
		Property property = new Property("name", null);
		properties.add(property);
		
		assertTrue(properties.contains("name"));
		assertTrue(properties.contains(property));
		
	}

	/**
	 * Tests an error in which changing a property value
	 * would make it impossible to remove it from the properties list
	 * because of <code>hashCode()</code> change in the property.
	 */
	@Test
	public void propertyValueChangeTest() {
	
		Property property = new Property("name");
	
		Properties properties = new Properties(null).with(property);
	
		property.setValue(new Value("value"));
		
		properties.remove(property);
	
		assertTrue(properties.isEmpty());
	
	}
	
	@Test
	public void differentPropertiesSameNameEqualsTest() {
	
		Property property1 = new Property("prop");
		Property property2 = new Property(property1.getName());
		
		new Node().withVariable("n").withProperties(property1);
		new Node().withVariable("m").withProperties(property2);
		
		assertFalse(property1.equals(property2));
		
	}

}
