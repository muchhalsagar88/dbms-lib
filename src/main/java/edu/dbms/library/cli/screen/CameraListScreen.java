package edu.dbms.library.cli.screen;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import dnl.utils.text.table.TextTable;
import edu.dbms.library.cli.Constant;
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.AssetCheckout;
import edu.dbms.library.entity.Patron;
import edu.dbms.library.entity.resource.Camera;
import edu.dbms.library.session.SessionUtils;

public class CameraListScreen extends AssetListScreen<Camera> {

	@Override
	public void execute() {
		
		displayReserveCheckoutOpts();
		Object o = readInput();
		while(!(o instanceof Integer)) {
			System.out.println("Incorrect input.");
			readInputLabel();
			o = readInput();
		}
		
		if((int)o == 1){
			
			reserveOption();
			System.out.println("The item has been reserved!!");
		}
		else if((int)o == 2){
			// call checkout methods
			checkout();
			System.out.println("The item has been checked out!!");
		}	
		
		// Redirect to Base screen after checkout notification
		BaseScreen nextScreen = getNextScreen(RouteConstant.PATRON_BASE);
		nextScreen.execute();
	}
	
	private String mapFridayToOption(List<String> fridays, int option) {
		
		if(! (option > fridays.size()+1))
			return fridays.get(option-1);
		return null;
	}
	
	private void reserveOption() {
		
		List<String> fridays = displayFridays();
		readInputLabel();
		Object input = readInput();
		while(!(input instanceof Integer)) {
			System.out.println("Incorrect input.");
			readInputLabel();
			input = readInput();
		}
		String selectedFriday = mapFridayToOption(fridays, (Integer)input);
		
		displayAssets();
		readInputLabel();
		input = readInput();
		while(!(input instanceof Integer)) {
			System.out.println("Incorrect input.");
			readInputLabel();
			input = readInput();
		}
		Camera c = mapAssetToInputOption((int) input);
		reserve(c, selectedFriday);
	}
	
	/*
	 * TODO:	Write code to persist this asset against this patron
	 */
	private void checkout() {
		
		System.out.println("Logic to checkout");
		// Check camera reservation table for patron id and time
		// If time is still valid and patron has reserved item then let the man checkout  
		/*Patron loggedInPatron = (Patron) DBUtils.findEntity(Patron.class, SessionUtils.getPatronId(), String.class);
		
		AssetCheckout assetCheckout = new AssetCheckout();
		// TODO: find camera from reserve list
		Camera c = new Camera();
		assetCheckout.setAsset(c);
		assetCheckout.setPatron(loggedInPatron);
		assetCheckout.setIssueDate(new Date());*/
	}
	
	private void reserve(Camera camera, String selectedFriday) {
		System.out.println("Login to reserve");
	}
	
	public void readInputLabel() {
		System.out.print("Enter your choice: ");
	}
	
	public Object readInput() {
		int option = inputScanner.nextInt();
		return option;
	}
	
	public List<String> displayFridays() {
		
		List<String> fridays = new LinkedList<String>();
		LocalDate currDate = LocalDate.now();
		int currDay = currDate.getDayOfWeek();
		
		LocalDate firstFriday = currDate.plusDays(Math.abs(DateTimeConstants.FRIDAY - currDay));
		fridays.add(firstFriday.toString());
		for(int i=1; i<=4; ++i)
			fridays.add(firstFriday.plusDays(7*i).toString());
		
		String[] title = {"Available Fridays"};
		String[][] options = new String[fridays.size()][]; 
		int count = 0;
		for(String friday: fridays){
			String[] temp = new String[1];
			temp[0] = friday;
			options[count++] = temp;
		}
		
		TextTable tt = new TextTable(title, options);
		tt.setAddRowNumbering(true);
		tt.printTable();
		
		return fridays;
	}
	
	public void displayAssets() {
		assets = getAssetList(Camera.class);
		String[] title = {"Maker", "Model", "Lens Detail", "Memory Available"};
		
		Object[][] cams = new Object[assets.size()][]; 
		int count = 0;
		for(Camera c: assets)
			cams[count++] = c.toObjectArray();
			
		TextTable tt = new TextTable(title, cams);
		tt.setAddRowNumbering(true);
		tt.printTable();
	}
	
	public void displayReserveCheckoutOpts() {
		
		String[] title = {""};
		String[][] options = { 
							{Constant.OPTION_ASSET_RESERVE},
							{Constant.OPTION_ASSET_CHECKOUT}//,
							//{Constant.OPTION_BACK}
							};
		TextTable tt = new TextTable(title, options);
		tt.setAddRowNumbering(true);
		tt.printTable();
	}
	
}
