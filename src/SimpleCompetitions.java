/*
 * Author: Damian Curran
 */

import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Main class of Simple Competitions program.
 * The program helps companies create competitions.
 * @author Name: Damian Curran
 */
public class SimpleCompetitions implements Serializable {
    
	private Competition comp;
	private boolean testingMode;
	private DataProvider data;
	private ArrayList<Competition> completedComps;
	private static Scanner keyboard = new Scanner(System.in);
	private static String fileName; //= "data.dat";
	
	/**
    * Main program that uses the main SimpleCompetitions class
    * @param args main program arguments
    */
    public static void main(String[] args) {
    	
    	System.out.println("----WELCOME TO SIMPLE COMPETITIONS APP----");
    	SimpleCompetitions sc = loadSC();
        sc.run();
        exitOptions(sc);
        
    }
    
	/**
    * Constructor of the SimpleCompetitions class
    * Creates a SimpleCompetitions object from which the balance of the program is executed.
    * The object can be saved and reloaded by the user.
    * @param tmode Testing mode boolean. True if the new object is in to be in testing mode. 
    */
	public SimpleCompetitions(boolean tmode) {
		
		this.comp = null;
		this.testingMode = tmode;
		this.data = null;
		this.completedComps = new ArrayList<Competition>();
		
	}

	//--- NOTE:
	//--- Javadoc formatting not included for private methods per Week 5 slides ---
	//--- However, for completeness, comments included on private methods in pseudo-Javadoc format ---
	
	
	/*
    * Static method called at the commencement of the main program.
    * Provides options to users to load saved SimpleCompetitions object, or create new.
    * @return SimpleCompetitions object, which was either loaded from file, or new. 
    */
	private static SimpleCompetitions loadSC() {
		
    	boolean loadFromFile = false;
    	
    	boolean takingInput = true;
    	while(takingInput) {
    		
    		System.out.println("Load competitions from file? (Y/N)?");	
    		String userInput = keyboard.nextLine().toUpperCase();	    	
    		switch (userInput) {
			
				case "Y":
					
					loadFromFile = true;
					System.out.println("File name:");
					fileName = keyboard.nextLine();
					takingInput = false;
					break;
					
				case "N":

					takingInput = false;
					break;
				
				default:
					
					System.out.println("Unsupported option. Please try again!");
					break;
			}    		
    	}
    	
    	if (!loadFromFile) {
    		
    		boolean tMode = queryTestMode();
    		return new SimpleCompetitions(tMode);
    		
    	} else {
    		
    		return loadSCFromFile();
    	
    	}
		
	}
	
	/*
	 *  Static method loads SimpleCompetitions object from a binary file.
	 *  @return SimpleCompetitions object from the file.
	 */
	private static SimpleCompetitions loadSCFromFile() {
		
		File fileObject = new File(fileName);

		if(fileObject.exists()) {

			ObjectInputStream inputStream = null;

			try {
				
				inputStream = new ObjectInputStream(new FileInputStream(fileName));
				SimpleCompetitions sc = (SimpleCompetitions) inputStream.readObject();
				inputStream.close();
				return sc;
				
			} catch (FileNotFoundException e) {
				System.out.println("Could not open file: " + fileName
						+ ". Goodbye.");
				System.exit(0);
				
			} catch(IOException e) {
				System.out.println("Could not read from file. Goodbye.");
				System.exit(0);
				
			} catch(ClassNotFoundException e) {
				System.out.println("Class not found. Goodbye.");
				System.exit(0);
				
			} catch (Exception e) {
				System.out.println("Error loading file. Goodbye.");
				System.exit(0);
				
			}
			
		} else {
			
			System.out.println("Could not open file: " + fileName
					+ ". Goodbye.");
			System.exit(0);
			
		}
		
		//Default return:
		System.out.println("Error. Could not load file. Launch default.");
    	return new SimpleCompetitions(true); 
    	
	}
	
	/*
	 *  Logic for user to elect test or normal mode prior to constructing new SimpleCompetitions object.
	 *  @return boolean, true if in test mode.
	 */
	private static boolean queryTestMode() {
		
		boolean choosingMode = true;
		while(choosingMode) {
			
			System.out.println("Which mode would you like to run? "
					+ "(Type T for Testing, and N for Normal mode):");
			String userInput = keyboard.nextLine().toUpperCase();	    	
			switch (userInput) {
			
				case "T":
					
					return true;
					
				case "N":
					
					return false;
				
				default:
					
					System.out.println("Invalid mode! Please choose again.");
					break;
			}    		
		}
		
		return false; //Default return
		
	}
        
