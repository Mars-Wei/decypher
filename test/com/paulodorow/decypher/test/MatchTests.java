package com.paulodorow.decypher.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.paulodorow.decypher.Alias;
import com.paulodorow.decypher.Match;
import com.paulodorow.decypher.Node;
import com.paulodorow.decypher.Path;
import com.paulodorow.decypher.Property;
import com.paulodorow.decypher.Return;
import com.paulodorow.decypher.ShortestPath;
import com.paulodorow.decypher.Value;
import com.paulodorow.decypher.operations.Equals;

public class MatchTests {

	@Test
	public void nodeMatchTest() {
		
		Match match = new Match(new Node().withVariable("n"));
		match.returns().all();
		
		assertEquals("MATCH (n) RETURN *", match.toString());
		
	}
	
	@Test 
	public void pathMatchTest() {
		
		Node nodeA = new Node().withVariable("a");
		Node nodeB = new Node("Person");
		
		Path path = new Path().between(nodeA, nodeB).unidirectional();
		
		Match match = new Match(path);
		assertEquals("MATCH (a)-->(:Person) RETURN *", match.toString());
		
		path = new ShortestPath().between(nodeA, nodeB).withVariable("p");
		match.clearPatterns();
		match.addPatterns(path);
		assertEquals("MATCH p=shortestPath((a)--(:Person)) RETURN *", match.toString());
		
	}
	
	@Test
	public void withTest() {

		Node nodeA = new Node().withVariable("a");
		Node nodeB = new Node("Person");
		
		Path path = new Path().between(nodeA, nodeB);
		
		Match match = new Match(path);
		match.with(nodeA);
		
		assertEquals("MATCH (a)--(:Person) WITH a", match.toString());

	}
	
	@Test
	public void whereTest() {

		Property name = new Property("name");
		Node node = new Node("Person").withVariable("p").withProperties(name);
		
		Match match = new Match();
		
		match.withPatterns(node).where(new Equals(name, new Value("a name"))).returns(node);
		
		assertEquals("MATCH (p:Person) WHERE p.name = 'a name' RETURN p", match.toString());
		
	}
	
	@Test
	public void withWhereTest() {

		Property name = new Property("name");
		Node node = new Node("Person").withVariable("p").withProperties(name);
		
		Match match = new Match();
		
		Alias friend = new Alias(node, "friend");
		
		match
			.withPatterns(node).where(new Equals(name, new Value("a name")))
			.with(new Return(friend), friend).withOrderBy(friend);

		assertEquals("MATCH (p:Person) WHERE p.name = 'a name' WITH p AS friend RETURN friend ORDER BY friend", match.toString());

	}
	
	@Test
	public void multipleMatchTest() {
		
		Node nodeA = new Node().withVariable("a");
		Node nodeB = new Node("Person");
		
		Path path1 = new Path().between(nodeA, nodeB).unidirectional();
		Path path2 = new Path().between(nodeB, nodeA).unidirectional();
		
		Match match = new Match().withPatterns(path1, path2);
		
		assertEquals("MATCH (a)-->(:Person), (:Person)-->(a) RETURN *", match.toString());
		
	}
	
	@Test
	public void matchWithMultipleConditionsTest() {
		
		Node nodeA = new Node().withVariable("a");
		Node nodeB = new Node("Person");
		
		Path path1 = new Path().between(nodeA, nodeB).unidirectional().withVariable("p");
		Path path2 = new Path().between(nodeB, nodeA).unidirectional().withVariable("q");
		
		Match match = new Match(path1, path2).returns(path1, path2);
		
		assertEquals("MATCH p=(a)-->(:Person), q=(:Person)-->(a) RETURN p, q", match.toString());
		
	}

