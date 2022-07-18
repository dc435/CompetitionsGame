/*
 * Author: Damian Curran
 */

import java.io.Serializable;

/**
 * Parent class for Entries.
 * @author Name: Damian Curran
 * @author Student ID: 1331249
 */
public class Entry implements Serializable {

	private int entryId;
	private String memberId;
	private String billId;
	private String memberName;
	private int prize;
	
	/**
    * Constructor of the Entry class.
    * @param eid The entry id, int.
    * @param mid The member id, String.
    * @param bid The bill id, String.
    * @param mName The member name, String.
    */
	public Entry(int eid, String mid, String bid, String mName) {
		this.entryId = eid;
		this.memberId = mid;
		this.billId = bid;
		this.prize = 0;
		this.memberName = mName;
		
	}
	
	/**
    * Get Entry id.
    * @return Returns id integer.
    */
	public int getEntryId() {
		return this.entryId;
	}
	
	/**
    * Get Member id.
    * @return Returns id String.
    */
	public String getMemberId() {
		return this.memberId;
	}
	
	/**
    * Get Bill id.
    * @return Returns id String.
    */
	public String getBillId() {
		return this.billId;
	}
	
	/**
    * Get prize for the entry (default 0 unless winners drawn in associated competition).
    * @return Returns prize integer.
    */
	public int getPrize() {
		return this.prize;	
	}
	
	/**
    * Get prize as a string
    * @return Returns prize as String.
    */
	public String getPrizeString() {
		return (String.valueOf(this.prize));
	}
	
	/**
    * Get member name.
    * @return Returns name String.
    */
	public String getMemberName() {
		return this.memberName;	
	}
	
	/**
	* Gets the number at index i in the NumbersEntry.
	* To override.
	* @param i Index integer of desired number.
	* @return The integer at the index i in the number set of the NumbersEntry object.
	*/
	public int getNumber(int i) {
		return 0;
	}

	/**
	* Checks how many numbers in two numbers entries match.
	* To override. 
	* @param e1 First entry object to be compared.
	* @param e2 Second entry object to be compared.
	* @return Integer of how many numbers in the two parameter objects are the same.
	*/
	public static int noMatching(Entry e1, Entry e2) {
		return 0; //To override
	}

	/**
    * Sets the prize.
    * @param p An integer of prize amount.
    */
	public void setPrize(int p) {
		this.prize = p;
		
	}
	
	/**
    * Print numbers associated with entry. 
    * To be overriden, used by NumbersEntry derived class.
    */
	public void printNumbers() {
		//To override
	}
	
}
