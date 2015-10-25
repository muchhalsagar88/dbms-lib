package edu.dbms.library.to;

public class UserTO {
	private String firstName;
	
	private String emailAddress;
	
	private int waitlistNumber;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public int getWaitlistNumber() {
		return waitlistNumber;
	}

	public void setWaitlistNumber(int waitlistNumber) {
		this.waitlistNumber = waitlistNumber;
	}

	public UserTO(String firstName, String email, int waitlistNumber) {
		this.firstName = firstName;
		this.emailAddress = email;
		this.waitlistNumber = waitlistNumber;
	}

	@Override
	public String toString() {
		return String.format("UserTO[firstName: %s, emailAddress: %s, waitlist: %d]", 
				this.firstName, this.emailAddress, this.waitlistNumber);
	}
	
}