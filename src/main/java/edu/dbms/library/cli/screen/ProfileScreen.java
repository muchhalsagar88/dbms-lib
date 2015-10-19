package edu.dbms.library.cli.screen;

import edu.dbms.library.cli.route.Route;
import edu.dbms.library.cli.route.RouteConstant;

public class ProfileScreen extends BaseScreen {

	public ProfileScreen() {
		super();
		options.put(1, new Route("BACK"));
		options.put(2, new Route(RouteConstant.LOGOUT));
	}
	
	@Override
	public void execute() {
		System.out.println("Executed");
		
	}

}
