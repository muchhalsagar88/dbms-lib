package edu.dbms.library.cli.screen;

import java.util.List;

import dnl.utils.text.table.TextTable;
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.db.manager.NotificationManager;
import edu.dbms.library.entity.Notification;
import edu.dbms.library.session.SessionUtils;

public class PatronNotificationScreen extends BaseScreen {

	@Override
	public void execute() {

		displayOptions();
		int option = readOptionNumber("Enter 0 to go back", 0, 0);

		if(option == 0) {
			BaseScreen nextScreen = getNextScreen(RouteConstant.BACK);
			nextScreen.execute();
		}
	}

	@Override
	public void displayOptions() {

		List<Notification> notifications = NotificationManager.getUnreadNotifications(SessionUtils.getPatronId());

		String[] title = {"Notification"};

		Object[][] nots = new Object[notifications.size()][];
		int count = 0;
		for(Notification n: notifications)
			nots[count++] = n.toObjectArray();

		TextTable tt = new TextTable(title, nots);
		tt.setAddRowNumbering(true);
		tt.printTable();

	}

}
