package com.paulodorow.decypher.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.paulodorow.decypher.Alias;
import com.paulodorow.decypher.Node;
import com.paulodorow.decypher.Parameter;
import com.paulodorow.decypher.Property;
import com.paulodorow.decypher.Relationship;
import com.paulodorow.decypher.Return;
import com.paulodorow.decypher.Value;
import com.paulodorow.decypher.With;
import com.paulodorow.decypher.functions.Avg;
import com.paulodorow.decypher.functions.Sign;
import com.paulodorow.decypher.functions.Sum;
import com.paulodorow.decypher.functions.Timestamp;
import com.paulodorow.decypher.operations.Addition;
import com.paulodorow.decypher.operations.Group;
import com.paulodorow.decypher.operations.Multiplication;
import com.paulodorow.decypher.operations.NotEquals;
import com.paulodorow.decypher.operations.Xor;

public class WithTests {

	@Test
	public void allEntitiesTest() {
		
		With with = new With().all();
	
		assertEquals("WITH *", with.toString());
			
	}
	
	@Test
	public void nodeTest() {
		
		Node node = new Node("Node");

		With with = new With(node);
		
		assertEquals("WITH Node", with.toString());
		
		node.withVariable("n");
		assertEquals("WITH n", with.toString());

		with.returnables().clear();
		with.returnables().add(new Alias(node, "a_node"));
		assertEquals("WITH n AS a_node", with.toString());

	}

	@Test
	public void anonymousRelationshipTest() {
		
		Relationship relationship = new Relationship("Knows");

		With with = new With(relationship);
		
		/*
		 * Triggering creation of variable in an
		 * anonymous relationship before testing it
		 * otherwise we do not have generated variable name 
		 * when assembling expected string
		 */
		with.toString();
		
		assertEquals("WITH " + relationship.variable().get(), with.toString());
		
	}

	@Test
	public void relationshipTest() {
		
		Relationship relationship = new Relationship("Knows").withVariable("k");

		With with = new With(relationship);
		
		assertEquals("WITH k", with.toString());

		with.returnables().clear();
		with.addReturnables(new Alias(relationship, "relationship"));
		assertEquals("WITH k AS relationship", with.toString());

	}
	
	@Test
	public void propertyTest() {
	
		Property property1 = new Property("prop");
		Alias prop = new Alias(property1);
		Property property2 = new Property("prop");
		
		Node node = new Node("Node").withProperties(property1);
		Relationship relationship = new Relationship("Knows").withVariable("k").withProperties(property2);

		With with = new With(node, relationship);
		assertEquals("WITH Node, k", with.toString());

		with.returnables().clear();
		with.withReturnables(prop, property2);
		assertEquals("WITH Node.prop, k.prop", with.toString());
		
		prop.setAlias("alias");
		assertEquals("WITH Node.prop AS alias, k.prop", with.toString());
		
		node.withVariable("n");
		assertEquals("WITH n.prop AS alias, k.prop", with.toString());
		
	}
	
	@Test
	public void operationTest() {
		

		Property property1 = new Property("prop");
		Property property2 = new Property("prop");
		
		new Node("Node").withProperties(property1);
		new Relationship("Knows").withVariable("k").withProperties(property2);

		Xor xor = new Xor(property1, property2);
		
		With with = new With(xor);

		assertEquals("WITH Node.prop XOR k.prop", with.toString());

		with.returnables().clear();
		with.addReturnables(new Alias(xor.group(), "Exclusive"));
		assertEquals("WITH (Node.prop XOR k.prop) AS Exclusive", with.toString());
		
	}

	@Test
	public void functionTest() {

		Property property1 = new Property("prop");
		Property property2 = new Property("prop");
		
		new Node("Node").withProperties(property1);
		new Relationship("Knows").withVariable("k").withProperties(property2);

		With with = new With(new Multiplication(new Sign(property1), property2));

		assertEquals("WITH sign(Node.prop) * k.prop", with.toString());

	}

	@Test
	public void aggregateFunctionTest() {

		Property property1 = new Property("prop");
		
		new Node("Node").withProperties(property1);

		With with = new With(new Alias(new Sum(property1), "total"));

		assertEquals("WITH sum(Node.prop) AS total", with.toString());

	}

	@Test
	public void mixedReturnablesTest() {

		Property property1 = new Property("property1");
		Property property2 = new Property("property2");
		
		Node node = new Node("Node").withVariable("n"); 
		Relationship relationship = new Relationship("Knows").withVariable("k");

		Addition addition = new Addition(property1, property2);
		Group multiplication = new Multiplication(property1, property2).group();
		Avg avg = new Avg(property1);		

		With with = new With(relationship, property1, addition, new Timestamp());
		
		with
			.withReturnables(new Alias(node, "alias"))
			.withReturnables(new Alias(avg, "average"))
			.withReturnables(new Alias(multiplication, "m"));

		assertEquals("WITH k, property1, property1 + property2, timestamp(), n AS alias, avg(property1) AS average, (property1 * property2) AS m", with.toString());

	}

	@Test
	public void chainedStatementTest() {
		
		Property property = new Property("name");
		Node node = new Node().withVariable("n").withProperties(property);
		
		Return returns = new Return(property);
		With with = new With(returns, node);
		
		assertEquals("WITH n RETURN n.name", with.toString());
		
	}

	@Test
	public void chainedWithTest() {
		
		Property property = new Property("name");
		Node node = new Node().withVariable("n").withProperties(property);
		
		With with = new With(new With(node), node);
		
		assertEquals("WITH n WITH n", with.toString());
		
	}
	
	@Test
	public void orderByTest() {

		Property property = new Property("name");
		Node node = new Node().withVariable("n").withProperties(property);
		
		With with = new With(node).withOrderBy(property);

		assertEquals("WITH n ORDER BY n.name", with.toString());

	}
	
	@Test
	public void limitationsTest() {
		
		With with = new With().all();
		
		Value skip = new Value(1);
		Value limit = new Value(new Parameter("limit"));
		
		with.withSkip(skip);
		assertEquals("WITH * SKIP 1", with.toString());
		
		with.limitations().clearSkip();
		assertEquals("WITH *", with.toString());

		with.limitations().setLimit(limit);
		assertEquals("WITH * LIMIT {limit}", with.toString());
		
		with.limitations().clearLimit();
		assertEquals("WITH *", with.toString());

		with.withLimit(limit).withSkip(skip);
		assertEquals("WITH * SKIP 1 LIMIT {limit}", with.toString());
		
	}

	@Test
	public void distinctTest() {

		Property property = new Property("name");
		Node node = new Node().withVariable("n").withProperties(property);
		
		With with = new With(node).withOrderBy(property).withDistinct(true);

		assertEquals("WITH DISTINCT n ORDER BY n.name", with.toString());

	}
	
	@Test
	public void whereTest() {

		Property property = new Property("name");
		Node node = new Node().withVariable("n").withProperties(property);
		
		With with = new With(new Alias(node, "person")).withOrderBy(property).withDistinct(true).where(new NotEquals(property, new Value("anonymous")));

		assertEquals("WITH DISTINCT n AS person WHERE person.name <> 'anonymous' ORDER BY person.name", with.toString());

	}
	
}
