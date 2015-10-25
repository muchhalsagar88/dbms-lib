package edu.dbms.library.cli.screen;

import dnl.utils.text.table.TextTable;
import edu.dbms.library.cli.Constant;
import edu.dbms.library.cli.route.Route;
import edu.dbms.library.cli.route.RouteConstant;

/*
 *  TODO: 	Fire a query based on the PATRON table for details
 *  	  	Pass back some flag or parameter to differentiate between 
 *  	  	Student and Faculty
 */
public class ResourcePublications extends BaseScreen {

	public ResourcePublications() {
		super();
		options.put(1, new Route(RouteConstant.PUBLICATION_BOOKS));
		options.put(2, new Route(RouteConstant.PUBLICATIONS_JOURNALS));
		options.put(3, new Route(RouteConstant.PUBLICATIONS_CONFPAPERS));
//		getPatronDetails();
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

	@Override
	public void displayOptions() {
		
		System.out.println("Resource Publications:");
		System.out.println("-------------------------------------------");
		
		String[] title = {""};
		String[][] options = { 
							{Constant.PUBLICATION_BOOKS},
							{Constant.PUBLICATIONS_JOURNALS},
							{Constant.PUBLICATIONS_CONFPAPERS}
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

	// Implement the internal logic for this method
//	private Object getPatronDetails() {
		
//		return null;
//	}
	
}
