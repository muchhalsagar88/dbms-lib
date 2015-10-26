package edu.dbms.library.cli.screen;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import dnl.utils.text.table.TextTable;
import edu.dbms.library.cli.route.Route;
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.cli.route.RouteController;
import edu.dbms.library.session.SessionUtils;

public abstract class BaseScreen {

	protected Map<Integer, Route> options;

	protected static final int CONSOLE_WIDTH = 79;

	public abstract void execute();

	public abstract void displayOptions();

	public void displayOptions(Object[][] options, String[] title, boolean displayRowNumbers){

		TextTable tt = new TextTable(title, options);
		tt.setAddRowNumbering(displayRowNumbers);
		tt.printTable();
	}

	public void displayOptions(Object[][] options){
		displayOptions(options,null,true);
	}

	public void displayOptions(Object[][] options, String[] title){
		displayOptions(options,title,true);
	}

	public void displayOptions(Object[][] options, boolean displayRowNumbers){
		displayOptions(options,null,displayRowNumbers);;
	}

	public String readInput(String prompt){
		System.out.print(prompt+": ");
		while(true){
			String s = inputScanner.nextLine();
			if(s.length()>0)
				return s;
		}
	}

	public final static void clearConsole()
	{
		try
		{
			final String os = System.getProperty("os.name");

			if (os.toLowerCase().contains("windows"))
			{
				Runtime.getRuntime().exec("cls");
			}
			else
			{
				Runtime.getRuntime().exec("clear");
			}
		}
		catch (final Exception e)
		{
			//  Handle any exceptions.
		}
	}

	public int readOptionNumber(String prompt){
		return readOptionNumber(prompt, -9999999, 999999);
	}

	public int readOptionNumber(String prompt, int min, int max){
		int choice = -1;
		while(true){
			String _choice = readInput(prompt);
			try{
				choice = Integer.parseInt(_choice);
				if(choice<min||choice>max)
					throw new NumberFormatException();
			}
			catch(NumberFormatException e){
				if(min==max)
					System.out.println("Invalid Choice. Please enter a valid choice.\nValid input is Number "+max+".");
				else if(min==max-1)
					System.out.println("Invalid Choice. Please enter a valid choice.\nValid input is Number "+min+" or "+max+".");
				else
					System.out.println("Invalid Choice. Please enter a valid choice.\nValid input is a Number between "+min+" and "+max+".");
				continue;
			}
			break;
		}
		return choice;
	}


	protected static Scanner inputScanner;

	protected BaseScreen() {
		if(inputScanner == null)
			inputScanner = new Scanner(System.in);
		options = new HashMap<Integer, Route>();
	}

	@SuppressWarnings({ "static-access", "rawtypes" })
	protected BaseScreen getNextScreen(String routeKey) {

		// Handle Back option differently
		if(routeKey.equalsIgnoreCase(RouteConstant.BACK)) {
			// point the routeKey to the previous screen from session
			routeKey = SessionUtils.getCurrentRoute();
			routeKey = routeKey.substring(0, routeKey.lastIndexOf('/'));
		}
		SessionUtils.updateCurrentRoute(routeKey);
		Class t = RouteController.getInstance().getScreen(routeKey);
		BaseScreen nextScreen = null;
		try {
			nextScreen = (BaseScreen)t.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}
		return nextScreen;
	}

	protected BaseScreen handleBackOption() {

		return null;
	}

}
