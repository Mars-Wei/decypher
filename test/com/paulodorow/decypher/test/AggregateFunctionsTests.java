package com.paulodorow.decypher.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.paulodorow.decypher.Node;
import com.paulodorow.decypher.Parameter;
import com.paulodorow.decypher.Property;
import com.paulodorow.decypher.Relationship;
import com.paulodorow.decypher.Value;
import com.paulodorow.decypher.functions.Avg;
import com.paulodorow.decypher.functions.Collect;
import com.paulodorow.decypher.functions.Count;
import com.paulodorow.decypher.functions.Max;
import com.paulodorow.decypher.functions.Min;
import com.paulodorow.decypher.functions.PercentileCont;
import com.paulodorow.decypher.functions.PercentileDisc;
import com.paulodorow.decypher.functions.StDev;
import com.paulodorow.decypher.functions.StDevP;
import com.paulodorow.decypher.functions.Sum;
import com.paulodorow.decypher.operations.Addition;
import com.paulodorow.decypher.operations.Division;

public class AggregateFunctionsTests {

	@Test
	public void countTest() {
		
		Count count = new Count();
		assertEquals("count(*)", count.toString());
		
		Property property = new Property("active");
		Property property2 = new Property("age");
		Node node = new Node("Person").withVariable("p").withProperties(property, property2);
		
		count.withExpression(node);
		assertEquals("count(p)", count.toString());
	
		count.withExpression(property);
		assertEquals("count(p.active)", count.toString());
	
		Relationship relationship = new Relationship("LIKES");
		count.setExpression(relationship);
		relationship.enforceVariableName();
		assertEquals(String.format("count(%s)", relationship.variable().get()), count.toString());
		
		node.properties().remove(property2);
		relationship.properties().add(property2);
		count.setExpression(property2);
		assertEquals(String.format("count(%s.age)", relationship.variable().get()), count.toString());
	
		count.setDistinct(true);
		assertEquals(String.format("count(DISTINCT %s.age)", relationship.variable().get()), count.toString());
		
		count.withDistinct(false).countAll();
		assertEquals("count(*)", count.toString());
		
	}

	@Test
	public void avgTest() {
	
		Avg avg = new Avg();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		avg.withOperand(property);
		assertEquals("avg(p.visits)", avg.toString());
	
		Property property2 = new Property("age");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		avg.setOperand(property2);
		assertEquals("avg(l.age)", avg.toString());
	
		Addition addition = new Addition(property2, new Value(1));
		avg.setOperand(addition);
	
		assertEquals("avg(l.age + 1)", avg.toString());
	
		avg.setDistinct(true);
		assertEquals(String.format("avg(DISTINCT l.age + 1)"), avg.toString());
	
	}

	@Test
	public void sumTest() {
	
		Sum sum = new Sum();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		sum.withOperand(property);
		assertEquals("sum(p.visits)", sum.toString());
	
		Property property2 = new Property("age");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		sum.setOperand(property2);
		assertEquals("sum(l.age)", sum.toString());
	
		Addition addition = new Addition(property2, new Value(1));
		sum.setOperand(addition);
	
		assertEquals("sum(l.age + 1)", sum.toString());
	
		sum.setDistinct(true);
		assertEquals(String.format("sum(DISTINCT l.age + 1)"), sum.toString());
	
	}

	@Test
	public void minTest() {
		
		Min min = new Min();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		min.withOperand(property);
		assertEquals("min(p.visits)", min.toString());
	
		Property property2 = new Property("age");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		min.setOperand(property2);
		assertEquals("min(l.age)", min.toString());
	
		Addition addition = new Addition(property2, new Value(1));
		min.setOperand(addition);
	
		assertEquals("min(l.age + 1)", min.toString());
	
		min.setDistinct(true);
		assertEquals(String.format("min(DISTINCT l.age + 1)"), min.toString());
	
	}

