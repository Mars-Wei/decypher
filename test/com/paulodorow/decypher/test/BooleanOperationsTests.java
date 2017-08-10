package com.paulodorow.decypher.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.paulodorow.decypher.Node;
import com.paulodorow.decypher.Parameter;
import com.paulodorow.decypher.Property;
import com.paulodorow.decypher.Relationship;
import com.paulodorow.decypher.Value;
import com.paulodorow.decypher.operations.And;
import com.paulodorow.decypher.operations.Equals;
import com.paulodorow.decypher.operations.Not;
import com.paulodorow.decypher.operations.NotEquals;
import com.paulodorow.decypher.operations.Or;
import com.paulodorow.decypher.operations.Xor;

public class BooleanOperationsTests {

	@Test
	public void andTest() {
		
		Equals comparison1 = new Equals(new Value(1), new Value(2));
		NotEquals comparison2 = new NotEquals(new Value(1), new Parameter("number"));
		
		And and = new And(comparison1, comparison2);
		assertEquals("1 = 2 AND 1 <> {number}", and.toString());
		
		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);
		comparison1.equals(property1); 
		assertEquals("1 = 2 = n.property1 AND 1 <> {number}", and.toString());
		
		Property property2 = new Property("property2");
		new Relationship("RELATIONSHIP").withProperties(property2).withVariable("r");
		Equals comparison3 = new Equals(property2, new Value("name"));
		and.and(comparison3);
		
		assertEquals("1 = 2 = n.property1 AND 1 <> {number} AND r.property2 = 'name'", and.toString());

		and = new And(property1, property2);
		assertEquals("n.property1 AND r.property2", and.toString());
		
	}		


	@Test
	public void orTest() {
		
		Equals comparison1 = new Equals(new Value(1), new Value(2));
		NotEquals comparison2 = new NotEquals(new Value(1), new Parameter("number"));
		
		Or or = new Or(comparison1, comparison2);
		assertEquals("1 = 2 OR 1 <> {number}", or.toString());
		
		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);
		comparison1.equals(property1); 
		assertEquals("1 = 2 = n.property1 OR 1 <> {number}", or.toString());
		
		Property property2 = new Property("property2");
		new Relationship("RELATIONSHIP").withProperties(property2).withVariable("r");
		Equals comparison3 = new Equals(property2, new Value("name"));
		or.or(comparison3);
		
		assertEquals("1 = 2 = n.property1 OR 1 <> {number} OR r.property2 = 'name'", or.toString());

		or = new Or(property1, property2);
		assertEquals("n.property1 OR r.property2", or.toString());

	}

	@Test
	public void xorTest() {
		
		Equals comparison1 = new Equals(new Value(1), new Value(2));
		NotEquals comparison2 = new NotEquals(new Value(1), new Parameter("number"));
		
		Xor xor = new Xor(comparison1, comparison2);
		assertEquals("1 = 2 XOR 1 <> {number}", xor.toString());
		
		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);
		comparison1.equals(property1); 
		assertEquals("1 = 2 = n.property1 XOR 1 <> {number}", xor.toString());
		
		Property property2 = new Property("property2");
		new Relationship("RELATIONSHIP").withProperties(property2).withVariable("r");
		Equals comparison3 = new Equals(property2, new Value("name"));
		xor.xor(comparison3);
		
		assertEquals("1 = 2 = n.property1 XOR 1 <> {number} XOR r.property2 = 'name'", xor.toString());

		xor = new Xor(property1, property2);
		assertEquals("n.property1 XOR r.property2", xor.toString());

	}

	@Test
	public void notTest() {
		
		Equals comparison1 = new Equals(new Value(1), new Value(2));
		
		Not not = new Not(comparison1);
		assertEquals("NOT 1 = 2", not.toString());
		
		NotEquals comparison2 = new NotEquals(new Value(1), new Parameter("number"));
		not.not(comparison2);
		assertEquals("NOT 1 <> {number}", not.toString());
		
		Xor xor = new Xor(comparison1, comparison2);
		not.not(xor);
		assertEquals("NOT 1 = 2 XOR 1 <> {number}", not.toString());

		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);

		not = new Not(property1);
		assertEquals("NOT n.property1", not.toString());

	}

}
