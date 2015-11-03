package edu.dbms.library.to;

public class UserTO {
	private String firstName;

	private String emailAddress;

	private int waitlistNumber;

	// To be used for user with waitlist = 0 only
	private boolean camAvailable;

	private String patronId;

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

	public boolean isCamAvailable() {
		return camAvailable;
	}

	public void setCamAvailable(boolean camAvailable) {
		this.camAvailable = camAvailable;
	}

	public String getPatronId() {
		return patronId;
	}

	public void setPatronId(String patronId) {
		this.patronId = patronId;
	}

	public UserTO(String firstName, String email, int waitlistNumber, String patronId) {
		this.firstName = firstName;
		this.emailAddress = email;
		this.waitlistNumber = waitlistNumber;
		this.patronId = patronId;
	}

	@Override
	public String toString() {
		return String.format("UserTO[firstName: %s, emailAddress: %s, waitlist: %d]",
				this.firstName, this.emailAddress, this.waitlistNumber);
	}

}