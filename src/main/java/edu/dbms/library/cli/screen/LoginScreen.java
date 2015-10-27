package edu.dbms.library.cli.screen;

import edu.dbms.library.cli.route.Route;
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.db.manager.LoginManager;
import edu.dbms.library.session.SessionUtils;
import edu.dbms.library.to.LoginTO;

public class LoginScreen extends BaseScreen {

	public LoginScreen() {
		super();
		options.put(1, new Route(RouteConstant.PATRON_BASE));
	}
	
	@Override
	public void execute() {
		boolean validUser = false;
		do {
			String username = readInput("Enter Username");
			String password = readInput("Enter Password");
//			String username = "arpit";
//			String password = "tyagi";
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
	
	private void inputUsernameLabel() {
		System.out.println("Enter username: ");
	}
	
	private void inputPasswordLabel() {
		System.out.println("Enter password: ");
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
