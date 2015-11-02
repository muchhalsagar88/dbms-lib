package edu.dbms.library.cli.screen;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import dnl.utils.text.table.TextTable;
import edu.dbms.library.cli.Constant;
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.Patron;
import edu.dbms.library.session.SessionUtils;

public class CheckedOutResourcesScreen extends BaseScreen{

	String viewCheckedOutCamerasString = "SELECT A.ASSET_ID, AC.ID AS CHECKOUT_ID, AC.ISSUE_DATE, AC.DUE_DATE, CD.LENSDETAIL, CD.MEMORYAVAILABLE, CD.MAKER, CD.MODEL"
			+ " FROM ASSET A, ASSET_CHECKOUT AC, CAMERA C, CAMERA_DETAIL CD, ASSET_TYPE AT"
			+ " WHERE AC.PATRON_ID = ?"
			+ " AND AC.RETURN_DATE IS NULL"
			+ " AND A.ASSET_ID = AC.ASSET_ASSET_ID"
			+ " AND AT.CATEGORY = 'Device'"
			+ " AND AT.SUB_CATEGORY = 'Camera'"
			+ " AND A.ASSET_TYPE = AT.ASSETTYPEID"
			+ " AND C.CAMERA_ID = A.ASSET_ID"
			+ " AND CD.CAMERA_DETAIL_ID = C.CAMERA_DETAIL_ID";
	
	String viewRenewBookString = "SELECT  A1.ASSET_ID ,  B1.ISBN_NUMBER, BD1.TITLE, BD1.EDITION, BD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT , PB1.NAME"
			+ " ,rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS, "
			+ " (CASE WHEN ( ASC1.RETURN_DATE is NULL  AND ASC1.ASSET_ASSET_ID = A1.ASSET_ID ) THEN 'ISSUED' ELSE 'AVLBLE' END) as STATUS, ASC1.ID"
			+ " from Book b1, Publication p1, BOOK_DETAIL bd1, Asset a1, Publisher pb1, Author auth1, BOOK_AUTHOR BA1 , ASSET_CHECKOUT asc1"
			+ " WHERE B1.BOOK_ID = a1.ASSET_ID"
			+ " AND a1.ASSET_ID = P1.PUBLICATION_ID"
			+ " AND ( ASC1.ASSET_ASSET_ID(+) = A1.ASSET_ID" 
			+ " )"
			+ " AND B1.ISBN_NUMBER = BD1.ISBN_NUMBER"
			+ " AND BD1.PUBLISHER_ID = PB1.ID"
			+ " AND BA1.BOOK_ID = B1.ISBN_NUMBER"
			+ " AND AUTH1.ID = BA1.AUTHOR_ID"
			+ " AND  (ASC1.ASSET_ASSET_ID = A1.ASSET_ID"
			+ " AND ASC1.PATRON_ID = ?"
			+ " AND ASC1.RETURN_DATE is NULL	 )"
			+ "GROUP BY A1.ASSET_ID ,  B1.ISBN_NUMBER, BD1.TITLE, BD1.EDITION, BD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT , PB1.NAME,  ASC1.ID ,ASC1.RETURN_DATE, ASC1.ISSUE_DATE, ASC1.ASSET_ASSET_ID ";

