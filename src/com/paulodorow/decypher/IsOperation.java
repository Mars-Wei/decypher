package com.paulodorow.decypher;

import com.paulodorow.decypher.operations.And;
import com.paulodorow.decypher.operations.Group;
import com.paulodorow.decypher.operations.Not;
import com.paulodorow.decypher.operations.Or;
import com.paulodorow.decypher.operations.Xor;

public interface IsOperation<D> extends IsOperand<D> {

	default Group group() {
		return new Group(this);
	}

	default And and(IsOperand<?> operand) {
		return new And(this, operand);
	}

	default Or or(IsOperand<?> operand) {
		return new Or(this, operand);
	}
	
	default Xor xor(IsOperand<?> operand) {
		return new Xor(this, operand);
	}

	default Not not() {
		return new Not(this);
	}
	
}
