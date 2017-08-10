package com.paulodorow.decypher.functions;

import com.paulodorow.decypher.HasContext;
import com.paulodorow.decypher.IsReturnable;
import com.paulodorow.decypher.List;
import com.paulodorow.decypher.Path;

public class Size extends Function<Size> {

	private IsReturnable<?> object;

	public Size(Path path) {
		setPath(path);
	}

	public Size(List list) {
		setList(list);
	}

	public void setPath(Path path) {
		this.object = path;
	}
	
	public Path getPath() {
		return object instanceof Path ? (Path)object : null;
	}
	
	public Path withPath(Path path) {
		setPath(path);
		return getPath();
	}

	public void setList(List list) {
		this.object = list;
	}
	
	public List getList() {
		return object instanceof List ? (List)object : null;
	}
	
	public List withList(List list) {
		setList(list);
		return getList();
	}

	@Override
	public String toReturnableString(HasContext context) {
		
		String parameter = object instanceof Path ? getPath().toPatternString(context) : getList().toReturnableString(context);
		
		return String.format("size(%s)", parameter);
	}

}
