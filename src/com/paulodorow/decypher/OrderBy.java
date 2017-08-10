package com.paulodorow.decypher;

import java.util.Set;

public class OrderBy extends Token {

	private static class Orderable extends Token {
		
		private IsOrderable<?> orderable;
		private OrderDirection direction;
		
		public Orderable(IsOrderable<?> orderable) {
			setOrderable(orderable);
		}
		
		public Orderable(IsOrderable<?> orderable, OrderDirection direction) {
			this(orderable);
			setDirection(direction);
		}

		public IsOrderable<?> getOrderable() {
			return orderable;
		}

		public void setOrderable(IsOrderable<?> orderable) {
			this.orderable = orderable;
		}

		public OrderDirection getDirection() {
			return direction;
		}

		public void setDirection(OrderDirection direction) {
			this.direction = direction;
		}
		
		@Override
		public String toString() {
			return toStringInContext(NoContext.get());
		}
		
		public String toStringInContext(HasContext context) {

			IsReturnable<?> inContext = context.getInContext(getOrderable());
					
			StringBuilder builder = new StringBuilder();
			
			if (inContext instanceof IsOrderable<?>) {
				builder.append(((IsOrderable<?>)inContext).toOrderableString(context));
			} else {
				builder.append(inContext.toReturnableString(context));
			}
			
			if (OrderDirection.DESCENDING.equals(getDirection())) builder.append(" DESCENDING");
			
			return builder.toString();
			
		}
		
		
	}
	
	private Set<Orderable> orderables;
	
	public OrderBy() {
		orderables = new ArrayListSet<>();
	}
	
	public void add(IsOrderable<?>... orderables) {
		for (IsOrderable<?> orderable : orderables) if (orderable != null) this.orderables.add(new Orderable(orderable));
	}

	public void add(OrderDirection direction, IsOrderable<?>... orderables) {
		for (IsOrderable<?> orderable : orderables) if (orderable != null) this.orderables.add(new Orderable(orderable, direction));
	}

	public void remove(OrderDirection direction, IsOrderable<?>... orderables) {
		for (IsOrderable<?> orderable : orderables) if (orderable != null) this.orderables.remove(new Orderable(orderable, direction));
	}

	public void remove(IsOrderable<?>... orderables) {
		
		for (IsOrderable<?> orderable : orderables) if (orderable != null) {
			this.orderables.remove(new Orderable(orderable, OrderDirection.ASCENDING));
			this.orderables.remove(new Orderable(orderable, OrderDirection.DESCENDING));
		}
		
	}
	
	public void setAscending(IsOrderable<?> orderable) {
		
		Orderable comparison = new Orderable(orderable, OrderDirection.DESCENDING);
		
		orderables.stream()
			.filter(current -> comparison.equals(current))
			.forEach(current -> current.setDirection(OrderDirection.ASCENDING));
		
	}

	public void setDescending(IsOrderable<?> orderable) {
		
		Orderable comparison = new Orderable(orderable, OrderDirection.ASCENDING);
		
		orderables.stream()
			.filter(current -> comparison.equals(current))
			.forEach(current -> current.setDirection(OrderDirection.DESCENDING));
		
	}

	public void clear() {
		this.orderables.clear();
	}
	
	public int count() {
		return this.orderables.size();
	}
	
	public boolean hasOrder() {
		return count() > 0;
	}

	@Override
	public String toString() {
		return toStringInContext(NoContext.get());
	}
	
	public String toStringInContext(HasContext context) {

		ListBuilder listBuilder = new ListBuilder();
		
		for (Orderable orderable : orderables) {

			listBuilder.appendToList(orderable.toStringInContext(context));

		}

		return hasOrder() ? new StringBuilder("ORDER BY ").append(listBuilder).toString() : "";

	}
		
}