	/*
	 *  Logic for exiting program and saving SimpleCompetitions object to binary file.
	 *  Also updates bills text files if user elects to save.
	 *  @param SimpleCompetitions object to be saved.
	 */
	private static void exitOptions(SimpleCompetitions sc) {
		
		boolean saveToFile = false;
		
		boolean takingInput = true;
		while(takingInput) {
			
			System.out.println("Save competitions to file? (Y/N)?");
			String userInput = keyboard.nextLine().toUpperCase();	    	
			switch (userInput) {
			
				case "Y":
					
					saveToFile = true;
					takingInput = false;
					break;
					
				case "N":
					
					takingInput = false;
					break;
				
				default:
					
					System.out.println("Invalid response! Please choose again.");
					break;
			}    		
		}
		
		if (saveToFile) {
		
			//Update SimpleCompetitions to binary data file:
			System.out.println("File name:");
			fileName = keyboard.nextLine();
			
			ObjectOutputStream outputStream = null;

			try {
				
				outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
				outputStream.writeObject(sc);
				outputStream.close();
				System.out.println("Competitions have been saved to file.");
				
			} catch (FileNotFoundException e) {
				System.out.println("Error saving competitions. Could not open file: " + fileName);
				
			} catch(IOException e) {
				System.out.println("Error saving competitions. Could not read from file.");
				System.out.println(e.getMessage());
				
			}
			
			//Update bills file:
			try {
				
				sc.saveBillFile();
				System.out.println("The bill file has also been automatically updated.");				

			} catch (Exception e) {
				
				System.out.println("Error updating bill file.");
				System.out.println(e.getMessage());
			}
			
		}
		
		System.out.println("Goodbye!");
		
	}

	/*
	 *  Main program logic for running SimpleCompetitions object.
	 */	
    private void run() {
    	
    	//Call logic for identifying and loading members and bills files to DataProvider object
    	loadMemberBillFiles();

    	//Main in-game loop
    	boolean inGame = true;
    	while (inGame) {
			
    		displayMenuOptions();
    		String userInput = keyboard.nextLine();
			switch (userInput) {
			
				case "1":
					
					createNewCompetition();
					break;
					
				case "2":
					
					addNewEntriesOuterLoop();
					break;
					
				case "3":
					
					drawWinners();
					break;
					
				case "4":
					
					printSummaryReport();
					break;
					
				case "5":
					
					inGame = false;
					break;
				
				default:
					
					notRecognised(userInput);
					break;
			
			}
    	}    	
    }
    
