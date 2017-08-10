package com.paulodorow.decypher.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.paulodorow.decypher.Alias;
import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsReturnable;
import com.paulodorow.decypher.List;
import com.paulodorow.decypher.Match;
import com.paulodorow.decypher.Node;
import com.paulodorow.decypher.Parameter;
import com.paulodorow.decypher.Path;
import com.paulodorow.decypher.Property;
import com.paulodorow.decypher.Relationship;
import com.paulodorow.decypher.Return;
import com.paulodorow.decypher.Value;
import com.paulodorow.decypher.functions.Abs;
import com.paulodorow.decypher.functions.Acos;
import com.paulodorow.decypher.functions.Asin;
import com.paulodorow.decypher.functions.Atan;
import com.paulodorow.decypher.functions.Atan2;
import com.paulodorow.decypher.functions.Ceil;
import com.paulodorow.decypher.functions.Coalesce;
import com.paulodorow.decypher.functions.Cos;
import com.paulodorow.decypher.functions.Cot;
import com.paulodorow.decypher.functions.Degrees;
import com.paulodorow.decypher.functions.E;
import com.paulodorow.decypher.functions.Exists;
import com.paulodorow.decypher.functions.Exp;
import com.paulodorow.decypher.functions.Extract;
import com.paulodorow.decypher.functions.Floor;
import com.paulodorow.decypher.functions.Haversin;
import com.paulodorow.decypher.functions.Id;
import com.paulodorow.decypher.functions.Keys;
import com.paulodorow.decypher.functions.LTrim;
import com.paulodorow.decypher.functions.Labels;
import com.paulodorow.decypher.functions.Left;
import com.paulodorow.decypher.functions.Length;
import com.paulodorow.decypher.functions.Log;
import com.paulodorow.decypher.functions.Log10;
import com.paulodorow.decypher.functions.Lower;
import com.paulodorow.decypher.functions.Max;
import com.paulodorow.decypher.functions.Nodes;
import com.paulodorow.decypher.functions.Pi;
import com.paulodorow.decypher.functions.RTrim;
import com.paulodorow.decypher.functions.Radians;
import com.paulodorow.decypher.functions.Rand;
import com.paulodorow.decypher.functions.Relationships;
import com.paulodorow.decypher.functions.Replace;
import com.paulodorow.decypher.functions.Reverse;
import com.paulodorow.decypher.functions.Right;
import com.paulodorow.decypher.functions.Round;
import com.paulodorow.decypher.functions.Sign;
import com.paulodorow.decypher.functions.Sin;
import com.paulodorow.decypher.functions.Size;
import com.paulodorow.decypher.functions.Split;
import com.paulodorow.decypher.functions.Sqrt;
import com.paulodorow.decypher.functions.Substring;
import com.paulodorow.decypher.functions.Sum;
import com.paulodorow.decypher.functions.Tan;
import com.paulodorow.decypher.functions.Timestamp;
import com.paulodorow.decypher.functions.ToFloat;
import com.paulodorow.decypher.functions.ToInt;
import com.paulodorow.decypher.functions.ToString;
import com.paulodorow.decypher.functions.Trim;
import com.paulodorow.decypher.functions.Upper;
import com.paulodorow.decypher.operations.Addition;
import com.paulodorow.decypher.operations.And;
import com.paulodorow.decypher.operations.Concatenation;
import com.paulodorow.decypher.operations.Multiplication;

public class FunctionsTests {
	
	private static final HasContext context = new HasContext() {
		
		@Override
		public boolean isInContext(IsReturnable<?> returnable) {
			return true;
		}
		
		@Override
		public IsReturnable<?> getInContext(IsReturnable<?> returnable) {
			return new Alias(returnable, "alias");
		}
		
	};

