package com.paulodorow.decypher.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.paulodorow.decypher.Node;
import com.paulodorow.decypher.Parameter;
import com.paulodorow.decypher.Property;
import com.paulodorow.decypher.Relationship;
import com.paulodorow.decypher.Value;
import com.paulodorow.decypher.functions.Sum;
import com.paulodorow.decypher.operations.Addition;
import com.paulodorow.decypher.operations.Division;
import com.paulodorow.decypher.operations.Modulo;
import com.paulodorow.decypher.operations.Multiplication;
import com.paulodorow.decypher.operations.Power;
import com.paulodorow.decypher.operations.Subtraction;

public class MathOperationsTests {

	@Test
	public void additionTest() {
		
		Addition addition = new Addition(new Value(1));
		assertEquals("1", addition.toString());
		
		addition.add(new Value(2));
		assertEquals("1 + 2", addition.toString());
		
		Value parameterValue = new Value(new Parameter("number"));
		addition.add(parameterValue);
		assertEquals("1 + 2 + {number}", addition.toString());
		
		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);
		addition.add(property1); 
		assertEquals("1 + 2 + {number} + n.property1", addition.toString());
		
		Property property2 = new Property("property2");
		new Relationship("RELATIONSHIP").withProperties(property2).withVariable("r");
		addition.add(property2);
		assertEquals("1 + 2 + {number} + n.property1 + r.property2", addition.toString());

		addition = new Addition(new Value(10), new Value(-10)).add(parameterValue);
		assertEquals("10 + -10 + {number}", addition.toString());

	}
	
	@Test
	public void subtractionTest() {
		
		Subtraction subtraction = new Subtraction(new Value(100));
		assertEquals("100", subtraction.toString());
		
		subtraction.subtract(new Value(1));
		assertEquals("100 - 1", subtraction.toString());
		
		Value parameterValue = new Value(new Parameter("number"));
		subtraction.subtract(parameterValue);
		assertEquals("100 - 1 - {number}", subtraction.toString());
		
		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);
		subtraction.subtract(property1);
		assertEquals("100 - 1 - {number} - n.property1", subtraction.toString());
		
		Property property2 = new Property("property2");
		new Relationship("RELATIONSHIP").withProperties(property2).withVariable("r");
		subtraction.subtract(property2);
		assertEquals("100 - 1 - {number} - n.property1 - r.property2", subtraction.toString());
	
		subtraction = new Subtraction(new Value(10), new Value(-10)).subtract(parameterValue);
		assertEquals("10 - -10 - {number}", subtraction.toString());
		
	}

	@Test
	public void multiplicationTest() {
		
		Multiplication multiplication = new Multiplication(new Value(10), new Value(10));
		assertEquals("10 * 10", multiplication.toString());
		
		multiplication.multiply(new Value(1));
		assertEquals("10 * 10 * 1", multiplication.toString());
		
		Value parameterValue = new Value(new Parameter("number"));
		multiplication.multiply(parameterValue);
		assertEquals("10 * 10 * 1 * {number}", multiplication.toString());
		
		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);
		multiplication.multiply(property1);
		assertEquals("10 * 10 * 1 * {number} * n.property1", multiplication.toString());
		
		Property property2 = new Property("property2");
		new Relationship("RELATIONSHIP").withProperties(property2).withVariable("r");
		multiplication.multiply(property2);
		assertEquals("10 * 10 * 1 * {number} * n.property1 * r.property2", multiplication.toString());
	
		multiplication = new Multiplication(new Value(10), new Value(-10)).multiply(parameterValue);
		assertEquals("10 * -10 * {number}", multiplication.toString());
		
	}

	@Test
	public void divisionTest() {
		
		Division division = new Division(new Value(10), new Value(10));
		assertEquals("10 / 10", division.toString());
		
		division.divideBy(new Value(1));
		assertEquals("10 / 10 / 1", division.toString());
		
		Value parameterValue = new Value(new Parameter("number"));
		division.divideBy(parameterValue);
		assertEquals("10 / 10 / 1 / {number}", division.toString());
		
		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);
		division.divideBy(property1);
		assertEquals("10 / 10 / 1 / {number} / n.property1", division.toString());
		
		Property property2 = new Property("property2");
		new Relationship("RELATIONSHIP").withProperties(property2).withVariable("r");
		division.divideBy(property2);
		assertEquals("10 / 10 / 1 / {number} / n.property1 / r.property2", division.toString());
	
		division = new Division(new Value(10), new Value(-10)).divideBy(parameterValue);
		assertEquals("10 / -10 / {number}", division.toString());
		
	}

	@Test
	public void moduloTest() {
		
		Modulo modulo = new Modulo(new Value(10), new Value(10));
		assertEquals("10 % 10", modulo.toString());
		
		modulo.mod(new Value(1));
		assertEquals("10 % 10 % 1", modulo.toString());
		
		Value parameterValue = new Value(new Parameter("number"));
		modulo.mod(parameterValue);
		assertEquals("10 % 10 % 1 % {number}", modulo.toString());
		
		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);
		modulo.mod(property1);
		assertEquals("10 % 10 % 1 % {number} % n.property1", modulo.toString());
		
		Property property2 = new Property("property2");
		new Relationship("RELATIONSHIP").withProperties(property2).withVariable("r");
		modulo.mod(property2);
		assertEquals("10 % 10 % 1 % {number} % n.property1 % r.property2", modulo.toString());
	
		modulo = new Modulo(new Value(10), new Value(-10)).mod(parameterValue);
		assertEquals("10 % -10 % {number}", modulo.toString());
		
	}

	@Test
	public void powerTest() {
		
		Power power = new Power(new Value(10), new Value(10));
		assertEquals("10 ^ 10", power.toString());
		
		power.exponentiate(new Value(1));
		assertEquals("10 ^ 10 ^ 1", power.toString());
		
		Value parameterValue = new Value(new Parameter("number"));
		power.exponentiate(parameterValue);
		assertEquals("10 ^ 10 ^ 1 ^ {number}", power.toString());
		
		Property property1 = new Property("property1");
		new Node("node", "n").withProperties(property1);
		power.exponentiate(property1);
		assertEquals("10 ^ 10 ^ 1 ^ {number} ^ n.property1", power.toString());
		
		Property property2 = new Property("property2");
		new Relationship("RELATIONSHIP").withProperties(property2).withVariable("r");
		power.exponentiate(property2);
		assertEquals("10 ^ 10 ^ 1 ^ {number} ^ n.property1 ^ r.property2", power.toString());
	
		power = new Power(new Value(10), new Value(-10)).exponentiate(parameterValue);
		assertEquals("10 ^ -10 ^ {number}", power.toString());
		
	}
	
	@Test
	public void mathOperationsTests() {

		Sum sum = new Sum(new Parameter("count"));
		
		Power power = new Power(new Value(1), new Value(2));
		Subtraction subtraction = new Subtraction();
		Multiplication multiplication = new Multiplication(new Value(4));
		Modulo modulo = new Modulo(new Parameter("parm"), new Value(6));
		Division division = new Division(new Property("property"));
		
		Addition addition = new Addition(
				subtraction.subtract(power.group(), multiplication.multiply(modulo.group()).group()),
				division.divideBy(sum).group()
		);
		
		assertEquals("(1 ^ 2) - (4 * ({parm} % 6)) + (property / sum({count}))", addition.toString());

	}

}
