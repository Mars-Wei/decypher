package com.paulodorow.decypher.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	ExpressionTests.class,
	ValueTests.class,
	PropertyTests.class,
	PropertiesTests.class,
	NodeTests.class,
	RelationshipTests.class,
	PathTests.class,
	MathOperationsTests.class,
	ComparisonOperationsTests.class,
	BooleanOperationsTests.class,
	StringOperationsTests.class,
	GroupTests.class,
	FunctionsTests.class,
	AggregateFunctionsTests.class,
	ListTests.class,
	ContextTests.class,
	ReturnTests.class,
	WithTests.class,
	MatchTests.class,
	CreateTests.class
})

public class Tests {}
