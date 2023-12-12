package org.processmining.contextawareperformance.models.exceptions;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class NotImplementedYetException extends NotImplementedException {

	private static final long serialVersionUID = 7130701248839853600L;

	public NotImplementedYetException(String message) {
		super();
		System.out.println(message);
	}

}