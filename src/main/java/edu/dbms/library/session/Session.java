package edu.dbms.library.session;

/*
 * To be set only during Login and cleared out during logout
 */
public class Session {

	private String patronId;
	
	// True for Student &
	// False for Faculty
	private boolean isStudent;
	
	private String currentScreenRoute;

	public String getPatronId() {
		return patronId;
	}

	public boolean isStudent() {
		return isStudent;
	}

	public String getCurrentScreenRoute() {
		return currentScreenRoute;
	}

	public void setCurrentScreenRoute(String currentScreenRoute) {
		this.currentScreenRoute = currentScreenRoute;
	}
	
	Session(String patronId, boolean isStudent) {
		this.patronId = patronId;
		this.isStudent = isStudent;
	}
	
	Session(){}

}
