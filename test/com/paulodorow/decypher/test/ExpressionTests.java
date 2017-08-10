package com.paulodorow.decypher.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.paulodorow.decypher.Token;
import com.paulodorow.decypher.Node;
import com.paulodorow.decypher.Path;
import com.paulodorow.decypher.Relationship;
import com.paulodorow.decypher.RelationshipDistance;
import com.paulodorow.decypher.Value;

public class ExpressionTests {

	private static final Value value = new Value("v");
	private static final Value valueEquals = new Value("v");
	private static final Value anotherValue = new Value("w");

	private static final Node node = new Node("A");
	private static final Node nodeEquals = new Node("A");
	private static final Node anotherNode = new Node("B");

	private static final Relationship relationship = new Relationship().withLabels("R");
	private static final Relationship relationshipEquals = new Relationship().withLabels("R");
	private static final Relationship anotherRelationship = new Relationship();
	
	private static final RelationshipDistance relationshipDistance = new RelationshipDistance().withInfinite(true);
	private static final RelationshipDistance relationshipDistanceEquals = new RelationshipDistance().withInfinite(true);
	private static final RelationshipDistance anotherRelationshipDistance = new RelationshipDistance().between(1, 2);
	
	private static final Path path = new Path();
	private static final Path samePath = new Path();
	private static final Path anotherPath = new Path().from(node);

	@Test
	public void testEquals() {

		testEquals(value, valueEquals, "'v'", anotherValue);
		testEquals(node, nodeEquals, "(:A)", anotherNode);
		testEquals(relationship, relationshipEquals, "[:R]", anotherRelationship);
		testEquals(relationshipDistance, relationshipDistanceEquals, "*..", anotherRelationshipDistance);
		testEquals(path, samePath, "()-->()", anotherPath);
		
		assertFalse(relationship.equals(node));
		assertFalse(node.equals(relationship));

	}

	private static void testEquals(Token expression, Token expressionEquals, String expressionAsString, Token anotherExpression) {

		assertTrue(String.format("%s==%s", expression, expressionEquals), expression.equals(expressionEquals));
		assertFalse(String.format("%s<>%s", expression, anotherExpression), expression.equals(anotherExpression));

		assertTrue(String.format("String(%s)==%s", expressionAsString, expression), expressionAsString.equals(expression.toString()));
		assertTrue(String.format("%s==String(%s)", expression, expressionAsString), expression.toString().equals(expressionAsString));

	}

	@Test 
	public void testHashCode() {
		
		testHasCode(value);
		testHasCode(node);
		testHasCode(relationship);
		testHasCode(relationshipDistance);
		testHasCode(path);
		
	}
	
	private static void testHasCode(Token expression) {
		
		String expressionAsString = expression.toString();
		assertEquals(expression.hashCode(), expressionAsString.hashCode());	
		
	}
	
	
}
