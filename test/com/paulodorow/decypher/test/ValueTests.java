package com.paulodorow.decypher.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.paulodorow.decypher.Parameter;
import com.paulodorow.decypher.Value;


public class ValueTests {

	@Test
	public void testString() {
		
		Value value = new Value("test");
		
		assertEquals("'test'", value.toString());
		
	}

	@Test
	public void testEmptyString() {
		
		Value value = new Value("");
		
		assertEquals("''", value.toString());
		
	}
	
	@Test
	public void testInteger() {
		
		Value value = new Value(100);
		
		assertEquals("100", value.toString());
		
	}

	@Test
	public void testDifferentTypes() {
		
		Value valueInt = new Value(100);
		Value valueStr = new Value("100");
		
		assertFalse(valueInt.equals(valueStr));
		
	}

	
	@Test
	public void testNull() {
		
		Value value = new Value();
		
		assertNull(value.get());
		
	}
	
	@Test
	public void testEquals() {
		
		Value value1 = new Value("test");
		Value value2 = new Value("test");
		
		assertTrue(value1.equals(value2));

		Value value3 = new Value("different test");
		assertFalse(value1.equals(value3));

		Value valueNull1 = new Value();
		Value valueNull2 = new Value();
		assertTrue(valueNull1.equals(valueNull2));
		assertFalse(valueNull1.equals(value1));
		
	}
	
	@Test
	public void testParameter() {
		
		Parameter parameter = new Parameter("parameter");
		
		Value value = new Value(parameter);
		
		assertEquals(value.toString(), "{parameter}");
		
	}

}
