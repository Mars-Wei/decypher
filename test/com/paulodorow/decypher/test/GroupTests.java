package com.paulodorow.decypher.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.paulodorow.decypher.Node;
import com.paulodorow.decypher.Property;
import com.paulodorow.decypher.Value;
import com.paulodorow.decypher.operations.And;
import com.paulodorow.decypher.operations.GreaterThan;
import com.paulodorow.decypher.operations.LessThan;
import com.paulodorow.decypher.operations.Not;
import com.paulodorow.decypher.operations.Or;

public class GroupTests {

	@Test
	public void groupTest() {
	
		Property property = new Property("active");
		Property property2 = new Property("age");
		new Node("Person").withVariable("p").withProperties(property, property2);
		
		Not not = new Not(property).group().not();
		assertEquals("NOT (NOT p.active)", not.toString());
		
		GreaterThan greaterThan = new GreaterThan(new Value(1), new Value(10));
		LessThan lessThan = new LessThan(new Value(10), property2);
		not.not(property);
		Or or = new And(greaterThan, lessThan).group().or(not.group());
		assertEquals("(1 > 10 AND 10 < p.age) OR (NOT p.active)", or.toString());
				
	}
	
}
