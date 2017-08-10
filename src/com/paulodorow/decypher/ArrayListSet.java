package com.paulodorow.decypher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Set implementation backed by an array list. Preserves
 * insertion order of elements.
 * 
 * @author Paulo
 *
 * @param <E> 
 */
public class ArrayListSet<E> implements Set<E> {

	private ArrayList<E> items;

	public ArrayListSet() {
		items = new ArrayList<>();
	}
	
	@Override
	public int size() {
		return items.size();
	}

	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return items.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return items.iterator();
	}

	@Override
	public Object[] toArray() {
		return items.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return items.toArray(a);
	}

	@Override
	public boolean add(E e) {
		boolean contains = items.contains(e);
		if (!contains) items.add(e);
		return contains;
	}

	@Override
	public boolean remove(Object o) {
		return items.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return items.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		
		boolean changed = false;
		
		for (E item : c) changed = changed | add(item);
		
		return changed;
		
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return items.retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return items.removeAll(c);
	}

	@Override
	public void clear() {
		items.clear();
	}
	
	
}
