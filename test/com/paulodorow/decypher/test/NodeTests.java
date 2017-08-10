package com.paulodorow.decypher.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.paulodorow.decypher.Node;
import com.paulodorow.decypher.Property;
import com.paulodorow.decypher.Value;

public class NodeTests {

	@Test
	public void emptyNodeTest() {
		
		Node node = new Node();
		
		assertEquals("()", node.toString());
		
	}

	@Test
	public void nodeLabelOnlyTest() {
		
		Node node = new Node("Label");
		
		assertEquals("(:Label)", node.toString());
		
	}

	@Test
	public void nodeVariableOnlyTest() {
		
		Node node = new Node().withVariable("n");
		
		assertEquals("(n)", node.toString());
		
	}

	@Test
	public void nodeLabelWithVariableTest() {
		
		Node node = new Node("Label", "variable");
		
		assertEquals("(variable:Label)", node.toString());
		assertEquals(1, node.labels().count());
		
	}

	@Test
	public void nodeWithOnePropertyTest() {

		Property property = new Property("name", new Value(100));
		
		Node node = new Node("Label", "variable").withProperties(property);
		
		assertEquals("(variable:Label {name: 100})", node.toString());
		
	}

	@Test
	public void nodeWithMultiplePropertiesTest() {

		Property property1 = new Property("name1", new Value("value1"));
		Property property2 = new Property("name2", new Value("value2"));
		
		Node node = new Node("Label").withProperties(property1, property2);
		
		assertEquals("(:Label {name1: 'value1', name2: 'value2'})", node.toString());
		
	}
	
	@Test
	public void nodeWithMultipleLabelsTest() {
		
		Node node = new Node().withLabels("Label1", "Label2", "Label3");
		
		assertEquals("(:Label1:Label2:Label3)", node.toString());
		assertEquals(3, node.labels().count());

		node.withLabels("Label1");
		assertEquals("(:Label1:Label2:Label3)", node.toString());
		
		node.labels().remove("Label2");
		node.withVariable("variable");
		assertEquals("(variable:Label1:Label3)", node.toString());
		
		Property property = new Property("name", new Value(100));
		node.withProperties(property);
		assertEquals("(variable:Label1:Label3 {name: 100})", node.toString());
		
	}

	@Test
	public void propertiesWithExpressionValuesTest() {
	
		Property property = new Property("name");
		new Node().withVariable("n").withProperties(property);

		Property name = new Property("name").value(new Value(property));
		Node node = new Node("Node").withProperties(name);
		
		assertEquals("(:Node {name: n.name})", node.toString());
		
	}
	
}