	String viewRenewConfProcString = "SELECT unique  A1.ASSET_ID , CONF1.CONF_PROC_ID, CONF1.CONF_NUM, CPD1.TITLE,CPD1.CONFERENCENAME,CPD1.PUB_YEAR, P1.PUBLICATIONFORMAT"     
			+ " ,rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS,  "
			+ " (CASE WHEN ( ASC1.RETURN_DATE is NULL  AND ASC1.ASSET_ASSET_ID = A1.ASSET_ID ) THEN 'ISSUED' ELSE 'AVLBLE' END) as STATUS, ASC1.ID"  
			+ " from CONF_PROCEEDING conf1, Publication p1,   CONFERENCE_PROCEEDING_DETAIL cpd1 , Asset a1 ,  Author auth1, CONF_PROC_AUTHOR cpa1 , ASSET_CHECKOUT asc1"  
			+ " WHERE CONF1.CONF_PROC_ID = a1.ASSET_ID" 
			+ " AND a1.ASSET_ID = P1.PUBLICATION_ID"  
			+ " AND ( ASC1.ASSET_ASSET_ID(+) = A1.ASSET_ID"
			+ "  )"
			+ " AND CONF1.CONF_NUM = CPD1.CONF_NUM"
			+ " AND CPA1.CONF_NUM = CONF1.CONF_NUM"
			+ " AND AUTH1.ID = CPA1.AUTHOR_ID"
			+ " AND  (ASC1.ASSET_ASSET_ID = A1.ASSET_ID"
			+ " AND ASC1.PATRON_ID = ?"	
			+ " AND ASC1.RETURN_DATE is NULL)"
			+ " GROUP BY A1.ASSET_ID , CONF1.CONF_PROC_ID, CONF1.CONF_NUM, CPD1.TITLE,CPD1.CONFERENCENAME,CPD1.PUB_YEAR, P1.PUBLICATIONFORMAT, ASC1.ID,ASC1.RETURN_DATE, ASC1.ISSUE_DATE, ASC1.ASSET_ASSET_ID ";
	
	String viewRenewJournalString = "SELECT A1.ASSET_ID , J1.ISSN_NUMBER ,  JD1.TITLE , JD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT"
			+ " ,rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS,  "
			+ " (CASE WHEN ( ASC1.RETURN_DATE is NULL  AND ASC1.ASSET_ASSET_ID = A1.ASSET_ID ) THEN 'ISSUED' ELSE 'AVLBLE' END) as STATUS, ASC1.ID"
			+ " from JOURNAL j1, Publication p1,   JOURNAL_DETAIL jd1 , Asset a1 ,  Author auth1, JOURNAL_AUTHOR ja1 , ASSET_CHECKOUT asc1"
			+ "  WHERE J1.JOURNAL_ID = a1.ASSET_ID"
			+ " AND a1.ASSET_ID = P1.PUBLICATION_ID"
			+ " AND ( ASC1.ASSET_ASSET_ID(+) = A1.ASSET_ID"
			+ "  )"
			+ " AND J1.ISSN_NUMBER = JD1.ISSN_NUMBER"
			+ " AND JA1.JOURNAL_ID = J1.ISSN_NUMBER"
			+ " AND AUTH1.ID = JA1.AUTHOR_ID"
			+ " AND  (ASC1.ASSET_ASSET_ID =   A1.ASSET_ID"
			+ " AND ASC1.PATRON_ID = ?"
			+ " AND ASC1.RETURN_DATE is NULL	 )"
			+ " GROUP BY A1.ASSET_ID , J1.ISSN_NUMBER ,  JD1.TITLE , JD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT, ASC1.ID,ASC1.RETURN_DATE, ASC1.ISSUE_DATE, ASC1.ASSET_ASSET_ID ";
	
	public class OptionRange {
		private int rangeMin;
		private int rangeMax;

		public OptionRange(int rangeMin, int rangeMax) {
			this.rangeMin = rangeMin;
			this.rangeMax = rangeMax;
		}
		
		public int getRangeMin() {
			return rangeMin;
		}

		public int getRangeMax() {
			return rangeMax;
		}
	}
	
	@Override
	public void execute() {
		executeResourceTypeOptions();
	}
	
	@Override
	public void displayOptions() {
		//Put Code or just placeholder method
	}
	
