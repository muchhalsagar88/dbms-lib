package edu.dbms.library.session;

public class SessionUtils {

	private static Session instance;
	
	public static String getPatronId() {
		return instance.getPatronId();
	}
	
	public static boolean isStudent() {
		return instance.isStudent();
	}
	
	public static void init(String patronId, boolean isStudent) {
		instance = new Session(patronId, isStudent);
	}
	
	public static void updateCurrentRoute(String route) {
		instance.setCurrentScreenRoute(route);
	}
	
	public static String getCurrentRoute() {
		return instance.getCurrentScreenRoute();
	}
	
	public static void kill() {
		instance = null;
	}
	
	public static boolean checkForNullSession() {
		
		if(instance == null)
			return true;
		return false;
	}
}
