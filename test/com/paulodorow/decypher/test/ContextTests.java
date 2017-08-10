package com.paulodorow.decypher.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.paulodorow.decypher.Alias;
import com.paulodorow.decypher.List;
import com.paulodorow.decypher.Match;
import com.paulodorow.decypher.Node;
import com.paulodorow.decypher.Path;
import com.paulodorow.decypher.Property;
import com.paulodorow.decypher.Relationship;
import com.paulodorow.decypher.Return;
import com.paulodorow.decypher.Value;
import com.paulodorow.decypher.With;
import com.paulodorow.decypher.functions.Avg;
import com.paulodorow.decypher.functions.Max;
import com.paulodorow.decypher.functions.Min;
import com.paulodorow.decypher.functions.Nodes;
import com.paulodorow.decypher.functions.Timestamp;
import com.paulodorow.decypher.functions.ToString;
import com.paulodorow.decypher.operations.Addition;
import com.paulodorow.decypher.operations.And;
import com.paulodorow.decypher.operations.Equals;
import com.paulodorow.decypher.operations.GreaterThan;
import com.paulodorow.decypher.operations.Subtraction;

public class ContextTests {
	
	//TODO All Context tests need to be moved eventually to specific test units (node goes to NodeTests) -- or not

	@Test
	public void propertyContextTest() {
		
		Property property = new Property("property").value(new Value("a"));
		Alias alias = new Alias(property, "alias");
		Node node = new Node("Node", "n").withProperties(property);

		Match match = new Match(node);
		match.with(alias).where(new Equals(new Value("a"), property)).withChainedStatement(new Return(alias).withOrderBy(property));

		assertEquals("MATCH (n:Node {property: 'a'}) WITH n.property AS alias WHERE 'a' = alias RETURN alias ORDER BY alias", match.toString()); 
		
	}
	
	@Test
	public void nodeContextTest() {
		
		Property property = new Property("property").value(new Value("a"));
		Node node = new Node("Node", "n").withProperties(property);
		Alias alias = new Alias(node, "alias");

		Match match = new Match(node);
		match.with(alias).withChainedStatement(new Return(alias));
		
		assertEquals("MATCH (n:Node {property: 'a'}) WITH n AS alias RETURN alias", match.toString()); 
		
	}

	@Test
	public void nodePropertyContextTest() {
		
		Property property = new Property("property").value(new Value("a"));
		Node node = new Node("Node", "n").withProperties(property);
		Alias alias = new Alias(node, "alias");
		
		Match match = new Match(node);
		match.with(alias).withChainedStatement(new Return(property));

		assertEquals("MATCH (n:Node {property: 'a'}) WITH n AS alias RETURN alias.property", match.toString()); 
		
	}

	@Test
	public void nodePropertyOrderByContextTest() {
		
		Property property = new Property("property").value(new Value("a"));
		Alias propertyAlias = new Alias(property, "prop");
		Node node = new Node("Node", "n").withProperties(property);
		Alias nodeAlias = new Alias(node, "alias");
		
		Match match = new Match(node);
		match.with(nodeAlias).withChainedStatement(new Return(propertyAlias).withOrderBy(property)).withOrderBy(property);

		assertEquals("MATCH (n:Node {property: 'a'}) WITH n AS alias ORDER BY alias.property RETURN alias.property AS prop ORDER BY prop", match.toString()); 
		
	}

	@Test
	public void nodePropertyWhereContextTest() {
		
		Value value = new Value("a");
		Property property = new Property("property").value(value);
		Node node = new Node("Node", "n").withProperties(property);
		Alias alias = new Alias(node, "alias");
		
		Match match = new Match(node).where(new Equals(property, value));
		match.with(alias).where(new Equals(property, value)).withChainedStatement(new Return(property)).withOrderBy(property);

		assertEquals("MATCH (n:Node {property: 'a'}) WHERE n.property = 'a' WITH n AS alias WHERE alias.property = 'a' ORDER BY alias.property RETURN alias.property", match.toString()); 
		
	}

	@Test
	public void relationshipPropertyContextTest() {
		
		Property property = new Property("property").value(new Value("a"));
		Relationship relationship = new Relationship("REL").withProperties(property).withVariable("r");
		Alias alias = new Alias(relationship, "alias");
		Path path = new Path().withRelationship(relationship).between(new Node("A"), new Node("B"));
		
		Match match = new Match(path);
		match.with(alias).withChainedStatement(new Return(property));

		assertEquals("MATCH (:A)-[r:REL {property: 'a'}]-(:B) WITH r AS alias RETURN alias.property", match.toString()); 
		
	}
	
	@Test
	public void operandTest() {
		
		Node node1 = new Node().withVariable("n");
		Property age = new Property("age").withParentEntity(node1);

		Node node2 = new Node().withVariable("m");
		Property age2 = new Property("age").withParentEntity(node2);
		
		Avg average = new Avg(age);
		Alias alias = Alias.create(average, "average");
		Match match = new Match(node1);
		match.with(alias).match(node2, new GreaterThan(age2, average)).returns(age2, average);
		
		assertEquals("MATCH (n) WITH avg(n.age) AS average MATCH (m) WHERE m.age > average RETURN m.age, average", match.toString()); 
		
	}
	