	@Test
	public void existsTest() {
		
		Property property = new Property("property");
		
		Exists exists = new Exists(property);
		
		assertEquals("exists(property)", exists.toString());
		assertEquals("exists(alias)", exists.toReturnableString(context));
		
		Node node = new Node("node").withProperties(property);
		assertEquals("exists(node.property)", exists.toString());
		assertEquals("exists(alias)", exists.toReturnableString(context));
		
		node.withVariable("v");
		assertEquals("exists(v.property)", exists.toString());
		assertEquals("exists(alias)", exists.toReturnableString(context));
		
	}
	
	@Test
	public void coalesceTest() {
		
		Property property1 = new Property("property1");
		Node node = new Node("Node").withProperties(property1);

		Property property2 = new Property("property2");
		Relationship relationship = new Relationship("Relationship").withProperties(property2).withVariable("r");
		
		Coalesce coalesce = new Coalesce(property1, new Value("value"));
		
		assertEquals("coalesce(Node.property1, 'value')", coalesce.toString());
		assertEquals("coalesce(alias, alias)", coalesce.toReturnableString(context));

		coalesce.setDefaultValue(new Value(0));
		assertEquals("coalesce(Node.property1, 0)", coalesce.toString());
		assertEquals("coalesce(alias, alias)", coalesce.toReturnableString(context));

		coalesce.setDefaultValue(node);
		assertEquals("coalesce(Node.property1, Node)", coalesce.toString());
		assertEquals("coalesce(alias, alias)", coalesce.toReturnableString(context));

		coalesce.setValue(relationship);
		assertEquals("coalesce(r, Node)", coalesce.toString());
		assertEquals("coalesce(alias, alias)", coalesce.toReturnableString(context));

		relationship.variable().set("r");
		coalesce.setDefaultValue(new Sum(property2));
		assertEquals("coalesce(r, sum(r.property2))", coalesce.toString());
		assertEquals("coalesce(alias, alias)", coalesce.toReturnableString(context));

		Max max = new Max(coalesce);
		assertEquals("max(coalesce(r, sum(r.property2)))", max.toString());
		assertEquals("coalesce(alias, alias)", coalesce.toReturnableString(context));
		
		coalesce.setValue(new And(property1, property2));
		coalesce.setDefaultValue(new Value(false));
		assertEquals("coalesce(Node.property1 AND r.property2, false)", coalesce.toString());
		assertEquals("coalesce(alias, alias)", coalesce.toReturnableString(context));
	
		coalesce.setValue(new Coalesce(property1, property2));
		assertEquals("coalesce(coalesce(Node.property1, r.property2), false)", coalesce.toString());
		assertEquals("coalesce(alias, alias)", coalesce.toReturnableString(context));
		
	}
	
	@Test
	public void absTest() {
	
		Abs abs = new Abs();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		abs.withOperand(property);
		assertEquals("abs(p.visits)", abs.toString());
		assertEquals("abs(alias)", abs.toReturnableString(context));

		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		abs.setOperand(property2);
		assertEquals("abs(l.property)", abs.toString());
		assertEquals("abs(alias)", abs.toReturnableString(context));
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		abs.setOperand(concatenation);
	
		assertEquals("abs(l.property + '1' + p.visits)", abs.toString());
		assertEquals("abs(alias)", abs.toReturnableString(context));
	
	}

	@Test
	public void roundTest() {
	
		Round round = new Round();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		round.withOperand(property);
		assertEquals("round(p.visits)", round.toString());
		assertEquals("round(alias)", round.toReturnableString(context));

		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		round.setOperand(property2);
		assertEquals("round(l.property)", round.toString());
		assertEquals("round(alias)", round.toReturnableString(context));
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		round.setOperand(concatenation);
	
		assertEquals("round(l.property + '1' + p.visits)", round.toString());
	
	}

