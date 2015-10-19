package edu.dbms.library.cli.screen;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import edu.dbms.library.cli.route.Route;

public abstract class BaseScreen {
	
	protected Map<Integer, Route> options;
	
	public abstract void execute();
	
	protected static Scanner inputScanner;
	
	protected BaseScreen() {
		if(inputScanner == null)
			inputScanner = new Scanner(System.in);
		options = new HashMap<Integer, Route>();
	}
	
}