	@Test
	public void matchWithTest() {
	
		Property property1 = new Property("name");
		Property property2 = new Property("name", new Value("charlie"));
		
		Node nodeA = new Node().withVariable("a").withProperties(property1);
		Node nodeB = new Node("Person").withProperties(property2).withVariable("b");
		
		Path path1 = new Path().between(nodeA, nodeB).unidirectional().withVariable("p");
		Path path2 = new Path().between(nodeB, nodeA).unidirectional().withVariable("q");
		
		Match match = new Match();
		match.withPatterns(path1).with(new Match(path2).where(new Equals(property1, property2)), path1).returns().all();
		
		assertEquals("MATCH p=(a)-->(b:Person {name: 'charlie'}) WITH p MATCH q=(b:Person {name: 'charlie'})-->(a) WHERE a.name = b.name RETURN *", match.toString());
		            //MATCH q=(b:Person {name: 'charlie'})-->(a) WHERE a.name = b.name RETURN *	
	}
	
	@Test
	public void switchBetweenWithAndReturnTest() {
		
		Property property1 = new Property("name");
		Property property2 = new Property("name", new Value("charlie"));
		
		Node nodeA = new Node().withVariable("a").withProperties(property1);
		Node nodeB = new Node("Person").withProperties(property2).withVariable("b");
		
		Path path1 = new Path().between(nodeA, nodeB).unidirectional().withVariable("p");
		//Path path2 = new Path().between(nodeB, nodeA).unidirectional().withVariable("q");
		
		Match match = new Match();
		match.withPatterns(path1).with(path1).setChainedStatement(new Return().all());
		
		assertEquals("MATCH p=(a)-->(b:Person {name: 'charlie'}) WITH p RETURN *", match.toString());
		
		match.returns().withReturnables(path1);
		assertEquals("MATCH p=(a)-->(b:Person {name: 'charlie'}) RETURN p", match.toString());

		match.with();
		assertEquals("MATCH p=(a)-->(b:Person {name: 'charlie'}) WITH p", match.toString());

	}
	
	@Test
	public void optionalMatchTest() {
		
		Property property1 = new Property("name");
		Property property2 = new Property("name", new Value("charlie"));
		
		Node nodeA = new Node().withVariable("a").withProperties(property1);
		Node nodeB = new Node("Person").withProperties(property2).withVariable("b");
		
		Path path1 = new Path().between(nodeA, nodeB).unidirectional().withVariable("p");
		Path path2 = new Path().between(nodeB, nodeA).unidirectional().withVariable("q");

		Match match = new Match(path1);
		
		assertFalse(match.isOptional());
		
		match.match().withPatterns(path2).setOptional(true);

		assertTrue(match.match().isOptional());

		assertEquals("MATCH p=(a)-->(b:Person {name: 'charlie'}) OPTIONAL MATCH q=(b:Person {name: 'charlie'})-->(a) RETURN *", match.toString());
		
		match.match().setWhere(new Equals(property1, property2));

		assertEquals("MATCH p=(a)-->(b:Person {name: 'charlie'}) OPTIONAL MATCH q=(b:Person {name: 'charlie'})-->(a) WHERE a.name = b.name RETURN *", match.toString());
		
	}
	
	/*TODO match any type of pattern.
	 * Not only predicates but support full blown patterns
	 * 
	 * MATCH (tobias { name: 'Tobias' }),(others)
	 * WHERE others.name IN ['Andres', 'Peter'] AND (tobias)<--(others)
	 * RETURN others
	 * 
	 * MATCH (persons),(peter { name: 'Peter' })
	 * WHERE NOT (persons)-->(peter)
	 * RETURN persons
	 * 
	 * MATCH (n)
	 * WHERE (n)-[:KNOWS]-({ name: 'Tobias' })
	 * RETURN n
	 * 
	 * MATCH (n)-[r]->()
	 * WHERE n.name='Andres' AND type(r)=~ 'K.*'
	 * RETURN r
	 */
	@Test
	public void wherePatternTest() {
		
		Property property1 = new Property("name");
		Property property2 = new Property("name", new Value("charlie"));
		
		Node nodeA = new Node().withVariable("a").withProperties(property1);
		Node nodeB = new Node("Person").withProperties(property2).withVariable("b");
		
		Path path1 = new Path().between(nodeA, nodeB).unidirectional().withVariable("p");

		Match match = new Match(nodeA).where(path1);
		match.with(path1).withChainedStatement(new Return().all());
		
		assertEquals("MATCH (a) WHERE (a)-->(b:Person {name: 'charlie'}) WITH p RETURN *", match.toString());

		
	}
	
}
