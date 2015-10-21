package edu.dbms.library.to;

public class LoginTO {

	private String patronId;
	
	private boolean isStudent;

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
	
	public LoginTO(String patronId) {
		this.patronId = patronId;
	}

	@Override
	public String toString() {
		
		return String.format("LoginTO["
				+ "patronId: %s"
				+ " isStudent: %b]", patronId, isStudent);
	}
	
	
}
