package com.paulodorow.decypher.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.paulodorow.decypher.Property;
import com.paulodorow.decypher.Value;
import com.paulodorow.decypher.functions.ToString;
import com.paulodorow.decypher.operations.Addition;

public class PropertyTests {

	@Test
	public void testStringProperty() {
		
		Property property = new Property("name", new Value("value"));
		assertEquals("name: \'value\'", property.toString());
		
	}
	
	@Test
	public void testNullProperty() {
		
		Property property = new Property("name", null);
		assertEquals(property.toString(), "name IS NULL");
		
		property.setValue(new Value(null));
		assertEquals(property.toString(), "name IS NULL");
		
	}
	
	@Test
	public void testEquals() {
		
		Property property = new Property("name", new Value("value"));
		Property propertySame = new Property("name", new Value("value"));
		
		assertTrue(property.equals(propertySame));

		Property propertyDifferentName = new Property("different name", new Value("value"));
		assertFalse(property.equals(propertyDifferentName));
		
		Property propertyDifferentValue = new Property("name", new Value("different value"));
		assertFalse(property.equals(propertyDifferentValue));
		
	}
	
	@Test
	public void testCompleteValues() {

		Property property = new Property("name", new Value(new Addition(new Value(1), new Value(1))));
		
		assertEquals("name: 1 + 1", property.toString());
		
		property.setValue(new Value(new ToString(new Value(1))));
		assertEquals("name: toString(1)", property.toString());
		
	}
	
}
