package com.paulodorow.decypher.test;

import static com.paulodorow.decypher.Path.Direction.UNIDIRECTIONAL;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.paulodorow.decypher.AllShortestPaths;
import com.paulodorow.decypher.NoContext;
import com.paulodorow.decypher.Node;
import com.paulodorow.decypher.Path;
import com.paulodorow.decypher.Relationship;
import com.paulodorow.decypher.ShortestPath;

//@TODO Path needs to allow direction: (tom:Person {name:"Tom Hanks"})-[:ACTED_IN]->(m)<-[:ACTED_IN]-(coActors)

public class PathTests {

	@Test
	public void pathDirectionTest() {
		
		Node node1 = new Node().withVariable("n");
		Node node2 = new Node().withVariable("m");
		
		Path path = new Path();
		assertEquals("()-->()", path.toString());
				
		path.between(node1, node2);
		
		assertEquals("(n)--(m)", path.toString());

		path.unidirectional();
		assertEquals("(n)-->(m)", path.toString());

		path.bidirectional();
		assertEquals("(n)--(m)", path.toString());

		path.from(node1).to(node2);
		assertEquals("(n)-->(m)", path.toString());

	}
	
	@Test
	public void pathWithRelationshipTest() {
		
		Node node1 = new Node().withVariable("n");
		Node node2 = new Node().withVariable("m");
		
		Path path = new Path();
		
		path.between(node1, node2);

		Relationship relationship = new Relationship();
		
		path.withRelationship(relationship);
		assertEquals("(n)--(m)", path.toString());
	
		relationship.withVariable("r");
		assertEquals("(n)-[r]-(m)", path.toString());

		path.setDirection(UNIDIRECTIONAL);
		assertEquals("(n)-[r]->(m)", path.toString());
		
	}

	@Test
	public void multiplePathsTest() {
		
		Path path1 = new Path();
		Path path2 = new Path().to(path1);
		
		assertEquals("()-->()-->()", path2.toString());

		path2.clearTo();

		Relationship relationship = new Relationship().withVariable("r");
		
		Path path3 = new Path().between(path1, path2).withRelationship(relationship).bidirectional();

		assertEquals("()-->()-[r]-()-->()", path3.toString());

		Node node1 = new Node().withVariable("n");
		Node node2 = new Node().withVariable("m");

		path1.setFrom(node1);
		path2.setTo(node2);

		assertEquals("(n)-->()-[r]-()-->(m)", path3.toString());
		
		path3.setTo(node2);
		assertEquals("(n)-->()-[r]->(m)", path3.toString());
		
		path3.clearRelationship();
		assertEquals("(n)-->()-->(m)", path3.toString());

	}
	
	@Test
	public void pathVariableTest() {
		
		Path path1 = new Path();
		Path path2 = new Path().to(path1).withVariable("p");

		assertEquals("p=()-->()-->()", path2.toString());
		
		assertEquals("p", path2.toReturnableString(NoContext.get()));
		
	}

	@Test
	public void shortestPathTest() {
		
		Node node1 = new Node().withVariable("n");
		Node node2 = new Node().withVariable("m");
		
		Path path = new ShortestPath().between(node1, node2).unidirectional();
		assertEquals("shortestPath((n)-->(m))", path.toString());
		
		path.variable().set("p");
		assertEquals("p=shortestPath((n)-->(m))", path.toString());
		
		path = new AllShortestPaths().between(node1, node2);
		assertEquals("allShortestPaths((n)--(m))", path.toString());
			
		path.variable().set("p");
		assertEquals("p=allShortestPaths((n)--(m))", path.toString());		
		
	}
	
}
