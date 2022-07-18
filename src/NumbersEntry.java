/*
 * Author: Damian Curran
 */

import java.io.Serializable;
import java.util.Arrays;

/**
 * NumbersEntry class, derived from Entry.
 * @author Name: Damian Curran
 */
public class NumbersEntry extends Entry implements Serializable {

	private int[] numbers;
	private final static int NUMBER_COUNT = 7;
		
	/**
	* Constructor of the NumbersEntry class.
	* @param eid The entry id, int.
	* @param mid The member id, String.
	* @param bid The bill id, String.
	* @param mName The member name, String.
	*/
	public NumbersEntry(int eid, String mid, String bid, String mName) {
		super(eid, mid, bid, mName);
		
	}

	/**
    * Overloaded constructor of the NumbersEntry class (including number array n)
    * @param eid The entry id, int.
    * @param mid The member id, String.
    * @param bid The bill id, String.
    * @param n The array of numbers to associate with this entry.
    * @param mName The member name, String.
    */
	public NumbersEntry(int eid, String mid, String bid, int[] n, String mName) {
		super(eid, mid, bid, mName);
		Arrays.sort(n);
		this.numbers = n;
		
	}
	
	/**
	* Gets the number at index i in the NumbersEntry. Overriden.
	* @param i Index integer of desired number.
	* @return The integer at the index i in the number set of the NumbersEntry object.
	*/
	public int getNumber(int i) {
		return this.numbers[i];
	}

	/**
	* Sets the numbers for the NumbersEntry based on input integer array.
	* @param n Integer array.
	*/
	public void setNumbers(int[] n) {
		this.numbers = n;
	}
	
	/**
	* Checks how many numbers in two NumbersEntries match. Overriden.
	* @param e1 First entry object to be compared.
	* @param e2 Second entry object to be compared.
	* @return Integer of how many numbers in the two parameter objects are the same.
	*/
	public static int noMatching(Entry e1, Entry e2) {
		
		int matches = 0;
		
		for (int i = 0; i < NUMBER_COUNT; i ++) {
			for (int j = 0; j < NUMBER_COUNT; j ++) {
				if (e1.getNumber(i) == e2.getNumber(j)) {
					matches++;
				}
			}
		}
		
		return matches;
	}

	/**
    * Print number array to the console.
    */
	public void printNumbers() {
		for (int j = 0; j < NUMBER_COUNT; j++) {
			System.out.printf("%3s", this.getNumber(j));	
		}
	}
	
}
