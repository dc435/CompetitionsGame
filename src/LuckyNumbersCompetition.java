/*
 * Author: Damian Curran
 */

import java.util.Scanner;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Derived Competition class representing a Lucky Numbers competition type.
 * @author Name: Damian Curran
 */
public class LuckyNumbersCompetition extends Competition implements Serializable {
	
	private AutoNumbersEntry win;
	private final int SEVEN_NUMBERS = 50000;
	private final int SIX_NUMBERS = 5000;
	private final int FIVE_NUMBERS = 1000;
	private final int FOUR_NUMBERS = 500;
	private final int THREE_NUMBERS = 100;	
	private final int TWO_NUMBERS = 50;
	private final int[] prizes = {0, 0, TWO_NUMBERS, THREE_NUMBERS, FOUR_NUMBERS,
			FIVE_NUMBERS, SIX_NUMBERS, SEVEN_NUMBERS};
	private final int NUMBER_COUNT = 7;
	private final int MAX_NUMBER = 35;
	private static final String compType = "LuckyNumbersCompetition"; 
		
	/**
    * Constructor of the LuckyNumbersCompetition class.
    * @param n The name of new competition object, a String.
    * @param id The id of the new object, int.
    * @param tmode Testing mode boolean, true if the object is created whilst in 'Testing mode'.
    */
	public LuckyNumbersCompetition(String n, int id, boolean tmode) {
		
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
    * Logic for both manual and automatic numbers entries.
    * @param b Bill object to which the new entries will be assigned.
    * @param memberName String of member name associated with the Bill.
    * @param keyboard Scanner object used for user input.
    */
	@Override
	public void addEntries(Bill b, String memberName, Scanner keyboard) {
		
    	int numberOfEntries = b.getNoEntries();
    	String memberId = b.getMemberId();
    	String billId = b.getBillId();
    	
    	//User inputs and program validates the number of manual entries for this bill:  	
    	int noManualEntries = getNoManualEntries(b, numberOfEntries, keyboard);

    	//User inputs any manual entry number sets:
    	ArrayList<int[]> manualEntries = getManualEntries(noManualEntries, keyboard);
    	
    	//Create new entries and add to the competition:
    	int firstNewEntry = this.getNoEntries() + 1;
    	
    	System.out.println("The following entries have been added:");

    	//Add and print Manual-number entries:
		for (int i = firstNewEntry; i < firstNewEntry + noManualEntries; i++) {
			
			//Use NumbersEntry derived class for manual entries.
			NumbersEntry e = 
					new NumbersEntry(i, memberId, billId, manualEntries.get(i - firstNewEntry), memberName);
			this.addEntry(e);
			
			System.out.printf("%-1s %-6s %-8s", "Entry ID:", e.getEntryId(), "Numbers:");
			e.printNumbers();
			System.out.println();
			
		}
    	
    	//Add and print Auto-number entries:
		for (int i = firstNewEntry + noManualEntries; i < firstNewEntry + numberOfEntries; i++) {
			
			//Used AutoNumbersEntry derived class for auto-number entries.
			AutoNumbersEntry e = new AutoNumbersEntry(i, memberId, billId, memberName);
			
			//Add auto numbers to new entry based on testing mode.
			if (this.getIsTestingMode()) {
				//If in testing mode, create number set based on number of entries in current competition:
				e.newNumberSet(this.getNoEntries());
			} else {
				//If not in testing mode, create number set based on random seed:
				e.newNumberSet();
			}
			this.addEntry(e);
			
			System.out.printf("%-1s %-6s %-8s", "Entry ID:", e.getEntryId(), "Numbers:");
			e.printNumbers();
			System.out.println();
			
		}  	
    			
	}
	
	/**
    * Control logic for drawing winners for the competition. Override.
    * Winning Entries are random unless the competition is in testing mode.
    */
	@Override
	public void drawWinners() {
		
		win = new AutoNumbersEntry();
		
		if (this.getIsTestingMode()) {
			win.newNumberSet(this.getId()); //Creates winning number set based on competition ID seed
		} else {
			win.newNumberSet(); //Creates winning numbers set based on random seed
		}
		
		//Generate list of MemberPrize objects.
		//Used to track whether a member has won a prize previously and identify maximum prize.
		ArrayList<MemberPrize> memberMaxPrizes = new ArrayList<MemberPrize>();
		ArrayList<String> contestantIds = getUniqueMemberIds();
		for (String s : contestantIds) {
			MemberPrize mp = new MemberPrize(s);
			memberMaxPrizes.add(mp);
		}
		
		//Draw winners, checking that member has not previously won a higher prize in this competition.
		for (int i = 0; i < this.getNoEntries(); i ++) {
			Entry e = this.getEntryFromIndex(i);
			int matches = NumbersEntry.noMatching(e, win);
			if (matches > 1) {
				
				int index = 0;
				for (MemberPrize mp : memberMaxPrizes) {
					String memberId = mp.getMemberId();
					if (memberId.equals(e.getMemberId())) index = memberMaxPrizes.indexOf(mp);
				}
				
				int prize = prizes[matches];				
				if (prize > memberMaxPrizes.get(index).getMaxPrize()) {
					memberMaxPrizes.get(index).setMaxPrize(prize);
					memberMaxPrizes.get(index).setEntryId(e.getEntryId());
				}
			}
		}
		
		//Update entries in the competition with prizes as awarded.
		for (MemberPrize mp : memberMaxPrizes) {
			if (mp.getMaxPrize() > 0) {
				Entry winningEntry = this.getEntryFromID(mp.getEntryId());
				int maxPrize = mp.getMaxPrize();
				winningEntry.setPrize(maxPrize);
				this.updateEntries(winningEntry);	
			}
		}
			
	}
	
	/**
    * Control logic for printing winners for the competition to the console. Override.
    */
	@Override
	public void printWinners() {
		
		printCompetitionDetails();
		System.out.print("Lucky Numbers:");
		win.printNumbers();
		System.out.print("\n");
		System.out.println("Winning entries:");
		
		for (int i = 0; i < this.getNoEntries(); i ++) {
			
			Entry e = this.getEntryFromIndex(i);
			if (e.getPrize() != 0) {
				System.out.printf("Member ID: %2s, Member Name: %2s, Prize: %-5d%n",
						e.getMemberId(), e.getMemberName(), e.getPrize());
				System.out.print("--> Entry ID: " + e.getEntryId()
						+ ", Numbers:");
				e.printNumbers();
				System.out.print("\n");
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
	
	/*
	 *  Helper logic for entering and validating the number of manual entries to be inputed.
	 *  @return integer of number of entries.
	 */
	private int getNoManualEntries(Bill b, int numberOfEntries, Scanner keyboard) {
		
		int noManualEntries = 0;
	    	
		System.out.println("This bill (" + b.getAmountString()
				+ ") is eligible for " + b.getNoEntriesString()
				+ " entries. How many manual entries did the customer fill up?: ");

		while (true) {
			try {
		    	    	  
				noManualEntries = Integer.parseInt(keyboard.nextLine());
				
				if (noManualEntries > numberOfEntries || noManualEntries < 0) {
					System.out.println("The number must be in the range from 0 to "
							+ numberOfEntries + ". Please try again.");
					continue;
				} else {
					return noManualEntries;
				}
				
			} catch (Exception e) {
				System.out.println("Invalid input! A number is expected. Please try again!");
				continue;
			}
		}
		
	}
	
	/*
	 *  Helper method to take the numbers in the manual entries from the user.
	 *  @return arraylist of the numbered entries.
	 */
	private ArrayList<int[]> getManualEntries(int noManualEntries, Scanner keyboard) {
		
    	ArrayList<int[]> manualEntries = new ArrayList<int[]>();
    	int increment = 0;
    	String line;
    	
    	manualEntryLoop: while(increment < noManualEntries) {
    		
	    	System.out.println("Please enter 7 different numbers "
	    			+ "(from the range 1 to 35) separated by whitespace.");
	    	
	    	int[] numbers;
	    	line = keyboard.nextLine();
	    	numbers = new int[NUMBER_COUNT];
	    	String[] linesplit = line.split(" ");
	    	
	    	//Check manual inputs are all numbers:
	    	try {
	    		for (int j = 0; j < linesplit.length; j ++) {
	    			int x = Integer.parseInt(linesplit[j]);
	    		}
	    	} catch (Exception e) {
	    		System.out.println("Invalid input! Numbers are expected. Please try again!");
	    		continue manualEntryLoop;
	    	}
	    	
	    	//Check the correct count of numbers has been entered:
    		if (linesplit.length < NUMBER_COUNT) {
    			System.out.println("Invalid input! Fewer than 7 numbers are provided. Please try again!");
    			continue manualEntryLoop;
    		}	    		
    		if (linesplit.length > NUMBER_COUNT) {
    			System.out.println("Invalid input! More than 7 numbers are provided. Please try again!");
    			continue manualEntryLoop;
    		}
	    	
    		//Parse entered numbers into number array:
	    	for (int j = 0; j < NUMBER_COUNT; j ++) {
    			int indivNumber = Integer.parseInt(linesplit[j]);
    			numbers[j] = indivNumber; 
    		}
	    	
	    	//Check for duplicates in numbers with nested loop:
	    	for (int j = 0; j < NUMBER_COUNT; j++) {
		    	for (int k = 0; k < NUMBER_COUNT; k++) {
					if (j!=k && numbers[k] == numbers[j]) {
						System.out.println("Invalid input! All numbers must be different!");
	    				continue manualEntryLoop;
					}
				}
	    	}
	    		
	    	//Check that entered numbers are within range:
	    	for (int j = 0; j < NUMBER_COUNT; j ++) {
    			if (numbers[j] > MAX_NUMBER || numbers[j] < 1) {
    				System.out.println("Invalid input! All numbers must be in the range from 1 to 35!");
    				continue manualEntryLoop;
    			}	  
    		}	
	    	
	    	//If all checks are passed, add numbers to manual entries array list.
	    	manualEntries.add(numbers);
	    	increment++;

    	}
    	
    	return manualEntries;
		
	}
	
}