	@Test
	public void operationTest() {

		Node node1 = new Node().withVariable("n");
		Property age = new Property("age").withParentEntity(node1);

		GreaterThan greaterThan = new GreaterThan(age, new Value(18));
		
		Match match = new Match(node1);
		match.with(node1, greaterThan.asAlias("isAdult")).withChainedStatement(new Return(node1, greaterThan));
		
		assertEquals("MATCH (n) WITH n, n.age > 18 AS isAdult RETURN n, isAdult", match.toString()); 
		
	}
	
	@Test
	public void operandOperationComplexTest() {
		
		Property age = new Property("age");
		Node node = new Node().withVariable("n").withProperties(age);
		Match match = new Match(node);
		
		Min min = new Min(age);
		Max max = new Max(age);
		GreaterThan comparison = new GreaterThan(age, new Subtraction(max, min).group());
		
		match.with(min.asAlias("min"), max.asAlias("max")).withChainedStatement(
			new Match(node).where(comparison).returns(age, comparison.asAlias("inRange"))
		);
		
		assertEquals("MATCH (n) WITH min(n.age) AS min, max(n.age) AS max MATCH (n) WHERE n.age > (max - min) RETURN n.age, n.age > (max - min) AS inRange", match.toString());
		
	}
	
	@Test
	public void pathTest() {

		Property property = new Property("property");
		
		Node node1 = new Node().withVariable("n").withProperties(property);
		Node node2 = new Node().withVariable("m");
		
		Path path = new Path().between(node1, node2).withVariable("p");
		Alias alias = new Alias(path, "path");
		
		Match match = new Match(path);
		match.with(alias).withChainedStatement(new Return(new Nodes(path)));
		
		assertEquals("MATCH p=(n)--(m) WITH p AS path RETURN nodes(path)", match.toString());
		
		Property property2 = new Property("property2").value(new Value(property));
		Node node3 = new Node().withVariable("k").withProperties(property2);
		Match match2 = new Match(node3).where(new Equals(property, property2)); 
		
		match.with().returnables().clear();
		match.with().withChainedStatement(match2).addReturnables(node1.asAlias("nodeA"));

		assertEquals("MATCH p=(n)--(m) WITH n AS nodeA MATCH (k {property2: nodeA.property}) WHERE nodeA.property = k.property2 RETURN *", match.toString());

		property2.setValue(new Value(new ToString(property)));
		assertEquals("MATCH p=(n)--(m) WITH n AS nodeA MATCH (k {property2: toString(nodeA.property)}) WHERE nodeA.property = k.property2 RETURN *", match.toString());
		
	}
	
	@Test
	public void relationshipTest() {

		Node node1 = new Node().withVariable("n");
		Node node2 = new Node().withVariable("m");
		
		Property property = new Property("property");
		
		Relationship relationship = new Relationship("REL").withProperties(property).withVariable("r");
		
		Path path = new Path().between(node1, node2).withVariable("p").withRelationship(relationship);

		Match match = new Match(path);
		match.with(property.asAlias("alias")).withChainedStatement(new Return(new ToString(property).asAlias("another_alias")));
		
		assertEquals("MATCH p=(n)-[r:REL]-(m) WITH r.property AS alias RETURN toString(alias) AS another_alias", match.toString());
		
	}
	
	@Test
	public void doubleAliasTest() {
		
		Node node = new Node().withVariable("n");
		
		Match match = new Match(node);
		
		With with  = new With(node.asAlias("n3")).withChainedStatement(new Return(node));
		
		match.with(node.asAlias("n2")).withChainedStatement(with);
	
		assertEquals("MATCH (n) WITH n AS n2 WITH n2 AS n3 RETURN n3", match.toString());
		
	}
	
	@Test
	public void listTest() {
		
		Property property = new Property("property");
		Node node = new Node("Node").withProperties(property).withVariable("n");
		
		List list = new List();

		Addition addition = new Addition(property, new Value(10));
		
		Timestamp timestamp = new Timestamp();

		list.addElements(property, addition, timestamp);

		Match match = new Match(node);
		match.with(property.asAlias("propertyAlias"), addition.asAlias("addition"), timestamp.asAlias("timestamp")).withChainedStatement(new Return(list));

		assertEquals("MATCH (n:Node) WITH n.property AS propertyAlias, n.property + 10 AS addition, timestamp() AS timestamp RETURN [propertyAlias, addition, timestamp]", match.toString());
		
	}

	@Test
	public void matchNodesWithCreateRelationshipTest() {

		Property nameA = new Property("name");
		Node personA = new Node("Person", "a").withProperties(nameA);
		
		Property nameB = new Property("name");
		Node personB = new Node("Person", "b").withProperties(nameB);
		
		Equals nameAEquals = new Equals(nameA, new Value("Name A"));
		Equals nameBEquals = new Equals(nameB, new Value("Name B"));
		
		Relationship relationship = new Relationship("RELTYPE").withVariable("r");
		Path aToB = new Path().from(personA).to(personB).withRelationship(relationship);
		
		Match match = new Match(personA, personB).where(new And(nameAEquals, nameBEquals));
		match.with(personA.asAlias("personA"), personB.asAlias("personB")).create(aToB).returns(relationship);
		
		assertEquals("MATCH (a:Person), (b:Person) WHERE a.name = 'Name A' AND b.name = 'Name B' WITH a AS personA, b AS personB CREATE (personA)-[r:RELTYPE]->(personB) RETURN r", match.toString());
		
	}

}
