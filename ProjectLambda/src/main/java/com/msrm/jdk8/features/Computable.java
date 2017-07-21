package com.msrm.jdk8.features;

import java.util.Objects;

public interface Computable<T extends Number> {

	T compute(T t);

	public default boolean checkIfEven(T t) {
		return Objects.nonNull(t) && ((t.intValue() & 1) == 0);
	}

}
