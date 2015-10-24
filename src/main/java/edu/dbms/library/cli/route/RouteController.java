package edu.dbms.library.cli.route;

import java.util.HashMap;
import java.util.Map;

import edu.dbms.library.cli.screen.BaseScreen;
import edu.dbms.library.cli.screen.CameraListScreen;
import edu.dbms.library.cli.screen.LoginScreen;
import edu.dbms.library.cli.screen.LogoutScreen;
import edu.dbms.library.cli.screen.PatronResourcesScreen;
import edu.dbms.library.cli.screen.PatronScreen;
import edu.dbms.library.cli.screen.ProfileScreen;

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
