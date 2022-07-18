/*
 * Author: Damian Curran
 */

import java.io.Serializable;

/**
 * Represents a member.
 * @author Name: Damian Curran
 */
public class Member implements Serializable {
	
	private String memberId;
	private String name;
	private String address;
	
	/**
    * Constructor of the Member class.
    * @param id The member id, String.
    * @param n The member name, String.
	* @param add The member address, String.
    */
	public Member(String id, String n, String add) {
		this.memberId = id;
		this.name = n;
		this.address = add;

	}
	
	/**
    * Get Member name.
    * @return Returns name String.
    */
	public String getName() {
		return this.name;
	}
	
	/**
    * Get Member id.
    * @return Returns id String.
    */
	public String getMemberId() {
		return this.memberId;
	}
	
}
