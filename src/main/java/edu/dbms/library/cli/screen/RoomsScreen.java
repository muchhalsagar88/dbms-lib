/**
 * 
 */
package edu.dbms.library.cli.screen;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import edu.dbms.library.cli.route.Route;
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.db.DBUtils;
import edu.dbms.library.db.manager.RoomsManager;
import edu.dbms.library.entity.Library;
import edu.dbms.library.entity.RoomReserve;
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


		String[][] options = {{"Book Rooms"},{"Check-out/Cancel Booked Room"},{"Back"},{"Logout"}};
		String[] title = {"Options"};
		displayOptions(options, title);
		int choice = readOptionNumber("Enter a choice", 1, 4);
		clearConsole();
		switch(choice)
		{
		case 1:
			bookRoom();
			break;
		case 2:
			checkoutRoom();
			break;
		case 3:
			
			break;
		case 4:
			
			break;
		}
		//if(options);
		//if(option==2)
		//nextScree
		//diplayAvailableRooms();
	}

	private void checkoutRoom() {
		System.out.println("=============Checkout Room==========");
		int choice = -1;
		List<Object[]> rooms = RoomsManager.getBookedRooms();
		String[][] _rooms = new String[rooms.size()][6];
		int i=0;
		for (Iterator iterator = rooms.iterator(); iterator.hasNext();) {
			Object[] room = (Object[]) iterator.next();
			_rooms[i][0] = ""+room[4];
			_rooms[i][1] = ""+room[3];
			_rooms[i][2] = ""+room[2];
			_rooms[i][3] = ""+room[5];
			_rooms[i][4] = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(((Timestamp)room[6]).getTime()));
			_rooms[i][5] = ""+room[7];
			i++;
		}

		String[] RoomsTitles = {"Room No.","Floor","Capacity","Type","Start Time" ,"Available to Check-In"};
		displayOptions(_rooms,RoomsTitles);
		if(rooms.size()==0){
			choice = readOptionNumber("No rooms available for given criteria\nEnter 0 to go back or 1 to try other options", 0, 1);
			if(choice==1){
			}
		}
		else{
			int roomNo = readOptionNumber("Enter a choice (0 to go back)", 0, rooms.size());
			long reservationId = Long.parseLong(rooms.get(roomNo-1)[0].toString());
			if("Available".equalsIgnoreCase(rooms.get(roomNo-1)[7].toString())){
				choice = readOptionNumber("Press 1 to Checkout, 2 to Cancel or 0 to Go Back", 0, 2);
				if(choice==0){
					
				}
				else if(choice==1){
					boolean result = RoomsManager.checkOut(reservationId);
					if(result)
						System.out.println("Room checked out Successfully\nYou need to vacate room by : "+new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(((Timestamp)rooms.get(roomNo-1)[8]).getTime())));

				}
				else if(choice==2){
					boolean result = RoomsManager.cancel(reservationId);
					if(result)
						System.out.println("Cancelled Successfully...");
				}
					
			}
			else
			{
				choice = readOptionNumber("Press 1 to Cancel or 0 to Go Back", 0, 1);
				if(choice==0){

				}
				else if(choice==1){
					boolean result = RoomsManager.cancel(reservationId);
					if(result)
						System.out.println("Cancelled Successfully...");
				}
			}
		}

		
	}

	private void bookRoom() {

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
		//choice = readOptionNumber("Enter a choice (0 to go back)", 0, libs.size());
		int choice = 1;
		if(choice==0){
			BaseScreen nextScreen = getNextScreen(RouteConstant.BACK);
			nextScreen.execute();
			return;
		}
		Library library = libs.get(choice-1);
		//choice = readOptionNumber("Enter number of occupents", 1, 20);
		int occupents = 9;
		while(true){
			//String date = readInput("Enter Date in MM/DD/YYYY Format");
			//int startTime = readOptionNumber("Enter start time Hours(24 hours format)", 0, 23);
			//int endTime = readOptionNumber("Enter End time Hours(24 hours format)", startTime, 23);
			String date = "10/31/2015";
			int startTime = 12;
			int endTime = 14;
			if(endTime-startTime>3)
			{
				System.out.println("Max duration for room booking is 3 hours");
				continue;
			}
			Date startDate = DBUtils.validateDate(date+" "+(startTime<10?("0"+startTime):startTime)+":00", "MM/dd/yyyy HH:mm", true);
			if(startDate==null){
				System.out.println("Enter a valid start date(future date/time)");
				continue;
			}
			Date endDate = DBUtils.validateDate(date+" "+(startTime<10?("0"+endTime):endTime)+":00", "MM/dd/yyyy HH:mm", true);
			if(endDate==null){
				System.out.println("Enter a valid future date/time for end time");
				continue;
			}
			List<Object[]> rooms = RoomsManager.getAvailableRooms(occupents, library, startDate, endDate);
			String[][] _rooms = new String[rooms.size()][4];
			i=0;
			for (Iterator iterator = rooms.iterator(); iterator.hasNext();) {
				Object[] room = (Object[]) iterator.next();
				_rooms[i][0] = ""+room[3];
				_rooms[i][1] = ""+room[2];
				_rooms[i][2] = ""+room[1];
				_rooms[i][3] = ""+room[4];
				i++;
			}

			String[] RoomsTitles = {"Room No.","Floor","Capacity","Type"};
			displayOptions(_rooms,RoomsTitles);
			if(rooms.size()==0){
				choice = readOptionNumber("No rooms available for given criteria\nEnter 0 to go back or 1 to try other options", 0, 1);
				if(choice==1){
					continue;
				}
			}
			else
				choice = readOptionNumber("Enter a choice (0 to go back)", 0, rooms.size());
			if(choice==0){
				BaseScreen nextScreen = getNextScreen(RouteConstant.BACK);
				nextScreen.execute();
				return;
			}
			Room room = (Room) DBUtils.findEntity(Room.class, rooms.get(choice-1)[0], String.class);
			boolean status = RoomsManager.reserve(room, startDate, endDate);
			if(status){
				choice = readOptionNumber("Successfully booked Room#"+room.getRoomNo()+" on "+date+" from "+startTime+":00 Hrs to "+endTime+":00 Hrs\nDo not forget to check in within 15 minutes of start time\nEnter 0 to go back", 0, 0);
				BaseScreen nextScreen = getNextScreen(RouteConstant.BACK);
				nextScreen.execute();
				return;
			}
			break;
		}			
	}

	@Override
	public void displayOptions() {
		// TODO Auto-generated method stub

	}

}
