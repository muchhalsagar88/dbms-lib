package edu.dbms.library.to;

public class LoginTO {

	private String patronId;

	private boolean isStudent;

	private boolean isAccountOnHold;

	public String getPatronId() {
		return patronId;
	}

	public void setPatronId(String patronId) {
		this.patronId = patronId;
	}

	public boolean isStudent() {
		return isStudent;
	}

	public void setStudent(boolean isStudent) {
		this.isStudent = isStudent;
	}

	public boolean isAccountOnHold() {
		return isAccountOnHold;
	}

	public void setAccountOnHold(boolean isAccountOnHold) {
		this.isAccountOnHold = isAccountOnHold;
	}

	public LoginTO(String patronId) {
		this.patronId = patronId;
	}

	@Override
	public String toString() {

		return String.format("LoginTO["
				+ "patronId: %s"
				+ " isStudent: %b"
				+ " isAccountOnHold: %b]", patronId, isStudent, isAccountOnHold);
	}


}
