package com.paulodorow.decypher.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.paulodorow.decypher.Alias;
import com.paulodorow.decypher.List;
import com.paulodorow.decypher.Node;
import com.paulodorow.decypher.Parameter;
import com.paulodorow.decypher.Path;
import com.paulodorow.decypher.Property;
import com.paulodorow.decypher.Relationship;
import com.paulodorow.decypher.Return;
import com.paulodorow.decypher.Value;
import com.paulodorow.decypher.functions.Id;
import com.paulodorow.decypher.functions.Sum;
import com.paulodorow.decypher.functions.ToString;
import com.paulodorow.decypher.operations.And;
import com.paulodorow.decypher.operations.Concatenation;
import com.paulodorow.decypher.operations.Division;

public class ReturnTests {

	@Test
	public void labelOnlyTest() {
		
		Node node = new Node("Node");
		
		Return returns = new Return(node);
		
		assertEquals("RETURN Node", returns.toString());
		
		Node anotherNode = new Node("Another");
		returns.withReturnables(anotherNode);
		
		assertEquals("RETURN Node, Another", returns.toString());
		
	}
	
	@Test
	public void variableOnlyTest() {
		
		Node node = new Node().withVariable("n");
		
		Return returns = new Return(node);
		
		assertEquals("RETURN n", returns.toString());

		Relationship relationship = new Relationship().withVariable("a");
		returns.withReturnables(relationship);
		
		assertEquals("RETURN n, a", returns.toString());

	}
	
	@Test 
	public void relationshipTest() {
		
		Relationship relationship = new Relationship("KNOWS");
		
		Return returns = new Return(relationship);
		
		/*
		 * Triggering creation of variable in an
		 * anonymous relationship before testing it
		 * otherwise we do not have generated variable name 
		 * when assembling expected string
		 */
		returns.toString();
		
		assertEquals("RETURN ".concat(relationship.variable().toString()), returns.toString());
	
		relationship.variable().set("k");
		assertEquals("RETURN k", returns.toString());
		
	}

	@Test
	public void labelAndVariableTest() {
		
		Node node = new Node("Node", "n");
		
		Return returns = new Return(node);
		
		assertEquals("RETURN n", returns.toString());
	
		Node anotherNode = new Node().withVariable("a");
		returns.withReturnables(anotherNode);
		
		assertEquals("RETURN n, a", returns.toString());
		
	}
	
	@Test
	public void propertiesOnlyTest() {
		
		Property property1 = new Property("property1");
		
		Return returns = new Return(property1);
		
		assertEquals("RETURN property1", returns.toString());
		
		property1.setValue(new Value("Value"));
		assertEquals("RETURN property1", returns.toString());

		Property property2 = new Property("property2");
		returns.addReturnables(property2);
		assertEquals("RETURN property1, property2", returns.toString());
		
		returns.withOrderBy(property2);
		assertEquals("RETURN property1, property2 ORDER BY property2", returns.toString());

	}
	
	@Test
	public void propertiesWithParentNodeTest() {
		
		Node node = new Node("Node", "n");
		
		Property property1 = new Property("property1");
		node.withProperties(property1);
		
		Return returns = new Return(property1);
		
		assertEquals("RETURN n.property1", returns.toString());
		
		property1.setValue(new Value("Value"));
		assertEquals("RETURN n.property1", returns.toString());

		returns.returnables().remove(property1);
		returns.addReturnables(node);
		assertEquals("RETURN n", returns.toString());

		Property property2 = new Property("property2");
		node.withProperties(property2);
		returns.addReturnables(property2);
		assertEquals("RETURN n, n.property2", returns.toString());
		
		returns.withOrderBy(property1);
		assertEquals("RETURN n, n.property2 ORDER BY n.property1", returns.toString());
		
		node.properties().remove(property1);
		assertEquals("RETURN n, n.property2 ORDER BY property1", returns.toString());

	}
	
	
	@Test
	public void propertiesWithParentRelationshipTest() {

		Property property1 = new Property("property1");

		Relationship relationship = new Relationship("RELATIONSHIP").withVariable("r");
		relationship.withProperties(property1);
	
		Return returns = new Return(property1);

		assertEquals("RETURN r.property1", returns.toString());
		
		property1.setValue(new Value("Value"));
		assertEquals("RETURN r.property1", returns.toString());

		Property property2 = new Property("property2");
		relationship.withProperties(property2);
		returns.addReturnables(property2);
		assertEquals("RETURN r.property1, r.property2", returns.toString());
		
	}
	
