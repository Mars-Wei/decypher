package com.paulodorow.decypher.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.paulodorow.decypher.Node;
import com.paulodorow.decypher.Parameter;
import com.paulodorow.decypher.Property;
import com.paulodorow.decypher.Relationship;
import com.paulodorow.decypher.Value;
import com.paulodorow.decypher.operations.Contains;
import com.paulodorow.decypher.operations.EndsWith;
import com.paulodorow.decypher.operations.RegExp;
import com.paulodorow.decypher.operations.StartsWith;

public class StringOperationsTests {

	@Test
	public void regExpTest() {
		
		Value parameterValue = new Value(new Parameter("name"));
		RegExp comparison = new RegExp(parameterValue, new Value(".*"));
		assertEquals("{name} ~= '.*'", comparison.toString());
		
		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);
		comparison.withOperand(property1); 
		assertEquals("n.property1 ~= '.*'", comparison.toString());
		
		Property property2 = new Property("property2");
		new Relationship("RELATIONSHIP").withProperties(property2).withVariable("r");
		comparison.withOperand(property2);
		assertEquals("r.property2 ~= '.*'", comparison.toString());

	}

	@Test
	public void startsWithTest() {
		
		Value parameterValue = new Value(new Parameter("name"));
		StartsWith comparison = new StartsWith(parameterValue, new Value("string"));
		assertEquals("{name} STARTS WITH 'string'", comparison.toString());
		
		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);
		comparison.withOperand(property1); 
		assertEquals("n.property1 STARTS WITH 'string'", comparison.toString());
		
		Property property2 = new Property("property2");
		new Relationship("RELATIONSHIP").withProperties(property2).withVariable("r");
		comparison.withOperand(property2).startsWith(parameterValue);
		assertEquals("r.property2 STARTS WITH {name}", comparison.toString());

	}

	@Test
	public void endsWithTest() {
		
		Value parameterValue = new Value(new Parameter("name"));
		EndsWith comparison = new EndsWith(parameterValue, new Value("string"));
		assertEquals("{name} ENDS WITH 'string'", comparison.toString());
		
		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);
		comparison.withOperand(property1); 
		assertEquals("n.property1 ENDS WITH 'string'", comparison.toString());
		
		Property property2 = new Property("property2");
		new Relationship("RELATIONSHIP").withProperties(property2).withVariable("r");
		comparison.withOperand(property2).endsWith(parameterValue);
		assertEquals("r.property2 ENDS WITH {name}", comparison.toString());

	}

	@Test
	public void containsTest() {
		
		Value parameterValue = new Value(new Parameter("name"));
		Contains comparison = new Contains(parameterValue, new Value("string"));
		assertEquals("{name} CONTAINS 'string'", comparison.toString());
		
		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);
		comparison.withOperand(property1); 
		assertEquals("n.property1 CONTAINS 'string'", comparison.toString());
		
		Property property2 = new Property("property2");
		new Relationship("RELATIONSHIP").withProperties(property2).withVariable("r");
		comparison.withOperand(property2).contains(parameterValue);
		assertEquals("r.property2 CONTAINS {name}", comparison.toString());

	}

}
