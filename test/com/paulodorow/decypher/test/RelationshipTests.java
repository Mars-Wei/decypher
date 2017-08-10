package com.paulodorow.decypher.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.paulodorow.decypher.NoContext;
import com.paulodorow.decypher.Property;
import com.paulodorow.decypher.Relationship;
import com.paulodorow.decypher.RelationshipDistance;
import com.paulodorow.decypher.Value;

public class RelationshipTests {

	@Test
	public void relationshipTest() {

		Relationship relationship = new Relationship();

		relationship.withVariable("r");
		assertEquals("[r]", relationship.toString());

		relationship.withLabels("RELATIONSHIP");
		assertEquals("[r:RELATIONSHIP]", relationship.toString());

		relationship.withLabels("RELATIONSHIP", "RELATIONSHIP2", "RELATIONSHIP3");
		assertEquals("[r:RELATIONSHIP|:RELATIONSHIP2|:RELATIONSHIP3]", relationship.toString());

		relationship.variable().clear();
		assertEquals("[:RELATIONSHIP|:RELATIONSHIP2|:RELATIONSHIP3]", relationship.toString());
		
		relationship.labels().remove("RELATIONSHIP2");
		assertEquals("[:RELATIONSHIP|:RELATIONSHIP3]", relationship.toString());

	}
	
	@Test
	public void relationshipWithPropertiesTest() {
		
		Property property = new Property("value", new Value(0));
		
		Relationship relationship = new Relationship().withProperties(property);

		assertEquals("[{value: 0}]", relationship.toString());
		
		relationship.withVariable("r");
		assertEquals("[r {value: 0}]", relationship.toString());

		relationship.withLabels("RELATIONSHIP");
		assertEquals("[r:RELATIONSHIP {value: 0}]", relationship.toString());

		relationship.withLabels("RELATIONSHIP2");

		assertEquals(2, relationship.labels().count());
		assertEquals("[r:RELATIONSHIP|:RELATIONSHIP2 {value: 0}]", relationship.toString());

	}

	@Test
	public void relationshipWithDistanceTest() {
		
		Property property = new Property("value", new Value(0));
		
		RelationshipDistance distance = new RelationshipDistance().between(1, 3);
		
		Relationship relationship = new Relationship().withProperties(property).distance(distance);

		assertEquals("[*1..3 {value: 0}]", relationship.toString());
		
		relationship.withVariable("r");
		assertEquals("[r *1..3 {value: 0}]", relationship.toString());

		relationship.withLabels("RELATIONSHIP");
		assertEquals("[r:RELATIONSHIP *1..3 {value: 0}]", relationship.toString());

		relationship.withLabels("RELATIONSHIP2");

		assertEquals("[r:RELATIONSHIP|:RELATIONSHIP2 *1..3 {value: 0}]", relationship.toString());
		
		distance.clear();
		assertEquals("[r:RELATIONSHIP|:RELATIONSHIP2 {value: 0}]", relationship.toString());

		distance.withMinimum(4);
		assertEquals("[r:RELATIONSHIP|:RELATIONSHIP2 *4.. {value: 0}]", relationship.toString());

		distance.clear();
		distance.withMaximum(6);
		assertEquals("[r:RELATIONSHIP|:RELATIONSHIP2 *..6 {value: 0}]", relationship.toString());
		
	}

	@Test
	public void emptyRelationshipTest() {

		Relationship relationship = new Relationship();

		assertEquals("", relationship.toString());
		
		relationship.withLabels("RELATIONSHIP").labels().clear();

		assertEquals("", relationship.toString());

	}
	
	@Test
	public void relationshipVariableTest() {
		
		Relationship relationship = new Relationship();
		assertFalse(relationship.variable().isSet());
		
		assertTrue(relationship.toReturnableString(NoContext.get()).startsWith("r_"));
		assertTrue(relationship.toString().startsWith("[r_"));
		
		final String label = "RELATIONSHIP";
		relationship.variable().clear();
		relationship.labels().add(label);
		assertTrue(relationship.toReturnableString(NoContext.get()).startsWith(label.concat("_")));
		
		// +1 to count the added underline
		assertTrue(relationship.variable().get().length() > label.length() + 1);

		relationship.variable().set("var");
		assertTrue(relationship.toReturnableString(NoContext.get()).startsWith("var"));
		assertTrue(relationship.toString().startsWith("[var"));
		
	}
	
	@Test
	public void distanceTest() {

		RelationshipDistance distance = new RelationshipDistance();
		
		assertEquals("", distance.toString());
		
		distance.between(1, 5);
		assertEquals("*1..5", distance.toString());
		
		distance.clear();
		assertEquals("", distance.toString());
		
		distance.withMinimum(2);
		assertEquals("*2..", distance.toString());

		distance.clearMinimum();
		distance.withMaximum(3);
		assertEquals("*..3", distance.toString());

		distance.clearMaximum();
		assertEquals("", distance.toString());
		
		distance.setLength(2);
		assertEquals("*2", distance.toString());

		distance.setInfinite(true);
		assertEquals("*..", distance.toString());
		
		distance.clear();
		assertEquals("", distance.toString());

	}
	
}
