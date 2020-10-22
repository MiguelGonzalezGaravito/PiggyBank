package com.PiggyBank.Exeption;



public class IdNotFound extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1668398822129870029L;

	public IdNotFound() {
		super("Id no encontrado");
	}
	
	public IdNotFound(String message) {
		super(message);
	}
}
