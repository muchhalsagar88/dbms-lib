package edu.dbms.library.cli.screen;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import edu.dbms.library.cli.route.Route;
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.cli.route.RouteController;
import edu.dbms.library.session.SessionUtils;

public abstract class BaseScreen {
	
	protected Map<Integer, Route> options;
	
	public abstract void execute();
	
	public abstract void displayOptions();
	
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
