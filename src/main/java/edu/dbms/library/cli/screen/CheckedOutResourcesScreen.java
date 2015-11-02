package edu.dbms.library.cli.screen;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import dnl.utils.text.table.TextTable;
import edu.dbms.library.cli.Constant;
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.session.SessionUtils;

public class CheckedOutResourcesScreen extends BaseScreen{

	String viewRenewBookString = "SELECT  A1.ASSET_ID ,  B1.ISBN_NUMBER, BD1.TITLE, BD1.EDITION, BD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT , PB1.NAME"
			+ " ,rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS, (CASE WHEN ASC1.ID IS NULL THEN 'AVLBLE' ELSE 'ISSUED' END) as STATUS"
			+ " from Book b1, Publication p1, BOOK_DETAIL bd1, Asset a1, Publisher pb1, Author auth1, BOOK_AUTHOR BA1 , ASSET_CHECKOUT asc1"
			+ " WHERE B1.BOOK_ID = a1.ASSET_ID"
			+ " AND a1.ASSET_ID = P1.PUBLICATION_ID"
			+ " AND ( ASC1.ASSET_ASSET_ID(+) = A1.ASSET_ID" 
			+ " AND ( ASC1.RETURN_DATE is NULL OR ASC1.RETURN_DATE=ASC1.ISSUE_DATE))"
			+ " AND B1.ISBN_NUMBER = BD1.ISBN_NUMBER"
			+ " AND BD1.PUBLISHER_ID = PB1.ID"
			+ " AND BA1.BOOK_ID = B1.ISBN_NUMBER"
			+ " AND AUTH1.ID = BA1.AUTHOR_ID"
			+ " AND  (ASC1.ASSET_ASSET_ID = A1.ASSET_ID"
			+ " AND ASC1.PATRON_ID = ?"
			+ " AND ASC1.RETURN_DATE is NULL	 )"
			+ "GROUP BY A1.ASSET_ID ,  B1.ISBN_NUMBER, BD1.TITLE, BD1.EDITION, BD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT , PB1.NAME,  ASC1.ID ";

	String viewRenewConfProcString = "SELECT unique  A1.ASSET_ID , CONF1.CONF_PROC_ID, CONF1.CONF_NUM, CPD1.TITLE,CPD1.CONFERENCENAME,CPD1.PUB_YEAR, P1.PUBLICATIONFORMAT"     
			+ " ,rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS,  (CASE WHEN ASC1.ID IS NULL THEN 'AVLBLE' ELSE 'ISSUED' END) as STATUS"  
			+ " from CONF_PROCEEDING conf1, Publication p1,   CONFERENCE_PROCEEDING_DETAIL cpd1 , Asset a1 ,  Author auth1, CONF_PROC_AUTHOR cpa1 , ASSET_CHECKOUT asc1"  
			+ " WHERE CONF1.CONF_PROC_ID = a1.ASSET_ID" 
			+ " AND a1.ASSET_ID = P1.PUBLICATION_ID"  
			+ " AND ( ASC1.ASSET_ASSET_ID(+) = A1.ASSET_ID"
			+ " AND ( ASC1.RETURN_DATE is NULL OR ASC1.RETURN_DATE=ASC1.ISSUE_DATE)	 )"
			+ " AND CONF1.CONF_NUM = CPD1.CONF_NUM"
			+ " AND CPA1.CONF_NUM = CONF1.CONF_NUM"
			+ " AND AUTH1.ID = CPA1.AUTHOR_ID"
			+ " AND  (ASC1.ASSET_ASSET_ID = A1.ASSET_ID"
			+ " AND ASC1.PATRON_ID = ?"	
			+ " AND ASC1.RETURN_DATE is NULL)"
			+ " GROUP BY A1.ASSET_ID , CONF1.CONF_PROC_ID, CONF1.CONF_NUM, CPD1.TITLE,CPD1.CONFERENCENAME,CPD1.PUB_YEAR, P1.PUBLICATIONFORMAT, ASC1.ID";
	
	String viewRenewJournalString = "SELECT A1.ASSET_ID , J1.ISSN_NUMBER ,  JD1.TITLE , JD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT"
			+ " ,rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS,  (CASE WHEN ASC1.ID IS NULL THEN 'AVLBLE' ELSE 'ISSUED' END) as STATUS"
			+ " from JOURNAL j1, Publication p1,   JOURNAL_DETAIL jd1 , Asset a1 ,  Author auth1, JOURNAL_AUTHOR ja1 , ASSET_CHECKOUT asc1"
			+ "  WHERE J1.JOURNAL_ID = a1.ASSET_ID"
			+ " AND a1.ASSET_ID = P1.PUBLICATION_ID"
			+ " AND ( ASC1.ASSET_ASSET_ID(+) = A1.ASSET_ID"
			+ " AND ( ASC1.RETURN_DATE is NULL OR ASC1.RETURN_DATE=ASC1.ISSUE_DATE)	 )"
			+ " AND J1.ISSN_NUMBER = JD1.ISSN_NUMBER"
			+ " AND JA1.JOURNAL_ID = J1.ISSN_NUMBER"
			+ " AND AUTH1.ID = JA1.AUTHOR_ID"
			+ " AND  (ASC1.ASSET_ASSET_ID =   A1.ASSET_ID"
			+ " AND ASC1.PATRON_ID = ?"
			+ " AND ASC1.RETURN_DATE is NULL	 )"
			+ " GROUP BY A1.ASSET_ID , J1.ISSN_NUMBER ,  JD1.TITLE , JD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT, ASC1.ID";
	
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
			//executeViewAndRenewCameras();
			nextScreenUrl = RouteConstant.PATRON_BASE;
			break;
		case 1:
			//executeReturnCameras();
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
		
		int option = readOptionNumber("Enter a publication you want to renew/waitlist (or 0 to go back):", 0, numBooks + numConfProcs + numJournals);
		
		if(option == 0) {
			return;
		}
		
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
		
		runReturnPublicationCode(SessionUtils.getPatronId(), (String) publications[option - 1][0]);
	}
	
	private void runReturnPublicationCode(String patronId, String assetId) {
		String updateReturnString = "update ASSET_CHECKOUT asc1 set ASC1.RETURN_DATE = SYSDATE"
				+ " WHERE ASC1.PATRON_ID = ?"
				+ " AND ASC1.ASSET_ASSET_ID = ?"
				+ " AND ASC1.RETURN_DATE is NULL";
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
							{Constant.OPTION_ASSET_VIEW_OR_RENEW},
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
