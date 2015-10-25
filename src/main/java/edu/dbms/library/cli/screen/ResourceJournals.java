package edu.dbms.library.cli.screen;

import java.util.ArrayList;
import java.util.List;

import dnl.utils.text.table.TextTable;
import edu.dbms.library.cli.Constant;
import edu.dbms.library.cli.route.Route;
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.resource.Book;
import edu.dbms.library.entity.resource.Journal;

public class ResourceJournals extends BaseScreen {

	public ResourceJournals() {
		super();
//		options.put(1, new Route(RouteConstant.PATRON_PROFILE));
//		options.put(2, new Route(RouteConstant.PATRON_RESOURCES));
//		options.put(3, new Route(RouteConstant.PATRON_CHECKED_OUT));
//		options.put(4, new Route(RouteConstant.PATRON_RES_REQUEST));
//		options.put(5, new Route(RouteConstant.PATRON_NOTIFICATIONS));
//		options.put(6, new Route(RouteConstant.PATRON_BALANCE));
//		options.put(7, new Route(RouteConstant.LOGOUT));
	}

	List<Journal> journals = new ArrayList<Journal>();
	@Override
	public void execute() {
		displayJournals();
		displayOptions();
		Object o = readInput();
		while(!(o instanceof Integer)) {
			System.out.println("Incorrect input.");
			readInputLabel();
			o = readInput();
		}

		if((int)o == 0){
			//do nothing - goto patron screen.
		}
		else{
			// call checkout methods
//			checkout();
			
			// update the approproate tables with respect to availibility. Assign return dates to items
			// checkout possible only for available items
			// if resource not available..put the request in waitlist
			// checkout rnew possible only if waitlist is empty -- update the due dates nd relevant data.
			System.out.println("The item has been checked out!!");
		}
			

		// Redirect to Base screen after checkout notification
		BaseScreen nextScreen = getNextScreen(RouteConstant.PATRON_BASE);
		nextScreen.execute();
				
	}

	public void readInputLabel() {
		System.out.print("Enter your choice: ");
	}
	
	public Object readInput() {
		/*
		 * Buggy!! Returns incorrect input without any input
		 * 
		 * String option = inputScanner.nextLine();
		try {
			int correct = Integer.parseInt(option);
			return correct;
		} catch (Exception e) {
			return option;
		}*/
		int option = inputScanner.nextInt();
		return option;
	}
	
	@Override
	public void displayOptions() {
		
		String[] title = {""};
		String[][] options = { 
							{Constant.OPTION_ASSET_CHECKOUT},
							{Constant.OPTION_EXIT},
							{"0 to exit the menu."},
							{"Enter the book number to checkout the book"}
							};
		TextTable tt = new TextTable(title, options);
		tt.setAddRowNumbering(true);
		tt.printTable();
	}
	
	/*public static void main(String []args) {
		SessionUtils.init("patron_id", true);
		SessionUtils.updateCurrentRoute("/patron");
		new PatronScreen().execute();
	}*/
	
	public void displayJournals() {
		// opt1: Display only those books tht a patron can checkout. 
		// if the patron has been issued some books. remove thos ISBN number wala books from the display list..
		// Display conditions for REserved books??
		journals = getJournalList(); // publisher is not joined with books yet.
		String[] title = {"ISSN", "TITLE",  "AUTHOR(S)", "PUB_YEAR"};
		
		Object[][] jrnls = new Object[journals.size()][]; 
		int count = 0;
		for(Journal j: journals)
			jrnls[count++] = j.toObjectArray();
			
		TextTable tt = new TextTable(title, jrnls);
		tt.setAddRowNumbering(true);
		tt.printTable();
	}
	
	
protected List<Journal> getJournalList() {
		
		String query = "SELECT j FROM "+Journal.class.getName()+" j";
//				+" WHERE b.id NOT IN"
//				+"(SELECT a.asset.id FROM AssetCheckout a WHERE a.dueDate IS NULL)";
		
		return (List<Journal>)DBUtils.fetchAllEntities(query);
	}


public void checkout(int bookNo) {
	Journal journal = journals.get(bookNo-1);
	//check of the book is already issues?
		// if issued by the same patron - check the waitlist for renew condition
		// if issued by other user -- add the entry to waitlist
	// if the book is not issued
		// entry int asset_checkout table.
	
	// checkout Rules:
	//	1. Reserved books can be checked out for maximum of 4hrs and by only students of the class for which the book is reserved.
	//	2. Electronic publications Have	no checkout duration.
	//	3. Journals and Conference Proceedings can be checked out for a period of 12 hours 
	//	4. Every other book Students: 2 weeks // faculty : 1 month.
}
}