	private void executeResourceTypeOptions() {
		OptionRange resourceOptionRange = displayCheckedOutResourceTypes();
		int option = readOptionNumber("Select a resource type", resourceOptionRange.getRangeMin(), resourceOptionRange.getRangeMax());
		
		String nextScreenUrl = "";
		switch (option) {
		case 4:
			nextScreenUrl = RouteConstant.BACK;
			break;
		case 3:
			executeCheckedoutRoomOptions();
			nextScreenUrl = RouteConstant.PATRON_BASE;
			break;
		case 2:
			executeCheckedoutCameraOptions();
			nextScreenUrl = RouteConstant.PATRON_BASE;
			break;
		case 1:
			executeCheckedoutPublicationOptions();
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
	
	private void executeCheckedoutRoomOptions() {
		OptionRange roomOptionRange = displayCheckedOutRoomOptions();
		int option = readOptionNumber("Enter an option", roomOptionRange.getRangeMin(), roomOptionRange.getRangeMax());
		
		String nextScreenUrl = "";
		switch (option) {
		case 3:
			nextScreenUrl = RouteConstant.BACK;
			break;
		case 2:
			//executeViewAndRenewRooms();
			nextScreenUrl = RouteConstant.PATRON_BASE;
			break;
		case 1:
			//executeReturnRooms();
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
	
	private void executeCheckedoutCameraOptions() {
		OptionRange cameraOptionRange = displayCheckedOutCameraOptions();
		int option = readOptionNumber("Enter an option", cameraOptionRange.getRangeMin(), cameraOptionRange.getRangeMax());
		
		String nextScreenUrl = "";
		switch (option) {
		case 3:
			nextScreenUrl = RouteConstant.BACK;
			break;
		case 2:
			executeViewCameras();
			nextScreenUrl = RouteConstant.PATRON_BASE;
			break;
		case 1:
			executeReturnCameras();
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
	
	private void executeViewCameras() {
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("main");
		EntityManager entityManager = emFactory.createEntityManager();
		
		Query viewCameraQuery = entityManager.createNativeQuery(viewCheckedOutCamerasString).setParameter(1, SessionUtils.getPatronId());
		List camerasResult = viewCameraQuery.getResultList();
		int numCameras = camerasResult.size();
		
		entityManager.close();
		emFactory.close();
		
		int i = 0;
		Object[][] cameraDisplayDetails  = new Object[numCameras][6];
		Object[][] cameraDetails = new Object[numCameras][];
		
		while(i < numCameras){
			Object[] arr = (Object[]) camerasResult.get(i);
			cameraDetails[i] = arr;
			cameraDisplayDetails[i][0] = arr[6];
			cameraDisplayDetails[i][1]=  arr[7];
			cameraDisplayDetails[i][2]=  arr[4];
			cameraDisplayDetails[i][3]=  arr[5];
			cameraDisplayDetails[i][4]=  arr[2];
			cameraDisplayDetails[i][4]=  arr[3];
			i++;
		}
		
		System.out.println("Checked out cameras:");
		
		String[] title = {"Maker","Model", "Lens Details", "Available Memory", "Issue Date", "Due Date"};
		TextTable tt = new TextTable(title, cameraDisplayDetails);
		tt.setAddRowNumbering(true);
		tt.printTable();
		
		int option = readOptionNumber("Enter 0 to go back", 0, 0);
		
		if(option == 0) {
			return;
		}
	}
	
	
	private void executeReturnCameras() {
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager entityManager = emFactory.createEntityManager();
		
		Query viewCameraQuery = entityManager.createNativeQuery(viewCheckedOutCamerasString).setParameter(1, SessionUtils.getPatronId());
		List camerasResult = viewCameraQuery.getResultList();
		int numCameras = camerasResult.size();
		
		int i = 0;
		Object[][] cameraDisplayDetails  = new Object[numCameras][6];
		Object[][] cameraDetails = new Object[numCameras][];
		
		while(i < numCameras){
			Object[] arr = (Object[]) camerasResult.get(i);
			cameraDetails[i] = arr;
			cameraDisplayDetails[i][0] = arr[6];
			cameraDisplayDetails[i][1]=  arr[7];
			cameraDisplayDetails[i][2]=  arr[4];
			cameraDisplayDetails[i][3]=  arr[5];
			cameraDisplayDetails[i][4]=  arr[2];
			cameraDisplayDetails[i][5]=  arr[3];
			i++;
		}
		
		System.out.println("Checked out cameras:");
		
		String[] title = {"Maker","Model", "Lens Details", "Available Memory", "Issue Date", "Due Date"};
		TextTable tt = new TextTable(title, cameraDisplayDetails);
		tt.setAddRowNumbering(true);
		tt.printTable();
		
		int option = readOptionNumber("Enter the number of the camera you want to return (or 0 to go back)", 0, numCameras);
		
		if(option == 0) {
			return;
		}
		
		BigDecimal chkoutId = (BigDecimal) cameraDetails[option - 1][1];
		String updateReturnString = "UPDATE ASSET_CHECKOUT asc1 "
				+ "set ASC1.RETURN_DATE = sysdate, ASC1.FINE = (SELECT fine_amount from FINE_SNAPSHOT where checkout_id = ?) "
				+"	WHERE ASC1.ID = ? ";
		
		entityManager.getTransaction().begin();
		
		Query q = entityManager.createNativeQuery(updateReturnString).setParameter(1, chkoutId).setParameter(2, chkoutId);
		int outNo = q.executeUpdate();

		entityManager.getTransaction().commit();

		Query getFineQuery = entityManager.createQuery("SELECT fine"
					+ " FROM AssetCheckout"
					+ " WHERE id = ?");
		getFineQuery.setParameter(1, chkoutId);
		float fineAmount = (float) getFineQuery.getSingleResult();
		
		if(fineAmount != 0)
			System.out.print("You have successfully returned the camera. You have incurred a fine of $" + fineAmount + " on this device.");
		else
			System.out.print("You have successfully returned the camera.");

		entityManager.close();
		emFactory.close();
	}
	private void executeViewAndRenewPublications() {
		
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("main");
		EntityManager entityManager = emFactory.createEntityManager();
		
		Query viewRenewBookQuery = entityManager.createNativeQuery(viewRenewBookString).setParameter(1, SessionUtils.getPatronId());
		List booksResult = viewRenewBookQuery.getResultList();
		int numBooks = booksResult.size();
		
		Query viewRenewConfProcQuery = entityManager.createNativeQuery(viewRenewConfProcString).setParameter(1, SessionUtils.getPatronId());
		List confProcsResult = viewRenewConfProcQuery.getResultList();
		int numConfProcs = confProcsResult.size();
		
		Query viewRenewJournalQuery = entityManager.createNativeQuery(viewRenewJournalString).setParameter(1, SessionUtils.getPatronId());
		List journalsResult = viewRenewJournalQuery.getResultList();
		int numJournals = journalsResult.size();
		
		entityManager.close();
		emFactory.close();
		
		int i = 0;
		int j = 0;
		Object[][] publications  = new Object[numBooks + numConfProcs + numJournals][6];
		Object[][] books = new Object[numBooks][];
		Object[][] confProcs = new Object[numConfProcs][];
		Object[][] journals = new Object[numJournals][];
		
		while(j < numBooks){
			Object[] arr = (Object[]) booksResult.get(j);
			books[j] = arr;
			publications[i][0] = arr[2];
			publications[i][1]=  arr[3];
			publications[i][2]=  arr[5];
			publications[i][3]=  arr[7];
			publications[i][4]=  arr[8];
			i++;
			j++;
		}
		
		j = 0;
		while(j < numConfProcs){
			Object[] arr = (Object[]) confProcsResult.get(j);
			confProcs[j] = arr;
			publications[i][0] = arr[3];
			publications[i][1]=  arr[4];
			publications[i][2]=  arr[6];
			publications[i][3]=  arr[7];
			publications[i][4]=  arr[8];
			i++;
			j++;
		}
		
		j = 0;
		while(j < numJournals){
			Object[] arr = (Object[]) journalsResult.get(j);
			journals[j] = arr;
			publications[i][0] = arr[2];
			publications[i][1]=  "";
			publications[i][2]=  arr[4];
			publications[i][3]=  arr[5];
			publications[i][4]=  arr[6];
			i++;
			j++;
		}
		System.out.println("Choose the publication:");
		
		String[] title = {"Title","Edition OR Conf. Name", "Publication Format", "Authors", "Status"};
		TextTable tt = new TextTable(title, publications);
		tt.setAddRowNumbering(true);
		tt.printTable();
		
		int option = readOptionNumber("Enter a publication you want to renew/waitlist (or 0 to go back)", 0, numBooks + numConfProcs + numJournals);
		
		if(option == 0) {
			return;
		}
		else
			executeReturnPublications();
		//ENTER CODE TO RENEW/PUT INTO WAITLIST
		System.out.println("Enter code here to renew / waitlist");
	}
	
private void executeReturnPublications() {
		
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("main");
		EntityManager entityManager = emFactory.createEntityManager();
		
		Query viewRenewBookQuery = entityManager.createNativeQuery(viewRenewBookString).setParameter(1, SessionUtils.getPatronId());
		List booksResult = viewRenewBookQuery.getResultList();
		int numBooks = booksResult.size();
		
		Query viewRenewConfProcQuery = entityManager.createNativeQuery(viewRenewConfProcString).setParameter(1, SessionUtils.getPatronId());
		List confProcsResult = viewRenewConfProcQuery.getResultList();
		int numConfProcs = confProcsResult.size();
		
		Query viewRenewJournalQuery = entityManager.createNativeQuery(viewRenewJournalString).setParameter(1, SessionUtils.getPatronId());
		List journalsResult = viewRenewJournalQuery.getResultList();
		int numJournals = journalsResult.size();
		
		entityManager.close();
		emFactory.close();
		
		int i = 0;
		int j = 0;
		Object[][] publications  = new Object[numBooks + numConfProcs + numJournals][6];
		Object[][] books = new Object[numBooks][];
		Object[][] confProcs = new Object[numConfProcs][];
		Object[][] journals = new Object[numJournals][];
		
		while(j < numBooks){
			Object[] arr = (Object[]) booksResult.get(j);
			books[j] = arr;
			publications[i][0] = arr[2];
			publications[i][1]=  arr[3];
			publications[i][2]=  arr[5];
			publications[i][3]=  arr[7];
			publications[i][4]=  arr[8];
			i++;
			j++;
		}
		
		j = 0;
		while(j < numConfProcs){
			Object[] arr = (Object[]) confProcsResult.get(j);
			confProcs[j] = arr;
			publications[i][0] = arr[3];
			publications[i][1]=  arr[4];
			publications[i][2]=  arr[6];
			publications[i][3]=  arr[7];
			publications[i][4]=  arr[8];
			i++;
			j++;
		}
		
		j = 0;
		while(j < numJournals){
			Object[] arr = (Object[]) journalsResult.get(j);
			journals[j] = arr;
			publications[i][0] = arr[2];
			publications[i][1]=  "";
			publications[i][2]=  arr[4];
			publications[i][3]=  arr[5];
			publications[i][4]=  arr[6];
			i++;
			j++;
		}
		System.out.println("Choose the publication:");
		
		String[] title = {"Title","Edition OR Conf. Name", "Publication Format", "Authors", "Status"};
		TextTable tt = new TextTable(title, publications);
		tt.setAddRowNumbering(true);
		tt.printTable();
		
		int option = readOptionNumber("Enter a publication you want to return (or 0 to go back):", 0, numBooks + numConfProcs + numJournals);
		
		if(option == 0) {
			return;
		}
		
		if(option <= numBooks) {
			runReturnPublicationCode(SessionUtils.getPatronId(), (String) books[option - 1][0], (BigDecimal) books[option - 1][9]);
		} else if( option <= numConfProcs) {
			runReturnPublicationCode(SessionUtils.getPatronId(), (String) confProcs[option - numBooks - 1][0],(BigDecimal) confProcs[option - numBooks - 1][9]);
		} else {
			runReturnPublicationCode(SessionUtils.getPatronId(), (String) journals[option - numBooks - numConfProcs - 1][0],(BigDecimal)journals[option - numBooks - numConfProcs - 1][7]);
		}
		
		
	}
	
	private void runReturnPublicationCode(String patronId, String assetId, BigDecimal chkoutId) {
		Patron loggedInPatron = (Patron) DBUtils.findEntity(Patron.class, SessionUtils.getPatronId(), String.class);

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				"main");
		EntityManager entitymanager = emfactory.createEntityManager( );

			
		String updateReturnString = "UPDATE ASSET_CHECKOUT asc1 "
				+ "set ASC1.RETURN_DATE = sysdate, ASC1.FINE = (SELECT fine_amount from FINE_SNAPSHOT where checkout_id = ?) "
				+"	WHERE ASC1.ID = ? ";
		
		entitymanager.getTransaction().begin();
		
		Query q = entitymanager.createNativeQuery(updateReturnString).setParameter(1, chkoutId).setParameter(2, chkoutId);
		int outNo = q.executeUpdate();

		entitymanager.getTransaction().commit();


		entitymanager.close();
		emfactory.close();

		
	}
	
	private void runRenewPublicationCode(String patronId, String assetId, BigDecimal chkoutId) {
		Patron loggedInPatron = (Patron) DBUtils.findEntity(Patron.class, SessionUtils.getPatronId(), String.class);

		boolean is_student = SessionUtils.isStudent();
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				"main");
		EntityManager entitymanager = emfactory.createEntityManager( );

			
		String updateReturnString = "UPDATE ASSET_CHECKOUT asc1 "
				+ "set ASC1.RETURN_DATE = sysdate, ASC1.FINE = (SELECT fine_amount from FINE_SNAPSHOT where checkout_id = ?) "
				+"	WHERE ASC1.ID = ? "; 
		
		entitymanager.getTransaction().begin();
		
		Query q = entitymanager.createNativeQuery(updateReturnString).setParameter(1, chkoutId).setParameter(2, chkoutId);
		int outNo = q.executeUpdate();

		entitymanager.getTransaction().commit();


		entitymanager.close();
		emfactory.close();

		
	}
	

	private void executeCheckedoutPublicationOptions() {
		OptionRange publicationOptionRange = displayCheckedOutPublicationOptions();
		int option = readOptionNumber("Enter an option", publicationOptionRange.getRangeMin(), publicationOptionRange.getRangeMax());
		
		String nextScreenUrl = "";
		switch (option) {
		case 3:
			nextScreenUrl = RouteConstant.BACK;
			break;
		case 2:
			executeReturnPublications();
			nextScreenUrl = RouteConstant.PATRON_BASE;
			break;
		case 1:
			executeViewAndRenewPublications();
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
	
	public OptionRange displayCheckedOutResourceTypes() {
		System.out.println("Choose the type of the resources that you have checked out:");
		String[] title = {""};
		String[][] options = { 
							{Constant.OPTION_PUBLICATIONS},
							{Constant.OPTION_CAMERAS},
							{Constant.OPTION_ROOMS},
							{Constant.OPTION_BACK}
							};
		TextTable tt = new TextTable(title, options);
		tt.setAddRowNumbering(true);
		tt.printTable();
		
		return new OptionRange(1, options.length);
	}
	
	public OptionRange displayCheckedOutPublicationOptions() {
		System.out.println("Choose what you want to do with checked out publications:");
		String[] title = {""};
		String[][] options = { 
							{Constant.OPTION_ASSET_VIEW_OR_RENEW},
							{Constant.OPTION_ASSET_RETURN},
							{Constant.OPTION_BACK}
							};
		TextTable tt = new TextTable(title, options);
		tt.setAddRowNumbering(true);
		tt.printTable();
		
		return new OptionRange(1, options.length);
	}
	
	public OptionRange displayCheckedOutCameraOptions() {
		System.out.println("Choose what you want to do with checked out cameras:");
		String[] title = {""};
		String[][] options = { 
							{Constant.OPTION_ASSET_VIEW},
							{Constant.OPTION_ASSET_RETURN},
							{Constant.OPTION_BACK}
							};
		TextTable tt = new TextTable(title, options);
		tt.setAddRowNumbering(true);
		tt.printTable();
		
		return new OptionRange(1, options.length);
	}
	
	public OptionRange displayCheckedOutRoomOptions() {
		System.out.println("Choose what you want to do with checked out rooms:");
		String[] title = {""};
		String[][] options = { 
							{Constant.OPTION_ASSET_VIEW_OR_RENEW},
							{Constant.OPTION_ASSET_RETURN},
							{Constant.OPTION_BACK}
							};
		TextTable tt = new TextTable(title, options);
		tt.setAddRowNumbering(true);
		tt.printTable();
		
		return new OptionRange(1, options.length);
	}
}
