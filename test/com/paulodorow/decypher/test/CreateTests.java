package com.paulodorow.decypher.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.paulodorow.decypher.Create;
import com.paulodorow.decypher.Match;
import com.paulodorow.decypher.Node;
import com.paulodorow.decypher.Path;
import com.paulodorow.decypher.Property;
import com.paulodorow.decypher.Relationship;
import com.paulodorow.decypher.Value;
import com.paulodorow.decypher.operations.And;
import com.paulodorow.decypher.operations.Equals;

public class CreateTests {

	/*
	 * 
	 * MATCH (a:Person),(b:Person)
WHERE a.name = 'Node A' AND b.name = 'Node B'
CREATE (a)-[r:RELTYPE]->(b)
RETURN r

MATCH (a:Person),(b:Person)
WHERE a.name = 'Node A' AND b.name = 'Node B'
CREATE (a)-[r:RELTYPE { name: a.name + '<->' + b.name }]->(b)
RETURN r

CREATE p =(andres { name:'Andres' })-[:WORKS_AT]->(neo)<-[:WORKS_AT]-(michael { name: 'Michael' })
RETURN p

CREATE (n:Person $props)
RETURN n
	 * 
	 */
	
	//@TODO include create in contexttests
	
	@Test
	public void createNodeTest() {
		
		Create create = new Create(new Node().withVariable("n"));
		assertEquals("CREATE (n)", create.toString());
		
	}

	@Test
	public void createMultipleNodesTest() {
		
		Create create = new Create(new Node().withVariable("n"), new Node().withVariable("m"));
		assertEquals("CREATE (n), (m)", create.toString());
		
	}

	@Test
	public void createNodeWithLabelTest() {
		
		Create create = new Create(new Node("Person").withVariable("n"));
		assertEquals("CREATE (n:Person)", create.toString());
		
	}

	@Test
	public void createNodeWithLabelAndPropertiesTest() {
		
		Create create = new Create(new Node("Person").withVariable("n").withProperties(new Property("name",new Value("Peter"))));
		assertEquals("CREATE (n:Person {name: 'Peter'})", create.toString());

	}

	@Test
	public void createUniqueNodeWithLabelAndPropertiesTest() {
		
		Create create = new Create(new Node("Person").withVariable("n").withProperties(new Property("name",new Value("Peter")))).withUnique(true);
		assertEquals("CREATE UNIQUE (n:Person {name: 'Peter'})", create.toString());
		
	}

	@Test
	public void createAndReturnTest() {

		Node node = new Node("Person").withVariable("n");
		Create create = new Create(node).returns(node.asAlias("alias"));
		assertEquals("CREATE (n:Person) RETURN n AS alias", create.toString());

	}

	@Test
	public void createFullPath() {

		Property nameA = new Property("name", new Value("Name A"));
		Node personA = new Node("Person", "a").withProperties(nameA);
		
		Property nameB = new Property("name", new Value("Name B"));
		Node personB = new Node("Person", "b").withProperties(nameB);
		
		Relationship relationship = new Relationship("RELTYPE").withVariable("r");
		Path aToB = new Path().from(personA).to(personB).withRelationship(relationship);

		Create create = new Create(aToB).returns(relationship);
		
		assertEquals("CREATE (a:Person {name: 'Name A'})-[r:RELTYPE]->(b:Person {name: 'Name B'}) RETURN r", create.toString());

	}

	@Test
	public void matchNodesAndCreateRelationshipTest() {

		Property nameA = new Property("name");
		Node personA = new Node("Person", "a").withProperties(nameA);
		Node a = new Node().withVariable("a");
		
		Property nameB = new Property("name");
		Node personB = new Node("Person", "b").withProperties(nameB);
		Node b = new Node().withVariable("b");
		
		Equals nameAEquals = new Equals(nameA, new Value("Name A"));
		Equals nameBEquals = new Equals(nameB, new Value("Name B"));
		
		Relationship relationship = new Relationship("RELTYPE").withVariable("r");
		Path aToB = new Path().from(a).to(b).withRelationship(relationship);
		
		Match match = new Match(personA, personB).where(new And(nameAEquals, nameBEquals));

		match.create().withPatterns(aToB).returns(relationship);
		
		assertEquals("MATCH (a:Person), (b:Person) WHERE a.name = 'Name A' AND b.name = 'Name B' CREATE (a)-[r:RELTYPE]->(b) RETURN r", match.toString());
		
	}
	
	@Test 
	public void createCreateTest() {
		
		Create create = new Create(new Node().withVariable("n"));
		create.create(new Node().withVariable("m")).returns().all();
		
		assertEquals("CREATE (n) CREATE (m) RETURN *", create.toString());
		
	}
	
}
