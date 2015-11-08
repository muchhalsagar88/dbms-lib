package edu.dbms.library.cli.route;

import java.util.HashMap;
import java.util.Map;

import edu.dbms.library.cli.screen.BaseScreen;
import edu.dbms.library.cli.screen.CameraListScreen;
import edu.dbms.library.cli.screen.LoginScreen;
import edu.dbms.library.cli.screen.LogoutScreen;
import edu.dbms.library.cli.screen.PatronBalanceScreen;
import edu.dbms.library.cli.screen.PatronResourcesScreen;
import edu.dbms.library.cli.screen.PatronScreen;
import edu.dbms.library.cli.screen.ProfileScreen;
import edu.dbms.library.cli.screen.ResourceBook;
import edu.dbms.library.cli.screen.ResourceConfPapers;
import edu.dbms.library.cli.screen.ResourceJournals;
import edu.dbms.library.cli.screen.ResourcePublications;
import edu.dbms.library.cli.screen.RoomsScreen;

public class RouteController {

	private static RouteController instance;
	
	private static Map<String, Class<?>> mapping;
	
	private RouteController() {
		populateRouteMapping();
	}
	
	private void populateRouteMapping() {
		mapping = new HashMap<String, Class<?>>();
		mapping.put(RouteConstant.LOGIN, LoginScreen.class);
		mapping.put(RouteConstant.PATRON_BASE, PatronScreen.class);
		mapping.put(RouteConstant.PATRON_PROFILE, ProfileScreen.class);
		mapping.put(RouteConstant.PATRON_RESOURCES, PatronResourcesScreen.class);
		mapping.put(RouteConstant.PATRON_RESOURCES_CAMERA, CameraListScreen.class);
		mapping.put(RouteConstant.LOGOUT, LogoutScreen.class);		
		
		mapping.put(RouteConstant.PATRON_RESOURCES_PUBLICATIONS, ResourcePublications.class);
		mapping.put(RouteConstant.PUBLICATION_BOOKS, ResourceBook.class);
		mapping.put(RouteConstant.PUBLICATIONS_JOURNALS, ResourceJournals.class);
		mapping.put(RouteConstant.PUBLICATIONS_CONFPAPERS, ResourceConfPapers.class);
		
		mapping.put(RouteConstant.PATRON_RESOURCES_ROOMS, RoomsScreen.class);
		
		mapping.put(RouteConstant.PATRON_BALANCE, PatronBalanceScreen.class);
	}
	
	public static synchronized RouteController getInstance() {
		if(instance == null)
			instance = new RouteController();
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public static Class<? extends BaseScreen> getScreen(String screenOption) {
	
		return (Class<? extends BaseScreen>) mapping.get(screenOption);
	}
	
}
