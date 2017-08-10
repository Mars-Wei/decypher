package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.Path;

public abstract class PathFunction<F> extends Function<F> {

	private Path path;

	public PathFunction() {
		
	}
	
	public PathFunction(Path path)  {
		this();
		setPath(path);
	}

	public void setPath(Path path) {
		this.path = path;
	}
	
	public Path getPath() {
		return path;
	}
	
	public PathFunction<F> withPath(Path path) {
		setPath(path);
		return this;
	}
	
}
