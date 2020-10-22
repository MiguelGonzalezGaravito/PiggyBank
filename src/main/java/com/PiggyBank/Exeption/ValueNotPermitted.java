package com.PiggyBank.Exeption;



public class ValueNotPermitted extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1668398822129870029L;

	public ValueNotPermitted() {
		super("Valor no permitido");
	}
	
	public ValueNotPermitted(String message) {
		super(message);
	}
}
