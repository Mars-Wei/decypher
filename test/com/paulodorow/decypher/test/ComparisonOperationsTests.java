package com.paulodorow.decypher.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.paulodorow.decypher.Node;
import com.paulodorow.decypher.Parameter;
import com.paulodorow.decypher.Property;
import com.paulodorow.decypher.Relationship;
import com.paulodorow.decypher.Value;
import com.paulodorow.decypher.operations.Equals;
import com.paulodorow.decypher.operations.EqualsOrGreaterThan;
import com.paulodorow.decypher.operations.EqualsOrLessThan;
import com.paulodorow.decypher.operations.GreaterThan;
import com.paulodorow.decypher.operations.LessThan;
import com.paulodorow.decypher.operations.NotEquals;

public class ComparisonOperationsTests {

	@Test
	public void equalsTest() {
		
		Equals equality = new Equals(new Value(1), new Value(2));
		assertEquals("1 = 2", equality.toString());
		
		Value parameterValue = new Value(new Parameter("number"));
		equality.equals(parameterValue);
		assertEquals("1 = 2 = {number}", equality.toString());
		
		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);
		equality.equals(property1); 
		assertEquals("1 = 2 = {number} = n.property1", equality.toString());
		
		Property property2 = new Property("property2");
		new Relationship("RELATIONSHIP").withProperties(property2).withVariable("r");
		equality.equals(property2);
		assertEquals("1 = 2 = {number} = n.property1 = r.property2", equality.toString());

		equality = new Equals(new Value(10), new Value(-10)).equals(parameterValue);
		assertEquals("10 = -10 = {number}", equality.toString());

	}
	
	@Test
	public void notEqualsTest() {
		
		NotEquals inequality = new NotEquals(new Value(1), new Value(2));
		assertEquals("1 <> 2", inequality.toString());
		
		Value parameterValue = new Value(new Parameter("number"));
		inequality.notEquals(parameterValue);
		assertEquals("1 <> 2 <> {number}", inequality.toString());
		
		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);
		inequality.notEquals(property1); 
		assertEquals("1 <> 2 <> {number} <> n.property1", inequality.toString());
		
		Property property2 = new Property("property2");
		new Relationship("RELATIONSHIP").withProperties(property2).withVariable("r");
		inequality.notEquals(property2);
		assertEquals("1 <> 2 <> {number} <> n.property1 <> r.property2", inequality.toString());

		inequality = new NotEquals(new Value(10), new Value(-10)).notEquals(parameterValue);
		assertEquals("10 <> -10 <> {number}", inequality.toString());

	}

	@Test
	public void lessThanTest() {
		
		LessThan comparison = new LessThan(new Value(1), new Value(2));
		assertEquals("1 < 2", comparison.toString());
		
		Value parameterValue = new Value(new Parameter("number"));
		comparison.lessThan(parameterValue);
		assertEquals("1 < 2 < {number}", comparison.toString());
		
		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);
		comparison.lessThan(property1); 
		assertEquals("1 < 2 < {number} < n.property1", comparison.toString());
		
		Property property2 = new Property("property2");
		new Relationship("RELATIONSHIP").withProperties(property2).withVariable("r");
		comparison.lessThan(property2);
		assertEquals("1 < 2 < {number} < n.property1 < r.property2", comparison.toString());

		comparison = new LessThan(new Value(10), new Value(-10)).lessThan(parameterValue);
		assertEquals("10 < -10 < {number}", comparison.toString());

	}

	@Test
	public void greaterThanTest() {
		
		GreaterThan comparison = new GreaterThan(new Value(1), new Value(2));
		assertEquals("1 > 2", comparison.toString());
		
		Value parameterValue = new Value(new Parameter("number"));
		comparison.greaterThan(parameterValue);
		assertEquals("1 > 2 > {number}", comparison.toString());
		
		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);
		comparison.greaterThan(property1); 
		assertEquals("1 > 2 > {number} > n.property1", comparison.toString());
		
		Property property2 = new Property("property2");
		new Relationship("RELATIONSHIP").withProperties(property2).withVariable("r");
		comparison.greaterThan(property2);
		assertEquals("1 > 2 > {number} > n.property1 > r.property2", comparison.toString());

		comparison = new GreaterThan(new Value(10), new Value(-10)).greaterThan(parameterValue);
		assertEquals("10 > -10 > {number}", comparison.toString());

	}

	@Test
	public void equalsOrGreaterThanTest() {
		
		EqualsOrGreaterThan comparison = new EqualsOrGreaterThan(new Value(1), new Value(2));
		assertEquals("1 >= 2", comparison.toString());
		
		Value parameterValue = new Value(new Parameter("number"));
		comparison.equalsOrGreaterThan(parameterValue);
		assertEquals("1 >= 2 >= {number}", comparison.toString());
		
		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);
		comparison.equalsOrGreaterThan(property1); 
		assertEquals("1 >= 2 >= {number} >= n.property1", comparison.toString());
		
		Property property2 = new Property("property2");
		new Relationship("RELATIONSHIP").withProperties(property2).withVariable("r");
		comparison.equalsOrGreaterThan(property2);
		assertEquals("1 >= 2 >= {number} >= n.property1 >= r.property2", comparison.toString());

		comparison = new EqualsOrGreaterThan(new Value(10), new Value(-10)).equalsOrGreaterThan(parameterValue);
		assertEquals("10 >= -10 >= {number}", comparison.toString());

	}

	@Test
	public void equalsOrLessThanTest() {
		
		EqualsOrLessThan comparison = new EqualsOrLessThan(new Value(1), new Value(2));
		assertEquals("1 <= 2", comparison.toString());
		
		Value parameterValue = new Value(new Parameter("number"));
		comparison.equalsOrLessThan(parameterValue);
		assertEquals("1 <= 2 <= {number}", comparison.toString());
		
		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);
		comparison.equalsOrLessThan(property1); 
		assertEquals("1 <= 2 <= {number} <= n.property1", comparison.toString());
		
		Property property2 = new Property("property2");
		new Relationship("RELATIONSHIP").withProperties(property2).withVariable("r");
		comparison.equalsOrLessThan(property2);
		assertEquals("1 <= 2 <= {number} <= n.property1 <= r.property2", comparison.toString());

		comparison = new EqualsOrLessThan(new Value(10), new Value(-10)).equalsOrLessThan(parameterValue);
		assertEquals("10 <= -10 <= {number}", comparison.toString());

	}


}
