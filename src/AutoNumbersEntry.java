/*
 * Author: Damian Curran
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * AutoNumbersEntry class, derived from NumbersEntry.
 * @author Name: Damian Curran
 */
public class AutoNumbersEntry extends NumbersEntry implements Serializable {
	
	private final int NUMBER_COUNT = 7;
	private final int MAX_NUMBER = 35;
	
	/**
	* Constructor of the AutoNumbersEntry class.
	* @param eid The entry id, int.
	* @param mid The member id, String.
	* @param bid The bill id, String.
	* @param mName The member name, String.
	*/
	public AutoNumbersEntry(int eid, String mid, String bid, String mName) {
		super(eid, mid, bid, mName);
	}
	
	/**
    * Overloaded constructor of the AutoNumbersEntry class.
    * Used as default for winning entry.
    */
	public AutoNumbersEntry() {
		super(0, "", "", "");
	}

	/**
	* Creates a number set based on a given seed.
	* If in testing mode, seed is predictable.
	* @param seed Integer seed for generating random numbers.
	* @return An sorted integer array of non-repeating numbers between 0 and max.
	*/
	public int[] createNumbers (int seed) {
		
	    ArrayList<Integer> validList = new ArrayList<Integer>();
	    int[] tempNumbers = new int[NUMBER_COUNT];
	    for (int i = 1; i <= MAX_NUMBER; i++) {
	            validList.add(i);
	    }
	    Collections.shuffle(validList, new Random(seed));
	    for (int i = 0; i < NUMBER_COUNT; i++) {
	            tempNumbers[i] = validList.get(i);
	    }
	    Arrays.sort(tempNumbers);
	    return tempNumbers;
	}
	
	/**
	* Assigns new number set to the entry based on known seed.
	* @param seed Integer seed for generating random numbers.
	*/
	public void newNumberSet (int seed) {
		this.setNumbers(createNumbers(seed));
	}
	
	/**
	* Assigns new number set to the entry based on random number.
	*/
	public void newNumberSet () {
		Random rand = new Random();
		this.setNumbers(createNumbers(rand.nextInt(1000000)));
	}
	
	/**
    * Print number array to the console.
    */
	@Override
	public void printNumbers() {
		for (int j = 0; j < NUMBER_COUNT; j++) {
			System.out.printf("%3s", this.getNumber(j));	
		}
		System.out.print(" [Auto]");
	}
	
}
