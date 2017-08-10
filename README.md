
# Decypher

## Forewarning

THIS IS REALLY EARLY ALPHA STAGE SOFTWARE. Breaking changes will be made and any code relying on Decypher may have to be rewritten.

## What is it?

Decypher provides lightweight wrapper classes to express any Cypher statement in an object-oriented manner. Decypher classes do not directly manipulate any nodes or relationships but rather can be seen as a Cypher statement builder.

Instead of hardcoding your Cypher query:

	MATCH (p:Person {firstName: 'John'}) RETURN p ORDER BY p.age

you work with high-level Java classes:

	Person p = new Person("John")
	Match m = new Match(p).return(p).orderBy(p.age);

Decypher by example:

	Java representation						Cypher equivalent
	
	new Node('Person')						(Person)
	
	new Relationship('KNOWS')				[:KNOWS]
	
	new Path(new Relationship('KNOWS'))		(Person)-[:WORKS_AS]-(Job)
		.between(
			new Node('Person'), 
			new Node('Job')
		)


## Motivation

In a local database access scenario -- where your code resides on the same computer as your Node4J database -- you can use Node4J's provided SDK for direct access to the database. More importantly, the SDK facilitates access to data using Java classes and neatly furnishes your database entities with handy and powerful methods.

In a remote database access scenario -- where your code resides in a different computer than your Node4J database -- you lose the capability of using Node4J's SDK and its great functionalities and capabilities. Instead, you're left with sending Cypher string queries over JDBC. No doubt, Cypher is a powerful language and should meet anyone's needs for access to a Node4J database, yet having string constant queries all over your code can make it harder to understand and maintain.

Many beginner Node4J developers that may initially misunderstand the purpose of the SDK, feel let down when they find out they cannot use the SDK and its capabilities on a database over the network.

The motivation behind Decypher is to bridge some of that gap in functionality between  using the SDK and using the Cypher language to manipulate the database, namely to allow a developer to express any Cypher statement in an object-oriented way.

Decypher does not replace any of the SDK's functionality but when you cannot use the SDK, it will allow for more robust, flexible, expressive, maintainable and testable code by allowing a developer to assemble Cypher statements using Java classes.

If you have OOCD (Objected-Oriented Compulsive Disorder) Decypher will provide relief.

## Advantages

Reuse of common patterns,code

Maintainability and easier refactoring

Extensibility

Testability

Expressiveness and code clarity

Domain-driven design


## How to install

[Section pending creation]

## I'm in a hurry, show me the code

This section is a quick start if you want to get your hands dirty right away. If you want to explore Decypher in more detail please read subsequent sections -- highly advisable if you don't want to miss out on powerful functionality.


### Code completion and toString() are your friends

You can discover a lot about Decypher functionality by using code completion and inspecting methods using the following classes as starting points:

- Node
- Relationship
- Path
- Create
- Merge
- Match

Decypher makes extensive use of chainable methods:

	new Match(person).where(ageMoreThan10).orderBy(name).return(p).skip(10).limit(3);

Use `toString()` on almost any Decypher object to obtain it's respective Cypher string:

`new Node("Person").withProperty("name", "John").toString()` yields `(Person {name: 'John'})`

### Subclass the Node class to create specialized nodes

public class Person extends Node {

	public static final String LABEL = "Person";

	public Property name;
	public Property age;

	public Person() {
	   super(LABEL);
	   name = new Property();
	   age = new Property();
	   addProperties(name, age);
	}
	
	public Person(String name) {
	   this();
	   name.setValue(name);			   
	}

}

### Subclass class Relationship to declare relationships

public class KNOWS extends Relationship {

	public static final String LABEL = "KNOWS";

	public DateProperty since;

	public KNOWS() {
	   super(LABEL);
	}

}

### Subclass class Path to express paths among nodes

public class PersonKnowsPerson extends Path {

	KNOWS relationship;

	public PersonKnowsPerson(Person person1, Person person2) {
	    relationship = new KNOWS();
	    setRelationship(relationship).between(person1, person2);
	}

	public PersonKnowsPerson since(Date date) {
	   relationship.since.set(date);
	   return this;
	}

}

### Use your classes to create nodes and relationships

	Person mary = new Person("Mary");
	Person john = new Person("John");
	PersonKnowsPerson maryKnowsJonh = new PersonKnowsPerson(mary, john).since(new Date());
	
	stmt.executeQuery(new Merge(maryKnowsJonh).toString());

### Use your classes to query nodes and relationships

	Person debbie = new Person("Debbie").withVariable("debbie");
	Person anyone = new Person().withVariable("anyone");
	PersonKnowsPerson peopleWhoKnowDebbie = new PersonKnowsPerson(anyone, debbie);
	
	Match debbiesYoungestFriend = new Match(peopleWhoKnowDebbie).withReturn(anyone).withOrderBy(anyone.age).withLimit(1);
	
	ResultSet rs = stmt.executeQuery(debbiesYoungestFriend.toString());

### Or instead of subclassing, use raw Decypher classes

[Section pending creation]

### Use Parameters

[Section pending creation]

### take it further === sublclass merge, add functionality to nodes, etc, 

[Section pending creation]

furnish your classes new Person(debbie).knows(john)

