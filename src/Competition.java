/*
 * Author: Damian Curran
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class represents a single Competition.
 * Derived classes for each particular competition type.
 * @author Name: Damian Curran
 * @author Student ID: 1331249
 */
public class Competition implements Serializable {

	private int id;
	private String name;
	private ArrayList<Entry> entries;
	private boolean testingMode;
	private boolean active;
	
	/**
    * Constructor of the Competitions class.
    * @param n The name of new Competition object, a String.
    * @param id The id of the new object, int.
    * @param tmode Testing mode boolean, true if the object is created whilst in 'Testing mode'.
    */
	public Competition(String n, int id, boolean tmode) {
		this.name = n;
		this.id = id;
		this.entries = new ArrayList<Entry>();
		this.testingMode = tmode;
		this.active = true;
		
	}
	
	/**
    * Get Competition id.
    * @return Returns id integer.
    */
	public int getId() {
		return id;
	}
	
	/**
    * Get Competition name.
    * @return Returns name String.
    */
	public String getName() {
		return name;
	}
	
	/**
    * Get Competition type. Overriden in derived classes.
    * @return Returns derived competition type as String.
    */
	public String getType() {
		return "";
	}
	
	/**
    * Get number of entries in the competition.
    * @return Returns number int.
    */
	public int getNoEntries() {
		return entries.size();
	}
	
	/**
    * Get number of winning entries in the competition.
    * Winning entries are any entries with a non-zero prize.
    * @return Returns number of winning entries int.
    */
	public int getNoWinningEntries() {
		
		int noWinners = 0; 
		for (int i = 0; i < this.getNoEntries(); i ++) {
			Entry e = this.getEntryFromIndex(i);
			if (e.getPrize() != 0) noWinners++;
		}
		
		return noWinners;
	}	
	
	/**
    * Get specific entry in the competition based on index id (entry 1 = index 0).
    * @param index Integer index of entry to locate.
    * @return Returns Entry object.
    */
	public Entry getEntryFromIndex(int index) {
		return entries.get(index);
	}

	/**
    * Get specific entry in the competition based on entryId.
    * @param entryId Integer id of entry to locate.
    * @return Returns Entry object.
    */
	public Entry getEntryFromID(int entryId) {
		
		for (Entry e : entries) {
			if (e.getEntryId() == entryId) return e;
		}
		return entries.get(entryId - 1); //default return
	}

	/**
    * Get to sum of prizes from all entries in the competition.
    * @return Returns sum of prizes int.
    */
	public int getTotalPrizes() {
		
		int totalPrizes = 0; 
		for (int i = 0; i < this.getNoEntries(); i ++) {
			Entry e = this.getEntryFromIndex(i);
			int p = e.getPrize();
			if (p != 0) totalPrizes = totalPrizes + p;
		}
		
		return totalPrizes;
	}	

	/**
    * Identify if competition is active.
    * @return Returns boolean, true if active.
    */
	public boolean getActive() {
		return active;
	}
	
	/**
    * Identify if competition is active.
    * @return Returns String, "yes" if active, "no" otherwise.
    */
	public String getActiveString() {
		if (this.getActive()) {
			return "yes";
		} else {
			return "no";
		}
	}

	/**
    * Identify if competition operates in testing mode.
    * @return Returns boolean, true if in testing mode.
    */
	public boolean getIsTestingMode() {
		return testingMode;
	}

	/**
    * Identify if competition has any entries
    * @return Returns boolean, true if entry count is greater than 0.
    */
	public boolean hasEntries() {
	
		if (this.getNoEntries() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
    * Sets the active status of the competition.
    * @param b Boolean for active status, true is active.
    */
	public void setActive(boolean b) {
		this.active = b;
	}
	
	/**
    * Add entry to the competition.
    * @param e Entry object to add.
    */
	public void addEntry(Entry e) {
		entries.add(e);
	}

	/**
    * Outer control logic for adding entries from a bill. Overriden in derived classes.
    * @param b Bill object to which the new entries will be assigned.
    * @param memberName String of member name associated with the Bill.
    */
	public void addEntries(Bill b, String memberName)  {
		//To override
	}
	
	/**
    * Outer control logic for adding entries from a bill. Overriden in derived classes.
    * Overloaded with Scanner object for user input.
    * @param b Bill object to which the new entries will be assigned.
    * @param memberName String of member name associated with the Bill.
    * @param keyboard Scanner object used for user input.
    */
	public void addEntries(Bill b, String memberName, Scanner keyboard)  {
		//To override
	}
	
	/**
	* Update entries in competition.
	* @param e Entry object for updating.
	*/
	public void updateEntries (Entry e) {
		int index = e.getEntryId() - 1;
		entries.set(index, e);
	}

	/**
    * Control logic for drawing winners for the competition. Overriden in derived classes.
    */
	public void drawWinners() {
		//To override
	}
	
	/**
    * Control logic for printing winners for the competition to the console. 
    * Overriden in derived classes.
    */
	public void printWinners() {
		//To override
	}
	
	/**
    * Print competition details to the console, including id, name and type.
    */
	public void printCompetitionDetails() {
		System.out.println("Competition ID: " + this.getId()
				+ ", Competition Name: " + this.getName()
				+ ", Type: " + this.getType());	
	}
	
	/**
    * Print competition summary to the console.
    */
	public void printCompetitionSummary() {
		
		System.out.println("Competition ID: " + this.getId()
				+ ", name: " + this.getName()
				+ ", active: " + this.getActiveString());
		System.out.println("Number of entries: " + this.getNoEntries());
		
		if (!this.getActive()) {
			System.out.println("Number of winning entries: " + this.getNoWinningEntries());
			System.out.println("Total awarded prizes: " + this.getTotalPrizes());
			
		}
	}
		
}
