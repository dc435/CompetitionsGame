/*
 * Author: Damian Curran
 */

import java.io.Serializable;

/**
 * DataAccessException class
 * @author Name: Damian Curran
 * @author Student ID: 1331249
 */
public class DataAccessException extends Exception implements Serializable {

	/**
    * Constructor of the DataAccessException class.
    */
	public DataAccessException(){
		super("Error accessing file data.");
	}

	/**
    * Overloaded constructor of the DataAccessException class.
    * @param type String indicating type of file that caused the exception.
    */
	public DataAccessException(String type){
		super("Error accessing " +type + " file data.");
	}

}