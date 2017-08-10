package com.paulodorow.decypher;

import java.util.ArrayList;

public class Match implements IsStatement, HasReturnDecorator<Match>, HasPredicateDecorator<Match>, AcceptsMatches {

	private ArrayList<IsPattern> patterns;
	private IsPredicate where;

	private boolean isOptional;
	private AcceptsMatches nextStatement;
	
	//TODO implement tests for these statements
	// match (m) where m:Node return m
	// match (n)-[a]-(m) with distinct a create (Person {name:a.name}), create (Parent {name:a.name}) 
	// match (n)-[a]-(m) with distinct a create (Person {name:a.name}) with a create (Parent {name:a.name}) 
	// match (charlie:Person { name:'Charlie Sheen' }) with charlie.name as name  return name

	public Match() {
		patterns = new ArrayList<>();
		nextStatement = new Return().all();
	}
	
	public Match(IsPattern... patterns) {
		this();
		addPatterns(patterns);
	}

	public void addPatterns(IsPattern... patterns) {
		for (IsPattern pattern : patterns) if (pattern != null) this.patterns.add(pattern);
	}

	public Match withPatterns(IsPattern... pattern) {
		addPatterns(pattern);
		return this;
	}
	
	public void clearPatterns() {
		patterns.clear();
	}

	@Override
	public void setWhere(IsPredicate where) {
		this.where = where;
	}
	
	@Override
	public IsPredicate getWhere() {
		return where;
	}

	public void setOptional(boolean isOptional) {
		this.isOptional = isOptional;
	}
	
	public boolean isOptional() {
		return isOptional;
	}
	
	public Match withOptional(boolean isOptional) {
		setOptional(isOptional);
		return this;
	}

	public Match withOptionalMatch(IsPattern pattern) {
		addPatterns(pattern);
		return withOptional(true);
	}
	
	public Match then(AcceptsMatches nextStatement) {
		
		this.nextStatement = nextStatement;
		
		return this;
		
	}

	@Override
	public Return returns() {
		
		if (! (nextStatement instanceof Return)) nextStatement = new Return(
				
				nextStatement instanceof ReturnsResults ? 
						(ReturnsResults<?>)nextStatement : 
						null
						
				);
		
		return (Return) nextStatement;
	}
	
	@Override
	public With with() {
		
		if (! (nextStatement instanceof With)) nextStatement = new With(

				nextStatement instanceof ReturnsResults ? 
						(ReturnsResults<?>)nextStatement : 
						null
				
				);
		
		return (With) nextStatement;
		
	}

	@Override
	public Match match() {
		
		if (! (nextStatement instanceof Match)) nextStatement = new Match();
		
		return (Match) nextStatement;
		
	}

	@Override
	public Create create() {
		
		if (! (nextStatement instanceof Create)) nextStatement = new Create();
		
		return (Create) nextStatement;
		
	}
	
	@Override
	public String toStringInContext(HasContext context) {

		StringBuilder match = new StringBuilder();
		
		if (isOptional()) match.append("OPTIONAL ");
		
		match.append("MATCH ");
		
		StringBuilder patternsBuilder = new StringBuilder();
		for (IsPattern pattern : patterns) {
			if (patternsBuilder.length() > 0) patternsBuilder.append(", ");
			patternsBuilder.append(pattern.toPatternString(context));
		}
		
		match.append(patternsBuilder);

		if (hasWhere()) match.append(" WHERE ").append(where.toPredicateString(context));
		
		match.append(" ");
		
		match.append(nextStatement.toStringInContext(context));
		
		return match.toString();
	
	}
	
	@Override
	public String toString() {
		return toStringInContext(NoContext.get());
	}
	
}
