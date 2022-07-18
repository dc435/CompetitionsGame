/*
 * Author: Damian Curran
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Derived Competition class representing a Random Pick competition type.
 * @author Name: Damian Curran
 * @author Student ID: 1331249
 */
public class RandomPickCompetition extends Competition implements Serializable {
	
	private final int FIRST_PRIZE = 50000;
	private final int SECOND_PRIZE = 5000;
	private final int THIRD_PRIZE = 1000;
	private final int[] prizes = {FIRST_PRIZE, SECOND_PRIZE, THIRD_PRIZE};
	private final int MAX_WINNING_ENTRIES = 3;
	private static final String compType = "RandomPickCompetition"; 
	
	/**
    * Constructor of the RandomPickCompetition class.
    * @param n The name of new competition object, a String.
    * @param id The id of the new object, int.
    * @param tmode Testing mode boolean, true if the object is created whilst in 'Testing mode'.
    */
	public RandomPickCompetition(String n, int id, boolean tmode) {
		
		super(n, id, tmode);

	}
	
	/**
    * Get competition type. Override.
    * @return Returns derived competition type as String.
    */
	@Override
	public String getType() {
		return compType;
	}
	
	/**
    * Outer control logic for adding entries from a bill. Override.
    * Automatically generates the number of new Entry objects permitted by the bill.
    * @param b Bill object to which the new entries will be assigned.
    * @param memberName String of member name associated with the Bill.
    * @param keyboard Scanner object used for user input.
    */
	@Override
	public void addEntries(Bill b, String memberName, Scanner keyboard) {
		
    	System.out.println("This bill (" + b.getAmountString()
    			+ ") is eligible for " + b.getNoEntriesString()
    			+ " entries.");
    	
    	int number = b.getNoEntries();
    	String memberId = b.getMemberId();
    	String billId = b.getBillId();
			
		int firstNewEntry = this.getNoEntries() + 1;

		System.out.println("The following entries have been automatically generated:");
		
		//Construct new entry object and add to competition, the number of times provided for in the bill:	
		for (int i = firstNewEntry; i < firstNewEntry + number; i++) {
			
			Entry e = new Entry(i, memberId, billId, memberName);
			this.addEntry(e);
			System.out.print("Entry ID: ");
			System.out.printf("%-6d%n", e.getEntryId());

		}
		
	}
	
	/**
    * Control logic for drawing winners for the competition. Override.
    * Winning Entries are random unless the competition is in testing mode.
    */
	@Override
	public void drawWinners() {
		
		//Build random number generator based on whether competition is in Testing mode
		Random randomGenerator = null;
		if (this.getIsTestingMode()) {
			randomGenerator = new Random(this.getId());
		} else {
			randomGenerator = new Random();
		}
		
		/* 
		 * Competition policy prevents one member from receiving more than one prize.
		 * Create list of unique member IDs with entries in the competition.
		 * Check list as entries are drawn to ensure no member wins more than once.
		 */

		ArrayList<String> remainingContestantIds = getUniqueMemberIds();
		
		//Draw winners
		int winningEntryCount = 0;
		while (winningEntryCount < MAX_WINNING_ENTRIES) {
			
			int winningEntryIndex = randomGenerator.nextInt(this.getNoEntries());

			Entry winningEntry = this.getEntryFromIndex(winningEntryIndex);
			
			boolean notYetWon;
			
			//Check if member Id has won already, and update 'notYetWon' boolean
			if (remainingContestantIds.contains(winningEntry.getMemberId())) {
				notYetWon = true;
			} else {
				notYetWon = false;
			}
			
			if (winningEntry.getPrize() == 0) {
				
				//Member will only be awarded prize if they have not yet won.
				
				if (notYetWon) {
					
					int currentPrize = prizes[winningEntryCount];
					winningEntry.setPrize(currentPrize);
					
					//Remove member who just won from the remaining contestants list: 
					int index = remainingContestantIds.indexOf(winningEntry.getMemberId());
					remainingContestantIds.remove(index);		
					
				}
				
				winningEntryCount++;
			}			

		}
		
	}
	
	/**
    * Control logic for printing winners for the competition to the console. Override.
    */
	@Override
	public void printWinners() {
		
		printCompetitionDetails();
		System.out.println("Winning entries:");
		for (int i = 0; i < this.getNoEntries(); i ++) {
			
			Entry e = this.getEntryFromIndex(i);
			if (e.getPrize() != 0) {
				System.out.printf("Member ID: %2s, Member Name: %2s, Entry ID: %-1d, Prize: %-5d%n",
						e.getMemberId(), e.getMemberName(), e.getEntryId(), e.getPrize());
			}
			
		}
		
	}

	/*
	 *  Helper method to generate an array of unique memberIds with entries in the competition.
	 *  @return ArrayList of member Id Strings.
	 */
	private ArrayList<String> getUniqueMemberIds() {
		
		ArrayList<String> uniqueMemberIds = new ArrayList<String>();
		for (int i = 0; i < this.getNoEntries(); i ++) {
			Entry e = this.getEntryFromIndex(i);
			if (!uniqueMemberIds.contains(e.getMemberId())) {
				uniqueMemberIds.add(e.getMemberId());
			}
		}
		
		return uniqueMemberIds;
	}
		
}