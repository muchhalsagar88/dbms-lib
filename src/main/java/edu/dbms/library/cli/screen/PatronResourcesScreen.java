package edu.dbms.library.cli.screen;

import dnl.utils.text.table.TextTable;
import edu.dbms.library.cli.Constant;
import edu.dbms.library.cli.route.Route;
import edu.dbms.library.cli.route.RouteConstant;

public class PatronResourcesScreen extends BaseScreen {

	public PatronResourcesScreen() {
		super();
		options.put(1, new Route(RouteConstant.PATRON_RESOURCES_PUBLICATIONS));
		options.put(2, new Route(RouteConstant.PATRON_RESOURCES_ROOMS));
		options.put(3, new Route(RouteConstant.PATRON_RESOURCES_CAMERA));
		options.put(4, new Route(RouteConstant.BACK));
		options.put(5, new Route(RouteConstant.LOGOUT));
	}
	
	@Override
	public void execute() {
		displayOptions();
		readInputLabel();
		Object o = readInput();
		while(!(o instanceof Integer)) {
			System.out.println("Incorrect input.");
			readInputLabel();
			o = readInput();
		}
		
		BaseScreen nextScreen = getNextScreen(options.get((Integer)o).getRouteKey());
		nextScreen.execute();

	}
	
	public void readInputLabel() {
		System.out.print("Enter your choice: ");
	}
	
	public Object readInput() {
		int option = inputScanner.nextInt();
		return option;
	}

	@Override
	public void displayOptions() {
		String[] title = {""};
		String[][] options = { 
							{Constant.OPTION_PUBLICATIONS},
							{Constant.OPTION_ROOMS},
							{Constant.OPTION_CAMERAS},
							{Constant.OPTION_BACK},
							{Constant.OPTION_LOGOUT}
							};
		TextTable tt = new TextTable(title, options);
		tt.setAddRowNumbering(true);
		tt.printTable();

	}

}
