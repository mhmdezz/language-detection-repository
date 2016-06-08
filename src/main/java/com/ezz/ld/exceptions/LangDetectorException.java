package com.ezz.ld.exceptions;

/**
* LangDetectorException class
* Custom exception wrapping any exception raised in the application
*  
* @author  Mohamed Ezz
* @version 1.0
* @since   2016-06-07
*/

public class LangDetectorException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1596419351961917952L;

	public LangDetectorException() {
		super();
	}

	public LangDetectorException(String message) {
		super(message);
	}

	public LangDetectorException(Throwable cause) {
		super(cause);
	}

}
