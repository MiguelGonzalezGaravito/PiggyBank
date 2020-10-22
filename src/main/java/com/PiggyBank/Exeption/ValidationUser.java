package com.PiggyBank.Exeption;



public class ValidationUser extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4995433707591853255L;
	
	private String fieldName;
	
	public ValidationUser(String message, String fieldName) {
		super(message);
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return this.fieldName;
	}
}