	@Test
	public void propertiesWithNodeAndRelationshipTest() {

		Property property1 = new Property("property1");

		Relationship relationship = new Relationship().withVariable("r");
		relationship.withProperties(property1);
	
		Return returns = new Return(property1);

		assertEquals("RETURN r.property1", returns.toString());
		
		property1.setValue(new Value("Value"));
		assertEquals("RETURN r.property1", returns.toString());

		Property property2 = new Property("property2");
		relationship.withProperties(property2);
		returns.addReturnables(property2);
		assertEquals("RETURN r.property1, r.property2", returns.toString());

		Node node = new Node().withVariable("n");
		returns.withReturnables(node);
		assertEquals("RETURN r.property1, r.property2, n", returns.toString());
		
		relationship.properties().remove(property1);
		node.withProperties(property1);
		assertEquals("RETURN n.property1, r.property2, n", returns.toString());
		
	}
	
	@Test 
	public void allEntititesTest() {
		
		Return returns = new Return();
	
		returns.withReturnables(new Node());
		assertFalse(returns.isReturningAll());
		
		returns.all();
		assertTrue(returns.isReturningAll());
		
	}
	
	@Test
	public void limitationsTest() {
		
		Return returns = new Return().all();
		
		Value skip = new Value(1);
		Value limit = new Value(new Parameter("limit"));
		
		returns.withSkip(skip);
		assertEquals("RETURN * SKIP 1", returns.toString());
		
		returns.limitations().clearSkip();
		assertEquals("RETURN *", returns.toString());

		returns.limitations().setLimit(limit);
		assertEquals("RETURN * LIMIT {limit}", returns.toString());
		
		returns.limitations().clearLimit();
		assertEquals("RETURN *", returns.toString());

		returns.withLimit(limit).withSkip(skip);
		assertEquals("RETURN * SKIP 1 LIMIT {limit}", returns.toString());
		
	}

	@Test
	public void aliasTest() {
		
		Property property1 = new Property("property1");
		Property property2 = new Property("property2");
		Property property3 = new Property("property3");

		Node nodeN = new Node("Node1", "n").withProperties(property1);
		Node nodeM = new Node("Node2", "m").withProperties(property2);
		Relationship relationshipR = new Relationship("RELATIONSHIP").withVariable("r").withVariable("r").withProperties(property3);
		
		Alias node1 = new Alias(nodeN, "node1");
		Alias node2 = new Alias(nodeM, "node2");
		Alias relationship = new Alias(relationshipR, "relationship");
		
		Return returns = new Return()
				.withReturnables(node1)
				.withReturnables(node2)
				.withReturnables(relationship);
		
		assertEquals("RETURN n AS node1, m AS node2, r AS relationship", returns.toString());
		
		Alias p1 = new Alias(property1, "p1");
		Alias p2 = new Alias(property2, "p2");
		
		returns.withReturnables(p1).withReturnables(p2);
		assertEquals("RETURN n AS node1, m AS node2, r AS relationship, n.property1 AS p1, m.property2 AS p2", returns.toString());

		node1.clear();
		relationship.clear();
		p1.clear();
		assertEquals("RETURN n, m AS node2, r, n.property1, m.property2 AS p2", returns.toString());
		
		returns.withReturnables(new Alias(property3, "pr"));
		assertEquals("RETURN n, m AS node2, r, n.property1, m.property2 AS p2, r.property3 AS pr", returns.toString());
		
		returns.returnables().clear();
		returns.withReturnables(new Alias(new Sum(property1), "total"));
		assertEquals("RETURN sum(n.property1) AS total", returns.toString());

		returns.withReturnables(new Alias(new ToString(property2), "str"));
		assertEquals("RETURN sum(n.property1) AS total, toString(m.property2) AS str", returns.toString());

		returns.withReturnables(new Alias(new Division(property3, new Value(100)), "percent"));
		assertEquals("RETURN sum(n.property1) AS total, toString(m.property2) AS str, r.property3 / 100 AS percent", returns.toString());
		
		returns.returnables().clear();
		returns.withReturnables(new Alias(new And(property1, property2).group(), "group"));
		assertEquals("RETURN (n.property1 AND m.property2) AS group", returns.toString());

	}
	
