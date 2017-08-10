package com.paulodorow.decypher.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.paulodorow.decypher.List;
import com.paulodorow.decypher.Node;
import com.paulodorow.decypher.Parameter;
import com.paulodorow.decypher.Path;
import com.paulodorow.decypher.Property;
import com.paulodorow.decypher.Relationship;
import com.paulodorow.decypher.Value;
import com.paulodorow.decypher.functions.Coalesce;
import com.paulodorow.decypher.functions.Count;
import com.paulodorow.decypher.functions.Max;
import com.paulodorow.decypher.functions.Timestamp;
import com.paulodorow.decypher.operations.Addition;
import com.paulodorow.decypher.operations.Concatenation;
import com.paulodorow.decypher.operations.Multiplication;

public class ListTests {
	
	@Test
	public void emptyListTest() {

		List list = new List();
		assertEquals("[]", list.toString());

	}

	@Test
	public void nodeTest() {

		List list = new List();
	
		Node node = new Node("Node");
		
		list.addElements(node);
		assertEquals("[Node]", list.toString());
		
		Node anotherNode = new Node("Another");
		list.withElements(node).withElements(anotherNode);
		assertEquals("[Node, Node, Another]", list.toString());
		
		node.withVariable("n");
		assertEquals("[n, n, Another]", list.toString());
		
		assertEquals("[n, n, Another]", list.toString());
		
		node.withProperties(new Property("property")).withLabels("SecondLabel");
		assertEquals("[n, n, Another]", list.toString());
		
	}
	
	@Test
	public void relationshipTest() {

		List list = new List();
	
		Relationship relationship = new Relationship("Relationship").withVariable("r");
		
		list.addElements(relationship);
		assertEquals("[r]", list.toString());
		
		Relationship anotherRelationship = new Relationship("Another").withVariable("a");
		list.withElements(relationship).withElements(anotherRelationship);
		assertEquals("[r, r, a]", list.toString());
		
		assertEquals("[r, r, a]", list.toString());
		
		relationship.withProperties(new Property("property")).withLabels("SecondLabel");
		assertEquals("[r, r, a]", list.toString());
		
	}
	
	@Test
	public void pathTest() {

		List list = new List();
		Path path = new Path().withVariable("p").between(new Node("Node1"), new Node("Node2"));
		
		list.addElements(path);
		assertEquals("[p]", list.toString());

		list.addElements(path);
		assertEquals("[p, p]", list.toString());

	}

	@Test
	public void valueParameterTest() {

		List list = new List();
		
		list.addElements(new Value(1));
		assertEquals("[1]", list.toString());

		list.addElements(new Value("name"), new Parameter("parm"));
		assertEquals("[1, 'name', {parm}]", list.toString());

	}

	@Test
	public void propertyTest() {
		
		Property property1 = new Property("property1");
		Property property2 = new Property("property2");
		Node node = new Node("Node").withProperties(property1, property2);
		
		List list = new List();

		list.addElements(property1, property2);
		assertEquals("[Node.property1, Node.property2]", list.toString());

		node.withVariable("n");
		assertEquals("[n.property1, n.property2]", list.toString());

	}

	@Test
	public void functionTest() {
		
		Property property = new Property("property");
		Node node = new Node("Node").withProperties(property).withVariable("n");
		
		List list = new List();

		list.addElements(property, new Coalesce(property, new Value("")) );
		assertEquals("[n.property, coalesce(n.property, '')]", list.toString());

		list.addElements(new Count(node));
		assertEquals("[n.property, coalesce(n.property, ''), count(n)]", list.toString());

	}

	@Test
	public void concatenationTest() {
		
		List list1 = new List(new Value(1), new Value(2));
		List list2 = new List(new Value(2), new Value(3));
		
		Concatenation concatenation = new Concatenation(list1, list2);
		
		assertEquals("[1, 2] + [2, 3]", concatenation.toString());
		
		concatenation.concatenate(new Parameter("parm"));
		assertEquals("[1, 2] + [2, 3] + {parm}", concatenation.toString());
	
		concatenation.concatenate(new Timestamp());
		assertEquals("[1, 2] + [2, 3] + {parm} + timestamp()", concatenation.toString());
		
	}
	
	@Test
	public void operationAsListItemTest() {
		
		Property property = new Property("property");
		new Node("Node").withProperties(property).withVariable("n");
		
		List list = new List();

		list.addElements(property, new Addition(property, new Value(10)) );
		assertEquals("[n.property, n.property + 10]", list.toString());

		list.addElements(new Timestamp());
		assertEquals("[n.property, n.property + 10, timestamp()]", list.toString());

	}

	@Test
	public void mixedTypesTest() {
		
		Property property = new Property("property");
		Node node = new Node("Node").withProperties(property).withVariable("n");
		Path path = new Path().withVariable("p").between(node, new Node("Node2"));
		
		List list = new List();

		list.addElements(node, 
				new Multiplication(new Coalesce(property, new Value(0)), new Value(10)).group(),
				new Max(property),
				property,
				new Parameter("parameter"),
				new Relationship("Relationship").withVariable("r"),
				path,
				new Value("name"));
		
		assertEquals("[n, (coalesce(n.property, 0) * 10), max(n.property), n.property, {parameter}, r, p, 'name']", list.toString());

	}

}
