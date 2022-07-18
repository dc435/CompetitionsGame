/*
 * Author: Damian Curran
 */

import java.io.Serializable;

/**
 * MemberPrize objects used to store memberId and associated maximum prize entry for that member.
 * Used in LuckyNumbers logic during prize draw to ensure no member received multiple prizes.
 * @author Name: Damian Curran
 */
public class MemberPrize implements Serializable {
	
	private String memberId;
	private int maxPrize;
	private int entryId;
	
	/**
    * Constructor of the MemberPrize class.
    * Default prize and entry values are 0.
    * @param memberId The memberId as String.
    */
	public MemberPrize(String memberId) {
		this.memberId = memberId;
		this.maxPrize = 0;
		this.entryId = 0;
		
	}
	
	
	/**
    * Get member id.
    * @return Returns id String.
    */
	public String getMemberId () {
		return this.memberId;
	}
	
	/**
    * Get max prize.
    * @return Returns prize integer.
    */
	public int getMaxPrize() {
		return this.maxPrize;
	}
	
	/**
    * Get entry id.
    * @return Returns id integer.
    */
	public int getEntryId() {
		return this.entryId;
	}

	/**
    * Set max prize.
    * @param mp Maximum prize for that member in the current competition, integer.
    */
	public void setMaxPrize(int mp) {
		this.maxPrize = mp;
	}
	
	/**
    * Set entry id.
    * @param eid Entry id associated with max prize, integer.
    */
	public void setEntryId(int eid) {
		this.entryId = eid;
	}
	

}