	@Test
	public void maxTest() {
	
		Max max = new Max();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		max.withOperand(property);
		assertEquals("max(p.visits)", max.toString());
	
		Property property2 = new Property("age");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		max.setOperand(property2);
		assertEquals("max(l.age)", max.toString());
	
		Addition addition = new Addition(property2, new Value(1));
		max.setOperand(addition);
	
		assertEquals("max(l.age + 1)", max.toString());
	
		max.setDistinct(true);
		assertEquals(String.format("max(DISTINCT l.age + 1)"), max.toString());
		
	}

	@Test
	public void collectTest() {
		
		Collect collect = new Collect();
		
		Property property = new Property("active");
		Property property2 = new Property("age");
		Node node = new Node("Person").withVariable("p").withProperties(property, property2);
		
		collect.withExpression(node);
		assertEquals("collect(p)", collect.toString());
	
		collect.withExpression(property);
		assertEquals("collect(p.active)", collect.toString());
	
		Relationship relationship = new Relationship("LIKES").withVariable("l");
		collect.setExpression(relationship);
		assertEquals("collect(l)", collect.toString());
		
		node.properties().remove(property2);
		relationship.properties().add(property2);
		collect.setExpression(property2);
		assertEquals("collect(l.age)", collect.toString());
	
		collect.setDistinct(true);
		assertEquals("collect(DISTINCT l.age)", collect.toString());

	}

	@Test
	public void percentileDiscTest() {
	
		PercentileDisc percentile = new PercentileDisc();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
		
		percentile.withValue(property).withPercentile(new Value(.5));
		assertEquals("percentileDisc(p.visits, 0.5)", percentile.toString());
	
		percentile.setValue(new Addition(property, new Value(10)));
		assertEquals("percentileDisc(p.visits + 10, 0.5)", percentile.toString());
	
		percentile.withValue(new Parameter("parm")).withPercentile(new Division(new Value(100), property));
	
		assertEquals("percentileDisc({parm}, 100 / p.visits)", percentile.toString());
	
		percentile.setDistinct(true);
		assertEquals("percentileDisc(DISTINCT {parm}, 100 / p.visits)", percentile.toString());

	}

	@Test
	public void percentileContTest() {
	
		PercentileCont percentile = new PercentileCont();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
		
		percentile.withValue(property).withPercentile(new Value(.5));
		assertEquals("percentileCont(p.visits, 0.5)", percentile.toString());
	
		percentile.setValue(new Addition(property, new Value(10)));
		assertEquals("percentileCont(p.visits + 10, 0.5)", percentile.toString());
	
		percentile.withValue(new Parameter("parm")).withPercentile(new Division(new Value(100), property));
	
		assertEquals("percentileCont({parm}, 100 / p.visits)", percentile.toString());
	
		percentile.setDistinct(true);
		assertEquals("percentileCont(DISTINCT {parm}, 100 / p.visits)", percentile.toString());

	}

	@Test
	public void stDevTest() {
	
		StDev stDev = new StDev();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		stDev.withOperand(property);
		assertEquals("stdev(p.visits)", stDev.toString());
	
		Property property2 = new Property("age");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		stDev.setOperand(property2);
		assertEquals("stdev(l.age)", stDev.toString());
	
		Addition addition = new Addition(property2, new Value(1));
		stDev.setOperand(addition);
	
		assertEquals("stdev(l.age + 1)", stDev.toString());
	
		stDev.setDistinct(true);
		assertEquals("stdev(DISTINCT l.age + 1)", stDev.toString());

	}

	@Test
	public void stDevPTest() {
	
		StDevP stDevP = new StDevP();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		stDevP.withOperand(property);
		assertEquals("stdevp(p.visits)", stDevP.toString());
	
		Property property2 = new Property("age");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		stDevP.setOperand(property2);
		assertEquals("stdevp(l.age)", stDevP.toString());
	
		Addition addition = new Addition(property2, new Value(1));
		stDevP.setOperand(addition);
	
		assertEquals("stdevp(l.age + 1)", stDevP.toString());
	
		stDevP.setDistinct(true);
		assertEquals("stdevp(DISTINCT l.age + 1)", stDevP.toString());

	}

	
}