	/*
	 *  Logic for loading member and bill files at beginning of main program run.
	 */
    private void loadMemberBillFiles() {
    	
    	System.out.println("Member file: ");
    	String mFile = keyboard.nextLine();
    	System.out.println("Bill file: ");
    	String bFile = keyboard.nextLine();
    	
    	//Check if SimpleCompetitions object (with a DataProvider) is already loaded from binary file
    	if (data!=null) return;  
    	
    	//Try to open files. Terminate if error
    	try {
    		data = new DataProvider(mFile,bFile);
    	} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Please check file data and try again. Goodbye");
			System.exit(0);
    	}
    	
    }
    
	/*
	 *  Logic for creating new Competition object within Simple Competition object.
	 *  Called from main program loop, option 1
	 */
	private void createNewCompetition() {
		
		//Check first if there is already an active competition
		if (activeComp()) {
			System.out.println("There is an active competition. "
					+ "SimpleCompetitions does not support concurrent competitions!");
			return;
		}
		
		//Default competition type is RandomNumbers
		boolean lucky = false;
		
    	boolean choosingType = true;
    	while(choosingType) {
    		
    		System.out.println("Type of competition (L: LuckyNumbers, R: RandomPick)?:");
    		String userInput = keyboard.nextLine().toUpperCase();	    	
    		
    		switch (userInput) {
			
				case "L":
					
					lucky = true;
					
				case "R": 
					
					choosingType = false;
					break;

				default:
					
					System.out.println("Invalid competition type! Please choose again.");
					break;
			}    		
    	}
    	
    	System.out.println("Competition name: ");
   		String compName = keyboard.nextLine();	 
   		int compId = completedComps.size() + 1;
		
   		//Create new competition object using override constructors
   		if (lucky) {
   			comp = new LuckyNumbersCompetition(compName, compId, testingMode);
   		} else {
   			comp = new RandomPickCompetition(compName, compId, testingMode);
   		}
		
   		System.out.println("A new competition has been created!\n"
   				+ "Competition ID: " + comp.getId()
   				+ ", Competition Name: " + comp.getName()
   				+ ", Type: " + comp.getType()
   		);

	}
    
	/*
	 *  Outer loop control logic for adding new entries to the current competition
	 *  Called from main program loop, option 2
	 */	
	private void addNewEntriesOuterLoop() {
		
		//Check first if there is an active competition
		if (!activeComp()) {
			System.out.println("There is no active competition. Please create one!");
			return;
		}		
		
		addEntriesLoop: while (true) {
			
			//Call detailed inner loop for adding entries
			addNewEntriesInnerLoop();		
			
			//Further loop to control exit to main menu
			queryMoreLoop: while (true) {
			
				System.out.println("Add more entries (Y/N)?");
				String userInput = keyboard.nextLine().toUpperCase();
				if (userInput.equals("Y")) {
					continue addEntriesLoop;
				} else if (userInput.equals("N")) {
					break addEntriesLoop;
				} else {
					System.out.println("Unsupported option. Please try again!");
					continue queryMoreLoop;
				}
			}
			
		}
	}
	
	
	/*
	 *  Inner loop with detailed control logic for user to add new entries to the current competition
	 *  Called from addNewEntriesOuterLoop()
	 */
	private void addNewEntriesInnerLoop() {
		
		//Logic to take user input and check format of BillId
    	boolean checkingBillId = true;
    	Bill currentBill = null;
    	
    	while(checkingBillId) {
    		
    		System.out.println("Bill ID: ");		
    		String billId = keyboard.nextLine();	
    		
    		//Check bill format, eg "123abc"
    		if (!Bill.isValidFormat(billId)) {
    			System.out.println("Invalid bill id! It must be a 6-digit number. Please try again.");
    			continue;
    		} 
    		
    		//Check if bill exists in bills on DataProvider
    		if (!data.billExists(billId)) {
    			System.out.println("This bill does not exist. Please try again.");
    			continue;
    		}
    		
    		//Retrieve bill object from DataProvider
    		currentBill = data.getBill(billId);
    		    		
    		//Check if bill object has corresponding member id
    		if (!currentBill.hasMemberId()) {
    			System.out.println("This bill has no member id. Please try again.");
    			currentBill = null;
    			continue;
    		}
    		
    		//Check if bill has been used previously
			if (currentBill.getUsed()) {
				System.out.println("This bill has already been used for a competition. Please try again.");
				currentBill = null;
				continue;
			}
			
    		//Check if bill amount is sufficient
			if (currentBill.notEnoughFunds()) {
				System.out.println("This bill has insufficient funds. Please try again.");
				currentBill = null;
				continue;
			}
			
    		checkingBillId = false;	
    		
		}
    	
    	//Once checks are complete, add entries to the Competition using the valid bill
    	//Uses overload 'addEntries' method from Competition object
    	String memberName = data.getMemberName(currentBill.getMemberId());
    	comp.addEntries(currentBill, memberName, keyboard);
    	
    	//Once new entries successfully created, update bill to 'used'
    	currentBill.setUsed(true);
    	data.updateBills(currentBill);
    	
	}
	
	/*
	 *  Logic to draw winners in the current competition
	 *  Called from main program loop, option 3
	 */
	private void drawWinners() {
		
		//Check if active competition. Must be active comp to draw winners
		if (!activeComp()) {
			System.out.println("There is no active competition. Please create one!");
			return;
		}
		
		//Check if active competition has any entries
		if (comp.hasEntries()) {
		
			//Call overload methods from active competition to draw and print winners
			comp.drawWinners();
			comp.printWinners();
			
			//Set active competition to inactive and add to completed competitions array
			comp.setActive(false);
			completedComps.add(comp);
			comp = null;
			
		} else {
			
			System.out.println("The current competition has no entries yet!");
			
		}

	}
	
	/*
	 *  Logic for printing summary report of all competitions within SimpleCompetitions object
	 *  Called from main program loop, option 4
	 */
	private void printSummaryReport() {
		
		//Check if any competitions to print
		if (completedComps.size() == 0 && !activeComp()) {
			System.out.println("No competition has been created yet!");
			return;
		}
		
		//Print summary report in specified format
		//Draw info from both active competition object and completed competitions from completedComps array 
		int noCompleted = completedComps.size();
		int noActive = 0;
		if (activeComp()) noActive = 1;
		
		System.out.println("----SUMMARY REPORT----");
		System.out.println("+Number of completed competitions: " + noCompleted);
		System.out.println("+Number of active competitions: " + noActive);
				
		for (Competition c:completedComps) {
			System.out.println();
			c.printCompetitionSummary();
		}
		
		if(activeComp()) {
			System.out.println();
			comp.printCompetitionSummary();
		}
		
	}
	
	/*
	 *  Helper logic for exit options.
	 *  Used at program exit to call save bill information function in DataProvider object
	 */
	private void saveBillFile() throws Exception {
			
		data.saveBillsToFile();
		
	}

	/*
	 *  Helper logic where user input in main menu not recognised
	 */
	private void notRecognised(String s) {
		
		boolean isNumber = true;
		
		try {
			Integer.parseInt(s);	
		} catch (Exception e) {
			isNumber = false;
		}
		
		if (isNumber) {
			System.out.println("Unsupported option. Please try again!");
		} else {
			System.out.println("A number is expected. Please try again.");
		}
		
	}
	
	/*
	 *  Helper logic to check if there is an active competition in the SimpleCompetitions object
	 *  @return boolean, true if there is an active competition.
	 */
	private boolean activeComp() {
			
		if(comp == null) return false;
		return true;
		
	}

	/*
	 *  Helper logic to display main menu options
	 */
	private void displayMenuOptions() {
		
		String menuOptions = "Please select an option. Type 5 to exit.\n"
			+ "1. Create a new competition\n"
			+ "2. Add new entries\n"
			+ "3. Draw winners\n"
			+ "4. Get a summary report\n"
			+ "5. Exit";
		
		System.out.println(menuOptions);
	
	}
    
}

