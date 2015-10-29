package edu.dbms.library.cli.screen;

import dnl.utils.text.table.TextTable;
import edu.dbms.library.cli.Constant;
import edu.dbms.library.cli.route.Route;
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.session.SessionUtils;

public class LogoutScreen extends BaseScreen {

	public LogoutScreen() {
		super();
		options.put(1, new Route(RouteConstant.LOGIN));
		options.put(2, new Route(RouteConstant.EXIT));
	}
	
	@Override
	public void execute() {
		SessionUtils.kill();
		displayOptions();
		int o = readOptionNumber("Enter Choice", 1, 2);
		// Check for EXIT option, will appear only once in the application
		if(o == 2) {
			System.exit(0);
		}
		BaseScreen nextScreen = getNextScreen(options.get((Integer)o).getRouteKey());
		nextScreen.execute();
	}

	@Override
	public void displayOptions() {
		System.out.println("You have logged out of the system.");
		
		String[] title = {""};
		String[][] options = { 
							{Constant.OPTION_LOGIN},
							{Constant.OPTION_EXIT}
							};
		TextTable tt = new TextTable(title, options);
		tt.setAddRowNumbering(true);
		tt.printTable();

	}

	public void readInputLabel() {
		System.out.print("Enter your choice: ");
	}
	
	public Object readInput() {
		int option = inputScanner.nextInt();
		return option;
	}
}
