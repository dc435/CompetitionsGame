/*
 * Author: Damian Curran
 */

import java.io.Serializable;

/**
 * Represents a bill item.
 * @author Name: Damian Curran
 * @author Student ID: 1331249
 */
public class Bill implements Serializable {
	
	private String billId;
	private String memberId;
	private float amount;
	private boolean used;
	private final static int COST_PER_ENTRY = 50;
	
	/**
    * Constructor of the Bill class.
    * @param bid The bill id, String.
    * @param mid The member id, String.
	* @param amount The bill amount, float.
    * @param used Boolean indicating if bill previously used to purchase entries.
    */
	public Bill(String bid, String mid, float amount, boolean used) {
		this.billId = bid;
		this.memberId = mid;
		this.amount = amount;
		this.used = used;

	}
	
	/**
	* Static method to check if a billId is in the valid format.
	* Used during bill checking prior to adding entries.
	* @param billId String of billId to be format checked.
	* @return Boolean returned, true if format is valid.
	*/
	public static boolean isValidFormat(String billId) {
		
		if (billId.length() != 6) return false;
		
		try {
			Integer.parseInt(billId);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}

	/**
    * Get Bill id.
    * @return Returns id String.
    */
	public String getBillId() {
		return this.billId;
	}

	/**
    * Get Member id.
    * @return Returns id String.
    */
	public String getMemberId() {
		return this.memberId;
	}

	/**
    * Get used boolean.
    * @return Returns used boolean, true means previously used.
    */
	public boolean getUsed() {
		return this.used;
	}	

	/**
	* Get used boolean as a string
	* @return Returns used boolean as string, "true" means previously used.
	*/	
	public String getUsedString() {
		String usedStr = String.valueOf(this.used);
		return usedStr;	
	}

	/**
    * Get amount as a string
    * @return Returns bill amount as a formatted string for printing.
    */
	public String getAmountString() {
		String amountStr = String.format("$%.1f", this.amount);
		return amountStr;
	}
	
	/**
    * Get amount as a string, no format
    * @return Returns bill amount as a string.
    */	
	public String getAmountStringNoFormat() {
		String amountStr = String.format("%.1f", this.amount);
		return amountStr;
	}
	
	/**
    * Get number of entries the bill can purchase.
    * Calculated as amount of bill / cost per entry.
    * @return Returns integer of number of entries available.
    */
	public int getNoEntries() {
		
		int noEntries;
		
		if (!used) {
			noEntries = (int) this.amount / COST_PER_ENTRY;
		} else {
			noEntries = 0;
		}
		
		return noEntries;
		
	}

	/**
    * Get number of entries the bill can purchase as a string.
    * Calculated as amount of bill / cost per entry.
    * @return Returns number of entries available as string.
    */
	public String getNoEntriesString() {
		
		int noEntries = this.getNoEntries();
		return String.valueOf(noEntries);

	}
	
	/**
	* Used to check if a bill has a member Id. 
	* Used during bill checking prior to adding entries.
	* @return Boolean value, true if bill does have a memberId.
	*/
	public boolean hasMemberId() {
		if (this.getMemberId().equals("")) return false;
		return true;
		
	}
	
	/**
    * Used to check if a bill has enough funds for at least 1 entry.
    * @return Boolean value, true if not enough funds.
    */
	public boolean notEnoughFunds() {
		if (this.amount < COST_PER_ENTRY) return true;
		return false;
	}

	/**
    * Set if the bill is used.
    * @param u Boolean indicating new value, true means used.
    */
	public void setUsed(boolean u) {
		this.used = u;
	}

}
