package edu.dbms.library.cli.screen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.ColumnResult;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.SqlResultSetMapping;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import dnl.utils.text.table.TextTable;
import edu.dbms.library.cli.Constant;
import edu.dbms.library.cli.route.Route;
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.db.DBUtils;
import edu.dbms.library.db.manager.LoginManager;
import edu.dbms.library.entity.AssetCheckout;
import edu.dbms.library.entity.Patron;
import edu.dbms.library.entity.reserve.CameraReservation;
import edu.dbms.library.entity.resource.Camera;
import edu.dbms.library.session.SessionUtils;
import edu.dbms.library.utils.DateUtils;

@SqlResultSetMapping(name="OngoingFines",
entities={
    @EntityResult(entityClass=edu.dbms.library.entity.resource.Camera.class, fields={
        @FieldResult(name="fine_duration", column="fine_duration"),
        @FieldResult(name="fine", column="fine")}
    ),
    @EntityResult(entityClass=edu.dbms.library.entity.resource.Camera.class, fields={
        @FieldResult(name="fine_duration", column="fine_duration"),
        @FieldResult(name="fine", column="fine")}
    )
},
columns={
    @ColumnResult(name="overdue_hrs")
}
)
public class CameraListScreen extends AssetListScreen<Camera> {

	public CameraListScreen() {
		options.put(3, new Route(RouteConstant.BACK));
	}

	@Override
	public void execute() {

		displayReserveCheckoutOpts();
		int option = readOptionNumber("Enter your choice", 1, 3);
		String nextScreenUrl = "";
		switch (option) {
		case 3:
			nextScreenUrl = RouteConstant.BACK;
			break;

		case 2:
			checkout();
			nextScreenUrl = RouteConstant.PATRON_BASE;
			break;

		case 1:
			reserveOption();
			System.out.println("The item has been reserved!!");
			nextScreenUrl = RouteConstant.PATRON_BASE;
			break;

		default:
			nextScreenUrl = RouteConstant.BACK;
			break;
		}

		// Redirect to Base screen after checkout notification
		BaseScreen nextScreen = getNextScreen(nextScreenUrl);
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
		System.out.println(reserve(c, selectedFriday));
	}

	/*
	 * TODO:	Write code to persist this asset against this patron
	 */
	@SuppressWarnings("unchecked")
	private void checkout() {

		if(LoginManager.isPatronAccountOnHold(SessionUtils.getPatronId())) {
			System.out.println("Your library privileges have been suspended. Please pay your dues to checkout assets.");
			return;
		}

		if(! DateUtils.isCameraCheckoutFriday()) {
			System.out.println("Cameras can only be checked out on Fridays between 9:00am to 12:00pm");
			return;
		}

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager em = emfactory.createEntityManager();

		Date currDate = DateUtils.formatToQueryDate(LocalDate.now());
		System.out.println(currDate.toString());

		Query query = em.createNativeQuery("SELECT res.* FROM Camera_Reservation res, Min_Reservation_View TEMP "
				+"WHERE res.camera_Id=TEMP.camera_id AND "
				+"res.reserve_Date=TEMP.min_reserve_date AND "
				+"res.reservation_status='ACTIVE' AND "
				+"TEMP.issue_date=?1  AND "
				+"res.patron_Id=?2 ", CameraReservation.class);
		query.setParameter(2, SessionUtils.getPatronId());
		query.setParameter(1, currDate);

		List<CameraReservation> currReservations = query.getResultList();
		if(currReservations == null || currReservations.size()==0) {
			System.out.println("No cameras can be checked out");
			return;
		}

		int cam;
		if(currReservations.size() > 1){
			System.out.println("You have "+currReservations.size()+" cameras to be checked out");
			System.out.println("Please enter your choice starting from 1");
			cam = readOptionNumber("Enter your choice", 1, currReservations.size());
		} else {
			cam = 1;
		}

		CameraReservation c = currReservations.get(cam-1);
		em.getTransaction().begin();
		c.setStatus("CLOSED");

		AssetCheckout checkout = new AssetCheckout();
		checkout.setAsset(c.getCamera());
		checkout.setPatron(c.getPatron());
		checkout.setCameraReservation(c);
		checkout.setIssueDate(new Date());
		checkout.setDueDate(DateUtils.getNextThursday(LocalDate.now()));
		em.persist(checkout);
		c.setAssetCheckout(checkout);

		em.getTransaction().commit();
		em.clear();em.close();

		// Make all the other requests as CANCELLED
		em = emfactory.createEntityManager();
		Query cancelReservationQuery = em.createQuery("SELECT c FROM CameraReservation c "
				+ "WHERE c.cameraReservationKey.cameraId=:cameraId AND "
				+ "c.cameraReservationKey.issueDate=:issueDate AND "
				+ "c.status='ACTIVE' ");
		cancelReservationQuery.setParameter("cameraId", c.getCamera().getId());
		cancelReservationQuery.setParameter("issueDate", currDate);

		List<CameraReservation> toCancel = cancelReservationQuery.getResultList();

		em.getTransaction().begin();
		for(CameraReservation res: toCancel)
			res.setStatus("CANCELLED");

		em.getTransaction().commit();
		em.clear();em.close();
		System.out.println("The item has been checked out!!");
	}

	private String reserve(Camera camera, String selectedFriday) {

		String message = null;
		Patron loggedInPatron = (Patron) DBUtils.findEntity(Patron.class, SessionUtils.getPatronId(), String.class);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(selectedFriday);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CameraReservation reservation = new CameraReservation();
		reservation.setReserveDate(new Date());
		reservation.getCameraReservationKey().setIssueDate(date);
		reservation.setCamera(camera);
		reservation.setPatron(loggedInPatron);

		// Check for Integrity constraint violation, and return message, saying camera already reserved
		try {
			DBUtils.persist(reservation);

			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
					DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
			EntityManager entitymanager = emfactory.createEntityManager();

			Query query = entitymanager.createQuery("SELECT COUNT(res.cameraReservationKey.cameraId) FROM CameraReservation res"
					+ " WHERE res.cameraReservationKey.cameraId=:cameraId"
					+ " AND res.cameraReservationKey.issueDate=:issueDate");
			query.setParameter("cameraId", camera.getId());
			query.setParameter("issueDate", date);
			long waitlistNumber = (long)query.getSingleResult();
			entitymanager.close();
			emfactory.close();

			StringBuilder builder = new StringBuilder();
			builder.append("The item has been reserved. ");

			if(waitlistNumber != 0)
				builder.append("Your waitlist number is ").append(waitlistNumber-1);
			message = builder.toString();
		} catch(Exception e) {
			e.printStackTrace();
			message = "You have already reserved the camera";
		}
		return message;
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

		int diff = DateTimeConstants.FRIDAY - currDay;
		if(diff < 0)
			diff += 7;

		LocalDate firstFriday = currDate.plusDays(diff);
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

	@SuppressWarnings("unchecked")
	public void displayAssets() {
		//assets = getAssetList(Camera.class);

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager em = emfactory.createEntityManager();

		Query query = em.createQuery("SELECT c FROM Camera c");
		assets = query.getResultList();

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
							{Constant.OPTION_ASSET_CHECKOUT},
							{Constant.OPTION_BACK}
							};
		TextTable tt = new TextTable(title, options);
		tt.setAddRowNumbering(true);
		tt.printTable();
	}

}