	@Test
	public void orderByTest() {
		
		Property property1 = new Property("property1");
		Node node1 = new Node("Node1", "n").withProperties(property1);
		
		Property property2 = new Property("property2");
		new Node("Node2", "m").withProperties(property2);
		
		Relationship relationship = new Relationship("RELATIONSHIP").withVariable("r");
		
		Return returns = new Return().withReturnables(new Alias(node1, "node1"));
		returns.withOrderBy(property1);
		
		assertEquals("RETURN n AS node1 ORDER BY node1.property1", returns.toString());
		
		returns.orderBy().remove(property1);
		returns.orderBy().add(property2);
		assertEquals("RETURN n AS node1 ORDER BY m.property2", returns.toString());
	
		relationship.properties().add(property1);
		returns.orderBy().add(property1);
		assertEquals("RETURN n AS node1 ORDER BY m.property2, r.property1", returns.toString());
		
		returns.withLimit(new Value(10)).withSkip(new Value(5));
		assertEquals("RETURN n AS node1 ORDER BY m.property2, r.property1 SKIP 5 LIMIT 10", returns.toString());
		
		returns.orderBy().setDescending(property1);
		returns.orderBy().setDescending(property2);

		assertEquals("RETURN n AS node1 ORDER BY m.property2 DESCENDING, r.property1 DESCENDING SKIP 5 LIMIT 10", returns.toString());

		returns.orderBy().setAscending(property2);
		relationship.variable().clear();
		
		/*
		 * Triggering creation of variable in an
		 * anonymous relationship before testing it
		 * otherwise we do not have generated variable name 
		 * when assembling expected string
		 */
		returns.toString();
		
		assertEquals("RETURN n AS node1 ORDER BY m.property2, ".concat(relationship.variable().toString()).concat(".property1 DESCENDING SKIP 5 LIMIT 10"), returns.toString());

		returns.orderBy().clear();
		returns.limitations().clear();
		node1.withProperties(property1);
		returns.returnables().clear();
		returns.addReturnables(node1);
		Concatenation concatenation = new Concatenation(new ToString(property1), new Value(" "), property2);
		returns.withOrderBy(concatenation);
		assertEquals("RETURN n ORDER BY toString(n.property1) + ' ' + m.property2", returns.toString());
		
		returns.orderBy().clear();
		returns.orderBy().add(new Parameter("parm"), new Value(false));
		assertEquals("RETURN n ORDER BY {parm}, false", returns.toString());
		
		returns.withOrderBy(new Id(node1));
		assertEquals("RETURN n ORDER BY {parm}, false, id(n)", returns.toString());
		
	}
	
	@Test
	public void distinctTest() {
		
		Property property1 = new Property("property1");

		new Relationship().withVariable("r").withProperties(property1);
	
		Return returns = new Return(property1).withDistinct(true);

		assertEquals("RETURN DISTINCT r.property1", returns.toString());
		
		returns.setDistinct(false);
		assertEquals("RETURN r.property1", returns.toString());
		
	}
	
	@Test
	public void functionsAndOperationsTest() {

		Property property = new Property("property");
		new Node("Node").withProperties(property);
		
		Sum sum = new Sum(property);
		
		Return returns = new Return(sum);
		
		assertEquals("RETURN sum(Node.property)", returns.toString());

		Division division = new Division(property, new Value(100));
		
		returns.returnables().clear();
		returns.addReturnables(new Alias(division, "percentage"));

		assertEquals("RETURN Node.property / 100 AS percentage", returns.toString());
		
		returns.addReturnables(new Concatenation(new ToString(property), new Value("%")));
		assertEquals("RETURN Node.property / 100 AS percentage, toString(Node.property) + '%'", returns.toString());
		
	}
	
	@Test
	public void listTest() {
		
		List list = new List(new Value(1), new Parameter("parm"));
		
		Return returns = new Return().withReturnables(new Alias(list, "List"));
		
		assertEquals("RETURN [1, {parm}] AS List", returns.toString());
		
	}
	
	@Test
	public void pathTest() {
		
		Path path = new Path().between(new Node(), new Node()).withVariable("path");
		
		Return returns = new Return(path);
				
		assertEquals("RETURN path", returns.toString());
		
	}
	
}
