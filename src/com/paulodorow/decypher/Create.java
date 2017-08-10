package com.paulodorow.decypher;

import java.util.ArrayList;
import java.util.List;

public class Create extends Token implements HasReturnDecorator<Create>, IsStatement, AcceptsMatches {
	
	//TODO Complete Create command
//	(CREATE [UNIQUE] | MERGE)*
//	[SET|DELETE|REMOVE|FOREACH]*
	
	//TODO	UNWIND $listOfMaps AS properties CREATE (n) SET n = properties
	
	private List<IsPattern> patterns;
	private boolean isUnique;
	private AcceptsMatches nextStatement;
	
	public Create() {
		patterns = new ArrayList<>();
		nextStatement = new Return();
	}
	
	public Create(IsPattern... patterns) {
		this();
		setPatterns(patterns);
	}

	private void setPatterns(IsPattern... patterns) {
		for (IsPattern pattern : patterns) if (pattern != null) this.patterns.add(pattern);
	}
	
	public Create withPatterns(IsPattern... patterns) {
		setPatterns(patterns);
		return this;
	}

	public void addPatterns(IsPattern... patterns) {
		setPatterns(patterns);
	}

	public List<IsPattern> patterns() {
		return patterns;
	}
	
	public boolean hasPatterns() {
		return patterns.size() > 0;
	}
	
	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}
	
	public boolean isUnique() {
		return isUnique;
	}
	
	public Create withUnique(boolean isUnique) {
		setUnique(isUnique);
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
	
		StringBuilder builder = new StringBuilder("CREATE ");
		
		if (isUnique()) builder.append("UNIQUE "); 
	
		StringBuilder patternBuilder = new StringBuilder();
		
		patterns.forEach((pattern) -> patternBuilder.append(patternBuilder.length() > 0 ? ", " : "").append(pattern.toPatternString(context)));
		
		String nextStatementStr = nextStatement.toStringInContext(context);
		
		builder.append(patternBuilder);
		
		if (!nextStatementStr.isEmpty()) builder.append(" ").append(nextStatementStr);
		
		return builder.toString();
	
	}

	@Override
	public String toString() {
		return toStringInContext(NoContext.get());
	}

}
