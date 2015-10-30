package edu.dbms.library.cli.screen;

import edu.dbms.library.cli.route.Route;
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.db.manager.LoginManager;
import edu.dbms.library.session.SessionUtils;
import edu.dbms.library.to.LoginTO;

public class LoginScreen extends BaseScreen {
	private final int bucketSize = 100000;
	private final int salt = 57643;
	
	public LoginScreen() {
		super();
		options.put(1, new Route(RouteConstant.PATRON_BASE));
	}
	
	@Override
	public void execute() {
		boolean validUser = false;
		do {
			String username = readInput("Enter Username");
<<<<<<< HEAD
			String password = null;
			try{
				password = new String(System.console().readPassword("Enter Password: "));
			}
			catch(Exception e){
				password = readInput("Enter Password");
			}
			password = LoginManager.getOraHash(password, bucketSize, salt);
//			String username = "arpit";
//			String password = "tyagi";
=======
			String password = readInput("Enter Password");
>>>>>>> 9070428c6021ab544c00bac22e74041fb8f6b4df
			validUser = validateCredentials(username, password);
			if(!validUser)
				System.out.println("Username password combination is incorrect");
			
		} while(!validUser); 
		// Hard coding input to 1 for Base Patron Screen
		BaseScreen nextScreen = getNextScreen(options.get(1).getRouteKey());
		nextScreen.execute();
	}

	@Override
	public void displayOptions() {
		// TODO Auto-generated method stub
		
	}
	
	public String readInput() {
		
		String option = inputScanner.nextLine();
		return option;
	}
	
	/*
	 * TODO: 	Validate the username and password and return patron_id
	 * 			and also boolean for isStudent
	 * 			Also set the Session object
	 */
	private boolean validateCredentials(String username, String password) {
		
		LoginTO loginTO = LoginManager.checkCredentials(username, password);
		if(loginTO == null)
			return false;
		
		SessionUtils.init(loginTO.getPatronId(), loginTO.isStudent());
		return true;
	}
	
	public static void main(String []args) {
		
		new LoginScreen().execute();
	}
	
}
