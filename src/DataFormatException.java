/*
 * Author: Damian Curran
 */

import java.io.Serializable;

/**
 * DataFormatException class
 * @author Name: Damian Curran
 */
public class DataFormatException extends Exception implements Serializable {

	/**
    * Constructor of the DataFormatException class.
    */
	public DataFormatException(){
		super("Error in data format.");
	}

	/**
    * Overloaded constructor of the DataFormatException class.
    * @param type String indicating type of file that caused the exception.
    */
	public DataFormatException(String type){
		super("Error in " + type + " data formatting.");
	}

}