	@Test
	public void ceilTest() {
	
		Ceil ceil = new Ceil();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		ceil.withOperand(property);
		assertEquals("ceil(p.visits)", ceil.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		ceil.setOperand(property2);
		assertEquals("ceil(l.property)", ceil.toString());
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		ceil.setOperand(concatenation);
	
		assertEquals("ceil(l.property + '1' + p.visits)", ceil.toString());
	
	}

	@Test
	public void floorTest() {
	
		Floor floor = new Floor();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		floor.withOperand(property);
		assertEquals("floor(p.visits)", floor.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		floor.setOperand(property2);
		assertEquals("floor(l.property)", floor.toString());
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		floor.setOperand(concatenation);
	
		assertEquals("floor(l.property + '1' + p.visits)", floor.toString());
	
	}

	@Test
	public void sqrtTest() {
	
		Sqrt sqrt = new Sqrt();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		sqrt.withOperand(property);
		assertEquals("sqrt(p.visits)", sqrt.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		sqrt.setOperand(property2);
		assertEquals("sqrt(l.property)", sqrt.toString());
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		sqrt.setOperand(concatenation);
	
		assertEquals("sqrt(l.property + '1' + p.visits)", sqrt.toString());
	
	}

	@Test
	public void signTest() {
	
		Sign sign = new Sign();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		sign.withOperand(property);
		assertEquals("sign(p.visits)", sign.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		sign.setOperand(property2);
		assertEquals("sign(l.property)", sign.toString());
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		sign.setOperand(concatenation);
	
		assertEquals("sign(l.property + '1' + p.visits)", sign.toString());
	
	}

	@Test
	public void sinTest() {
	
		Sin sin = new Sin();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		sin.withOperand(property);
		assertEquals("sin(p.visits)", sin.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		sin.setOperand(property2);
		assertEquals("sin(l.property)", sin.toString());
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		sin.setOperand(concatenation);
	
		assertEquals("sin(l.property + '1' + p.visits)", sin.toString());
	
	}

	@Test
	public void cosTest() {
	
		Cos cos = new Cos();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		cos.withOperand(property);
		assertEquals("cos(p.visits)", cos.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		cos.setOperand(property2);
		assertEquals("cos(l.property)", cos.toString());
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		cos.setOperand(concatenation);
	
		assertEquals("cos(l.property + '1' + p.visits)", cos.toString());
	
	}

	@Test
	public void tanTest() {
	
		Tan tan = new Tan();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		tan.withOperand(property);
		assertEquals("tan(p.visits)", tan.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		tan.setOperand(property2);
		assertEquals("tan(l.property)", tan.toString());
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		tan.setOperand(concatenation);
	
		assertEquals("tan(l.property + '1' + p.visits)", tan.toString());
	
	}

	@Test
	public void cotTest() {
	
		Cot cot = new Cot();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		cot.withOperand(property);
		assertEquals("cot(p.visits)", cot.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		cot.setOperand(property2);
		assertEquals("cot(l.property)", cot.toString());
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		cot.setOperand(concatenation);
	
		assertEquals("cot(l.property + '1' + p.visits)", cot.toString());
	
	}

	@Test
	public void asinTest() {
	
		Asin asin = new Asin();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		asin.withOperand(property);
		assertEquals("asin(p.visits)", asin.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		asin.setOperand(property2);
		assertEquals("asin(l.property)", asin.toString());
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		asin.setOperand(concatenation);
	
		assertEquals("asin(l.property + '1' + p.visits)", asin.toString());
	
	}

	@Test
	public void acosTest() {
	
		Acos acos = new Acos();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		acos.withOperand(property);
		assertEquals("acos(p.visits)", acos.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		acos.setOperand(property2);
		assertEquals("acos(l.property)", acos.toString());
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		acos.setOperand(concatenation);
	
		assertEquals("acos(l.property + '1' + p.visits)", acos.toString());
	
	}

	@Test
	public void atanTest() {
	
		Atan atan = new Atan();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		atan.withOperand(property);
		assertEquals("atan(p.visits)", atan.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		atan.setOperand(property2);
		assertEquals("atan(l.property)", atan.toString());
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		atan.setOperand(concatenation);
	
		assertEquals("atan(l.property + '1' + p.visits)", atan.toString());
	
	}

	@Test
	public void atan2Test() {
	
		Atan2 atan2 = new Atan2();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		atan2.withOperand(property);
		assertEquals("atan2(p.visits)", atan2.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		atan2.setOperand(property2);
		assertEquals("atan2(l.property)", atan2.toString());
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		atan2.setOperand(concatenation);
	
		assertEquals("atan2(l.property + '1' + p.visits)", atan2.toString());
	
	}

	@Test
	public void haversinTest() {
	
		Haversin haversin = new Haversin();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		haversin.withOperand(property);
		assertEquals("haversin(p.visits)", haversin.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		haversin.setOperand(property2);
		assertEquals("haversin(l.property)", haversin.toString());
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		haversin.setOperand(concatenation);
	
		assertEquals("haversin(l.property + '1' + p.visits)", haversin.toString());
	
	}

	@Test
	public void degreesTest() {
	
		Degrees degrees = new Degrees();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		degrees.withOperand(property);
		assertEquals("degrees(p.visits)", degrees.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		degrees.setOperand(property2);
		assertEquals("degrees(l.property)", degrees.toString());
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		degrees.setOperand(concatenation);
	
		assertEquals("degrees(l.property + '1' + p.visits)", degrees.toString());
	
	}

	@Test
	public void radiansTest() {
	
		Radians radians = new Radians();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		radians.withOperand(property);
		assertEquals("radians(p.visits)", radians.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		radians.setOperand(property2);
		assertEquals("radians(l.property)", radians.toString());
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		radians.setOperand(concatenation);
	
		assertEquals("radians(l.property + '1' + p.visits)", radians.toString());
	
	}

	@Test
	public void logTest() {
	
		Log log = new Log();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		log.withOperand(property);
		assertEquals("log(p.visits)", log.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		log.setOperand(property2);
		assertEquals("log(l.property)", log.toString());
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		log.setOperand(concatenation);
	
		assertEquals("log(l.property + '1' + p.visits)", log.toString());
	
	}

	@Test
	public void log10Test() {
	
		Log10 log10 = new Log10();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		log10.withOperand(property);
		assertEquals("log10(p.visits)", log10.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		log10.setOperand(property2);
		assertEquals("log10(l.property)", log10.toString());
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		log10.setOperand(concatenation);
	
		assertEquals("log10(l.property + '1' + p.visits)", log10.toString());
	
	}
	
	@Test
	public void expTest() {
	
		Exp exp = new Exp();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		exp.withOperand(property);
		assertEquals("exp(p.visits)", exp.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		exp.setOperand(property2);
		assertEquals("exp(l.property)", exp.toString());
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		exp.setOperand(concatenation);
	
		assertEquals("exp(l.property + '1' + p.visits)", exp.toString());
	
	}
	
	@Test
	public void randTest() {
		
		Rand rand = new Rand();
		assertEquals("rand()", rand.toString());
		
		ToString toString = new ToString(rand);
		assertEquals("toString(rand())", toString.toString());
		
	}

	@Test
	public void piTest() {
		
		Pi rand = new Pi();
		assertEquals("pi()", rand.toString());
		
		ToString toString = new ToString(rand);
		assertEquals("toString(pi())", toString.toString());
		
	}

	@Test
	public void eTest() {
		
		E e = new E();
		assertEquals("e()", e.toString());
		
		ToString toString = new ToString(e);
		assertEquals("toString(e())", toString.toString());
		
	}

	@Test
	public void toIntTest() {
	
		ToInt toInt = new ToInt();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		toInt.withOperand(property);
		assertEquals("toInt(p.visits)", toInt.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		toInt.setOperand(property2);
		assertEquals("toInt(l.property)", toInt.toString());
	
		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		toInt.setOperand(concatenation);
	
		assertEquals("toInt(l.property + '1' + p.visits)", toInt.toString());
	
	}

	@Test
	public void toFloatTest() {
	
		ToFloat toFloat = new ToFloat();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		toFloat.withOperand(property);
		assertEquals("toFloat(p.visits)", toFloat.toString());
	
		Property property2 = new Property("property");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		toFloat.setOperand(property2);
		assertEquals("toFloat(l.property)", toFloat.toString());
	
		Max max = new Max(toFloat);
		assertEquals("max(toFloat(l.property))", max.toString());

		Concatenation concatenation = new Concatenation(property2, new Value("1"), property);
		toFloat.setOperand(concatenation);
	
		assertEquals("toFloat(l.property + '1' + p.visits)", toFloat.toString());
	
	}

	@Test
	public void toStringTest() {
	
		ToString toString = new ToString();
		
		Property property = new Property("visits");
		new Node("Person").withVariable("p").withProperties(property);
	
		toString.withOperand(property);
		assertEquals("toString(p.visits)", toString.toString());
	
		Property property2 = new Property("access");
		new Relationship("LIKES").withVariable("l").withProperties(property2);
		toString.setOperand(property2);
		assertEquals("toString(l.access)", toString.toString());
	
		Multiplication multiplication = new Multiplication(property2, new Value(2));
		toString.setOperand(multiplication);
	
		assertEquals("toString(l.access * 2)", toString.toString());
		
		Max max = new Max(new ToInt(toString));
		assertEquals("max(toInt(toString(l.access * 2)))", max.toString());
	
	}
	
	@Test
	public void timestampTest() {
		
		Timestamp timestamp = new Timestamp();
		assertEquals("timestamp()", timestamp.toString());
		
		ToString toString = new ToString(timestamp);
		assertEquals("toString(timestamp())", toString.toString());
		
	}

	@Test
	public void idTest() {
		
		Node node = new Node("Person");
		
		Id id = new Id(node);
		assertEquals("id(Person)", id.toString());
		
		assertEquals("id(Person)", id.toString());

		node.variable().set("v");
		assertEquals("id(v)", id.toString());

		Relationship relationship = new Relationship("LIKES").withVariable("r");
		id.setEntity(relationship);
		assertEquals("id(r)", id.toString());

	}
	
	@Test
	public void keysTest() {
		
		Node node = new Node("Person");
		
		Keys keys = new Keys(node);
		assertEquals("keys(Person)", keys.toString());
		
		assertEquals("keys(Person)", keys.toString());

		node.variable().set("v");
		assertEquals("keys(v)", keys.toString());

		Relationship relationship = new Relationship("LIKES").withVariable("r");
		keys.setEntity(relationship);
		assertEquals("keys(r)", keys.toString());
		
	}

	@Test
	public void nodesTest() {
		
		Path path = new Path().withVariable("p");
		
		Nodes nodes = new Nodes(path);
		
		assertEquals("nodes(p)", nodes.toString());
		
	}
	
	@Test
	public void relationshipsTest() {
		
		Path path = new Path().withVariable("p");
		
		Relationships relationships = new Relationships(path);
		
		assertEquals("relationships(p)", relationships.toString());
		
	}

	@Test
	public void lengthTest() {
		
		Path path = new Path().withVariable("p");
		
		Length length = new Length(path);
		assertEquals("length(p)", length.toString());
		
		Node node = new Node("Node").withVariable("e");
		
		Property property = new Property("property");
		node.withProperties(property);
		
		length.setOperand(property);
		assertEquals("length(e.property)", length.toString());

		Parameter parameter = new Parameter("parm");
		length.setOperand(parameter);
		assertEquals("length({parm})", length.toString());
		
		Value value = new Value("value");
		length.setOperand(value);
		assertEquals("length('value')", length.toString());
		
		Concatenation concatenation = new Concatenation(value, property);
		length.setOperand(concatenation);
		assertEquals("length('value' + e.property)", length.toString());
		
	}
	
	@Test
	public void labelsTest() {
		
		Node node = new Node("Node").withVariable("n");
		
		Labels labels = new Labels(node);
		
		assertEquals("labels(n)", labels.toString());
		
	}

	@Test
	public void extractTest() {
		
		Node node = new Node("Node").withVariable("e");
		
		Extract extract = new Extract().withEntity(node);
		
		Path path = new Path("p");
		
		Property property = new Property("property");
		node.withProperties(property);
		
		Nodes nodes = new Nodes(path);
		
		extract.setEntityCollection(nodes);
		extract.setReturns(property);
		
		assertEquals("extract(e IN nodes(p) | e.property)", extract.toString());
		
		extract.setEntityCollection(new Relationships(path));
		assertEquals("extract(e IN relationships(p) | e.property)", extract.toString());
		
		extract.setReturns(new ToString(property));
		assertEquals("extract(e IN relationships(p) | toString(e.property))", extract.toString());

		extract.setReturns(new Addition(new ToInt(property), new Value(15)));
		assertEquals("extract(e IN relationships(p) | toInt(e.property) + 15)", extract.toString());
		
	}
	
	@Test
	public void replaceTest() {

		Node node = new Node("Node").withVariable("e");
		
		Property property = new Property("property");
		node.withProperties(property);

		Parameter parameter = new Parameter("parm");
		
		Value value = new Value("replacement");
		
		Replace replace = new Replace(property, parameter, value);
		
		assertEquals("replace(e.property, {parm}, 'replacement')", replace.toString());

		Concatenation concatenation = new Concatenation(value, property);
		replace.setOriginal(concatenation);
		assertEquals("replace('replacement' + e.property, {parm}, 'replacement')", replace.toString());
		
	}
	
	@Test
	public void substringTest() {

		Node node = new Node("Node").withVariable("e");
		
		Property property = new Property("property");
		node.withProperties(property);

		Parameter parameter = new Parameter("parm");
		
		Value value = new Value(10);
		
		Substring substring = new Substring(property, parameter, value);
		
		assertEquals("substring(e.property, {parm}, 10)", substring.toString());
	
		Concatenation concatenation = new Concatenation(new Value("value"), property);
		substring.setOriginal(concatenation);
		assertEquals("substring('value' + e.property, {parm}, 10)", substring.toString());
		
	}

	@Test
	public void leftTest() {

		Node node = new Node("Node").withVariable("e");
		
		Property property = new Property("property");
		node.withProperties(property);

		Value value = new Value(10);
		
		Left left = new Left(property, value);
		
		assertEquals("left(e.property, 10)", left.toString());

		Concatenation concatenation = new Concatenation(new Value("value"), property);
		left.setOriginal(concatenation);
		assertEquals("left('value' + e.property, 10)", left.toString());

	}

	@Test
	public void rightTest() {

		Node node = new Node("Node").withVariable("e");
		
		Property property = new Property("property");
		node.withProperties(property);

		Value value = new Value(12);
		
		Right right = new Right(property, value);
		
		assertEquals("right(e.property, 12)", right.toString());

		Concatenation concatenation = new Concatenation(new Value("value"), property);
		right.setOriginal(concatenation);
		assertEquals("right('value' + e.property, 12)", right.toString());

	}

	@Test
	public void trimTest() {
		
		Node node = new Node("Node").withVariable("e");
		
		Property property = new Property("property");
		node.withProperties(property);
		
		Trim trim = new Trim(property);
		assertEquals("trim(e.property)", trim.toString());

		Parameter parameter = new Parameter("parm");
		trim.setOperand(parameter);
		assertEquals("trim({parm})", trim.toString());
		
		Value value = new Value(" value ");
		trim.setOperand(value);
		assertEquals("trim(' value ')", trim.toString());

		Concatenation concatenation = new Concatenation(value, property);
		trim.setOperand(concatenation);
		assertEquals("trim(' value ' + e.property)", trim.toString());
		
	}
	
	@Test
	public void lTrimTest() {
		
		Node node = new Node("Node").withVariable("e");
		
		Property property = new Property("property");
		node.withProperties(property);
		
		LTrim ltrim = new LTrim(property);
		assertEquals("ltrim(e.property)", ltrim.toString());

		Parameter parameter = new Parameter("parm");
		ltrim.setOperand(parameter);
		assertEquals("ltrim({parm})", ltrim.toString());
		
		Value value = new Value(" value");
		ltrim.setOperand(value);
		assertEquals("ltrim(' value')", ltrim.toString());

		Concatenation concatenation = new Concatenation(value, property);
		ltrim.setOperand(concatenation);
		assertEquals("ltrim(' value' + e.property)", ltrim.toString());

	}
	
	@Test
	public void rTrimTest() {
		
		Node node = new Node("Node").withVariable("e");
		
		Property property = new Property("property");
		node.withProperties(property);
		
		RTrim rtrim = new RTrim(property);
		assertEquals("rtrim(e.property)", rtrim.toString());

		Parameter parameter = new Parameter("parm");
		rtrim.setOperand(parameter);
		assertEquals("rtrim({parm})", rtrim.toString());
		
		Value value = new Value("value ");
		rtrim.setOperand(value);
		assertEquals("rtrim('value ')", rtrim.toString());

		Concatenation concatenation = new Concatenation(value, property);
		rtrim.setOperand(concatenation);
		assertEquals("rtrim('value ' + e.property)", rtrim.toString());
		
	}
	
	@Test
	public void upperTest() {
		
		Node node = new Node("Node").withVariable("e");
		
		Property property = new Property("property");
		node.withProperties(property);
		
		Upper upper = new Upper(property);
		assertEquals("upper(e.property)", upper.toString());

		Parameter parameter = new Parameter("parm");
		upper.setOperand(parameter);
		assertEquals("upper({parm})", upper.toString());
		
		Value value = new Value("value");
		upper.setOperand(value);
		assertEquals("upper('value')", upper.toString());

		Concatenation concatenation = new Concatenation(value, property);
		upper.setOperand(concatenation);
		assertEquals("upper('value' + e.property)", upper.toString());

	}
	
	@Test
	public void lowerTest() {
		
		Node node = new Node("Node").withVariable("e");
		
		Property property = new Property("property");
		node.withProperties(property);
		
		Lower lower = new Lower(property);
		assertEquals("lower(e.property)", lower.toString());

		Parameter parameter = new Parameter("parm");
		lower.setOperand(parameter);
		assertEquals("lower({parm})", lower.toString());
		
		Value value = new Value("value");
		lower.setOperand(value);
		assertEquals("lower('value')", lower.toString());

		Concatenation concatenation = new Concatenation(value, property);
		lower.setOperand(concatenation);
		assertEquals("lower('value' + e.property)", lower.toString());

	}

	@Test
	public void reverseTest() {
		
		Node node = new Node("Node").withVariable("e");
		
		Property property = new Property("property");
		node.withProperties(property);
		
		Reverse reverse = new Reverse(property);
		assertEquals("reverse(e.property)", reverse.toString());

		Parameter parameter = new Parameter("parm");
		reverse.setOperand(parameter);
		assertEquals("reverse({parm})", reverse.toString());
		
		Value value = new Value("value ");
		reverse.setOperand(value);
		assertEquals("reverse('value ')", reverse.toString());

		Concatenation concatenation = new Concatenation(value, property);
		reverse.setOperand(concatenation);
		assertEquals("reverse('value ' + e.property)", reverse.toString());

	}

	@Test
	public void splitTest() {

		Node node = new Node("Node").withVariable("e");
		
		Property property = new Property("property");
		node.withProperties(property);

		Value value = new Value("|");
		
		Split split = new Split(property, value);
		
		assertEquals("split(e.property, '|')", split.toString());

		Concatenation concatenation = new Concatenation(value, property);
		split.setOriginal(concatenation);
		assertEquals("split('|' + e.property, '|')", split.toString());

	}
	
	@Test
	public void sizeNodeTest() {
		
		Node node = new Node().withVariable("n");
		Path path = new Path().from(node);
		
		Match match = new Match(node).returns(new Size(path));
		
		assertEquals("MATCH (n) RETURN size((n)-->())", match.toString());
		
	}
	
	//TODO missing functions range() creates a list of numbers (step is optional), other functions returning lists are: labels(), nodes(), relationships(), filter(), extract().

	@Test
	public void sizeListTest() {
		
		List list = new List();

		Return returns = new Return(new Size(list)); 
		
		assertEquals("RETURN size([])", returns.toString());
		
	}

}
