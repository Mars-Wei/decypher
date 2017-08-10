package com.paulodorow.decypher.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.paulodorow.decypher.Create;
import com.paulodorow.decypher.Match;
import com.paulodorow.decypher.Node;
import com.paulodorow.decypher.Path;
import com.paulodorow.decypher.Properties;
import com.paulodorow.decypher.Property;
import com.paulodorow.decypher.Relationship;
import com.paulodorow.decypher.Value;

public class PersonTest {

	public static class Person extends Node {

		private static final String LABEL = "Person";
		private Property name;

		private Person() {
			super(LABEL);
			name = new Property("name");
			this.properties().add(name);
		}
		
		public Person(String name) {
			this();
			setName(name);
		}
		
		public String getName() {
			return (String) this.name.getValue().get();
		}
		
		public void setName(String name) {
			this.name.setValue(new Value(name));
		}
		
		public Person named(String name) {
			setName(name);
			return this;
		}
		
		public Match match() {
			return new Match(this);
		}

		public Create create() {
			return new Create(this);
		}
		
		PersonOwnsVehicle ownsVehicle(Vehicle vehicle) {
			return new PersonOwnsVehicle(this).vehicle(vehicle);
		}
		
	}
	
	public static class Vehicle extends Node {
		
		private static final String LABEL = "Vehicle";
		private Property make;
		private Property model;
		private Property year;
		
		private Vehicle(String make, String model, Integer year) {
			super(LABEL);
			this.make = new Property("make");
			this.model = new Property("model");
			this.year = new Property("year");
			setMake(make);
			setModel(model);
			setYear(year);
		}

		public String getMake() {
			return (String) this.make.getValue().get();
		}
		
		public void setMake(String make) {
			this.make.setValue(new Value(make));
		}
		
		public Vehicle make(String make) {
			setMake(make);
			return this;
		}
		
		public String getModel() {
			return (String) this.model.getValue().get();
		}
		
		public void setModel(String model) {
			this.model.setValue(new Value(model));
		}
		
		public Vehicle model(String model) {
			setModel(model);
			return this;
		}
		
		public Integer getYear() {
			return (Integer) this.year.getValue().get();
		}
		
		public void setYear(Integer year) {
			this.year.setValue(new Value(year));
		}
		
		public Vehicle year(Integer year) {
			setYear(year);
			return this;
		}
		
		public Match match() {
			return new Match(this);
		}

		public Create create() {
			return new Create(this);
		}
		
	}
	
	public static class Owns extends Relationship {
		
		static enum TYPE {
			PURCHASED,
			LEASED,
			RENTED
		}
		
		Property ownershipType = new Property("ownershipType");
		
		private static final String LABEL = "OWNS";

		public Owns() {
			super(LABEL);
			properties().add(ownershipType);
		}
		
		public TYPE getOwnershipType() {
			return (TYPE) ownershipType.getValue().get();
		}
		
		public void setOwnershipType(TYPE ownershipType) {
			this.ownershipType.setValue(new Value(ownershipType));
		}
		
		public void clearOwnershiptype() {
			ownershipType.getValue().clear();
		}
		
		@Override
		public Properties properties() {
			
			return super.properties();
		}
		
	}
	
	public static class PersonOwnsVehicle extends Path {
		
		public PersonOwnsVehicle() {
			super();
			setRelationship(new Owns());
		}
		
		public PersonOwnsVehicle(Person person) {
			this();
			setPerson(person);
		}
		
		public void setPerson(Person person) {
			setFrom(person);
		}

		public PersonOwnsVehicle person(Person person) {
			setFrom(person);
			return this;
		}

		public void setVehicle(Vehicle vehicle) {
			setTo(vehicle);
		}
		
		public PersonOwnsVehicle vehicle(Vehicle vehicle) {
			setVehicle(vehicle);
			return this;
		}
		
		public Owns ownership() {
			return (Owns) getRelationship();
		}

		public PersonOwnsVehicle ownership(Owns.TYPE ownershipType) {
			ownership().setOwnershipType(ownershipType);
			return this;
		}
		
		public Match match() {
			return new Match(this);
		}
		
		public Create create() {
			return new Create(this);
		}

	}

	@Test
	void personOwnVehicleTest() {
		
		Person john = new Person("John");
		
		Create create = john.create();
		assertEquals(create.toString(), "CREATE (:Person {name: 'John'})");

		Vehicle volt = new Vehicle("Chevy", "Volt", 2017);
		create = volt.create();
		assertEquals(create.toString(), "CREATE (:Vehicle {make: 'Chevy', model: 'Volt', year: 2017})");

		Match match = john.ownsVehicle(volt).match();
		assertEquals(match.toString(), "MATCH (:Person {name: 'John'})-->(:Vehicle {make: 'Chevy', model: 'Volt', year: 2017})");

		match = john.ownsVehicle(volt).ownership(Owns.TYPE.LEASED).match();
		assertEquals(match.toString(), "MATCH (:Person {name: 'John'})-[]->(:Vehicle {make: 'Chevy', model: 'Volt', year: 2017})");
		
	}

}
