/*
 * Author: Damian Curran
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * DataProvider object used to open files and provide data to main program.
 * @author Name: Damian Curran
 */
public class DataProvider implements Serializable {

	private ArrayList<Member> members;
	private ArrayList<Bill> bills;	
	private String billFileName;
			
	/**
    * Constructor of the DataProvider class.
    * @param memberFile The filename of the member file, String.
    * @param billFile The filename of the bill file, String.
    * @throws DataAccessException Exception thrown when file cannot be accessed.
    * @throws DataFormatException Exception thrown when file contents are improperly formatted and cannot be read.
    */
	public DataProvider (String memberFile, String billFile) throws DataAccessException, DataFormatException {
		
		this.members = new ArrayList<Member>();
		this.bills = new ArrayList<Bill>();
		this.billFileName = billFile;
		
		//Initialise empty array list of each line of the file.
		ArrayList<String> fileData = null;
		
		//Try open the member file and create member list from the file data.
		fileData = readIn(memberFile, "member");
		createMemberList(fileData);	
		
		//Try open the bill file and create bill list from the file data.
		fileData = readIn(billFile, "bill");
		createBillList(fileData);
		
	}
		
	/**
    * Get number of bills in bill list file.
    * @return Returns id integer.
    */
	public int getBillSize() {
		return bills.size();
	}
	
	/**
	* Get bill object based on billId
	* @param billId String billId.
	* @return Returns Bill object for the given billId
	*/
	public Bill getBill(String billId) {
	
		int size = bills.size();
	
		for (int i = 0; i < size; i ++) {
			String id = bills.get(i).getBillId();
			if (id.equals(billId)) return bills.get(i);
		}
	
		return bills.get(0); //Default
	
	}

	/**
	* Get index of the bill given a billId
	* @param billId String billId.
	* @return Returns integer of the index for that bill.
	*/
	public int getBillIndex(String billId) {
	
		int size = bills.size();
	
		for (int i = 0; i < size; i ++) {
			String id = bills.get(i).getBillId();
			if (id.equals(billId)) return i;
		}
	
		return -1;
	}

	/**
    * Get member name from member list, for given member Id.
    * @param memberId String member id.
    * @return Returns member name String for given member Id.
    */
	public String getMemberName(String memberId) {
		
		int size = members.size();
		
		for (int i = 0; i < size; i ++) {
			String currentId = members.get(i).getMemberId();
			if (currentId.equals(memberId)) return members.get(i).getName();
		}

		return "";//Default
		
	}
	
	
	/**
    * Check if a billId exists in the data.
    * Used during bill checks prior to adding new entries.
    * @param billId String of bill to check.
    * @return Returns boolean, true if the bill exists in the data.
    */
	public boolean billExists(String billId) {
		
		int size = bills.size();
		
		for (int i = 0; i < size; i ++) {
			String id = bills.get(i).getBillId();
			if (id.equals(billId)) return true;
		}
		
		return false;
	}

	/**
    * Check if a member is valid.
    * Checks if a member id from a bill matches a member Id in the member list
    * Used during bill checks prior to adding new entries.
    * @param memberId String of member to check.
    * @return Returns boolean, true if the member id on the member list.
    */
	public boolean validMember(String memberId) {
		
		int size = members.size();
		
		for (int i = 0; i < size; i ++) {
			
			String id = members.get(i).getMemberId();
			if (id.equals(memberId)) return true;
		}
		
		return false;

	}
	
	/**
    * Update bills into DataProvider.
    * @param b Bill object to be updated.
    */
	public void updateBills(Bill b) {

		int index = getBillIndex(b.getBillId());
		bills.set(index, b);

	}
	
	/**
    * Write bills object back to bills text file.
    * @throws Exception Exception thrown if problem encountered during file write.
    */
	public void saveBillsToFile() throws Exception {

        PrintWriter outputStream = null;
        outputStream = new PrintWriter(new FileOutputStream(billFileName));

		for(int i = 0; i < this.getBillSize(); i++) {
			
			Bill b = bills.get(i);
			
			String billId = b.getBillId();
			String memberId = b.getMemberId();
			String amount = b.getAmountStringNoFormat();
			String used = b.getUsedString();
			outputStream.print(billId + "," + memberId + "," + amount + "," + used +"\n");

		}
		
		outputStream.close();
		
	}

	/*
	 *  Helper method for constructor.
	 *  To read file into ArrayList (called from Constructor)
	 *  @param filename Filename string to be read in
	 *  @param type Type string indicating if bill or member, for purposes of exception message.
	 *  @return Returns string array list of file data as read
	 *  @throws DataAccessException if file cannot be accessed
	 */
	private ArrayList<String> readIn(String filename, String type)
		throws DataAccessException {
		
		ArrayList<String> fileData = new ArrayList<String>();		
		
		try {
	
			File file = new File(filename);
			Scanner in = new Scanner(file);
			
			while (in.hasNextLine()) {
				fileData.add(in.nextLine());
			}
			
			in.close();
	
		} catch (Exception e) {throw new DataAccessException(type);}
					
		return fileData;
		
	}

	/*
	 *  Helper method for constructor to create member list from file data.
	 *  @param fileData Array list of strings from the raw file data
	 *  @throws DataFormatException if raw data is not properly formatted.
	 */
	private void createMemberList(ArrayList<String> fileData)
		throws DataFormatException {
				
		//Taking each line of the ArrayList String (previously read from the file):
		int size = fileData.size();
		
		try {
		
			for (int i = 0; i < size; i ++) {
				
				String[] nextMember = fileData.get(i).split(",");
				String id = nextMember[0];
				String n = nextMember[1];
				String add = nextMember[2];
				
				Member m = new Member (id, n, add);
				members.add(m);
							
			}
			
		} catch (Exception e) {throw new DataFormatException("member");}
		
	}

	/*
	 *  Helper method for constructor to create bill list from file data.
	 *  @param fileData Array list of strings from the raw file data
	 *  @throws DataFormatException if raw data is not properly formatted.
	 */
	private void createBillList(ArrayList<String> fileData)
		throws DataFormatException {
				
		//Taking each line of the ArrayList String (previously read from the file):
		int size = fileData.size();
		
		try {
		
			for (int i = 0; i < size; i ++) {
				
				String[] nextBill = fileData.get(i).split(",");
				String bid = nextBill[0];
				String mid = nextBill[1];
				float amount = Float.parseFloat(nextBill[2]);
				boolean used = Boolean.parseBoolean(nextBill[3]);
				
				Bill b = new Bill (bid, mid, amount, used);
				bills.add(b);
				
			}
			
		} catch (Exception e) {throw new DataFormatException("bill");}
		
	}
	
}
