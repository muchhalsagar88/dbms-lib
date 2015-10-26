/**
 * 
 */
package edu.dbms.library.cli.screen;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;

import edu.dbms.library.cli.route.Route;
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.db.manager.RoomsManager;
import edu.dbms.library.entity.Library;
import edu.dbms.library.entity.resource.Room;

/**
 * @author ARPIT
 *
 */
public class RoomsScreen extends BaseScreen {

	/**
	 * 
	 */
	public RoomsScreen() {
		super();
		options.put(2, new Route(RouteConstant.BACK));
	}

	/* (non-Javadoc)
	 * @see edu.dbms.library.cli.screen.BaseScreen#execute()
	 */
	@Override
	public void execute() {
		//displayOptions(options, "Book a Room");
		
		
		String[][] options = {{"Book Rooms"},{"Check out Booked Room"},{"Back"},{"Logout"}};
		String[] title = {"Options"};
		displayOptions(options, title);
		int choice = readOptionNumber("Enter a choice", 1, 2);
		clearConsole();
		if(choice==1){
			List<Library> libs = RoomsManager.getLibraryList();
			String[][] lib_names = new String[libs.size()][5];
			int i=0;
			for (Iterator iterator = libs.iterator(); iterator.hasNext();) {
				Library library = (Library) iterator.next();
				lib_names[i][0] = library.getLibraryName();
				lib_names[i][1] = library.getLibraryAddress().getAddressLineOne();
				lib_names[i][2] = library.getLibraryAddress().getAddressLineTwo();
				lib_names[i][3] = library.getLibraryAddress().getCityName();
				lib_names[i][4] = ""+library.getLibraryAddress().getPinCode();
				i++;
			}
			
			String[] libTitles = {"Library","Address 1","Address 2","City","Post Code"};
			displayOptions(lib_names,libTitles);
			choice = readOptionNumber("Enter a choice", 1, libs.size());
			Library library = libs.get(choice-1);
			choice = readOptionNumber("Enter number of occupents", 1, 20);
			List<Room> rooms = RoomsManager.getAvailableRooms();
		}
		//if(options);
		//if(option==2)
		//nextScree
		//diplayAvailableRooms();
	}

	private void diplayAvailableRooms() {
		System.out.println();
	}

	@Override
	public void displayOptions() {
		// TODO Auto-generated method stub
		
	}

}
