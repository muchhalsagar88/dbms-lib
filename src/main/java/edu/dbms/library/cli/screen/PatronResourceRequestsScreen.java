package edu.dbms.library.cli.screen;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.session.SessionUtils;

public class PatronResourceRequestsScreen extends BaseScreen{

	public void readInputLabel() {
		System.out.print("Enter your choice: ");
	}
	
	public Object readInput() {
		int option = inputScanner.nextInt();
		return option;
	}
	
	@Override
	public void execute() {
		displayOptions();
		readInputLabel();
		Object o = readInput();
		while(!(o instanceof Integer)) {
			System.out.println("Incorrect input.");
			readInputLabel();
			o = readInput();
		}
		
		BaseScreen nextScreen = getNextScreen(options.get((Integer)o).getRouteKey());
		nextScreen.execute();
	}
	
	@Override
	public void displayOptions() {
	
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("main");
		EntityManager entityManager = emFactory.createEntityManager();
		
		String returnedAssetsString = "SELECT CONCAT(BD.TITLE, ' - ' , BD.EDITION) AS ASSET_NAME, AC.ID, AC.ISSUE_DATE, AC.RETURN_DATE, 'Returned' as ASSET_STATUS"
		+ " FROM ASSET_CHECKOUT AC, ASSET A, ASSET_TYPE AT, BOOK B, BOOK_DETAIL BD"
		+ " WHERE AC.PATRON_ID = ?"
		+ " AND AC.ISSUE_DATE IS NOT NULL"
		+ " AND AC.RETURN_DATE IS NOT NULL"
		+ " AND A.ASSET_ID = AC.ASSET_ASSET_ID"
		+ " AND AT.CATEGORY = 'Publication'"
		+ " AND AT.SUB_CATEGORY = 'Book'"
		+ " AND A.ASSET_TYPE = AT.ASSETTYPEID"
		+ " AND B.BOOK_ID = A.ASSET_ID"
		+ " AND BD.ISBN_NUMBER = B.ISBN_NUMBER"
		+ " UNION"
		//RESERVED BOOKS
		+ " SELECT CONCAT(BD.TITLE, ' - ' , BD.EDITION) AS ASSET_NAME, AC.ID, AC.ISSUE_DATE, AC.RETURN_DATE, 'Returned' as ASSET_STATUS"
		+ " FROM ASSET_CHECKOUT AC, ASSET A, ASSET_TYPE AT, BOOK B, BOOK_DETAIL BD"
		+ " WHERE AC.PATRON_ID = ?"
		+ " AND AC.ISSUE_DATE IS NOT NULL"
		+ " AND AC.RETURN_DATE IS NOT NULL"
		+ " AND A.ASSET_ID = AC.ASSET_ASSET_ID"
		+ " AND AT.CATEGORY = 'Publication'"
		+ " AND AT.SUB_CATEGORY = 'Reserved Book'"
		+ " AND A.ASSET_TYPE = AT.ASSETTYPEID"
		+ " AND B.BOOK_ID = A.ASSET_ID"
		+ " AND BD.ISBN_NUMBER = B.ISBN_NUMBER"
		+ " UNION"
		//JOURNALS
		+ " SELECT JD.TITLE AS ASSET_NAME, AC.ID, AC.ISSUE_DATE, AC.RETURN_DATE, 'Returned' as ASSET_STATUS"
		+ " FROM ASSET_CHECKOUT AC, ASSET A, ASSET_TYPE AT, JOURNAL J, JOURNAL_DETAIL JD"
		+ " WHERE AC.PATRON_ID = ?"
		+ " AND AC.ISSUE_DATE IS NOT NULL"
		+ " AND AC.RETURN_DATE IS NOT NULL"
		+ " AND A.ASSET_ID = AC.ASSET_ASSET_ID"
		+ " AND AT.CATEGORY = 'Publication'"
		+ " AND AT.SUB_CATEGORY = 'Journal'"
		+ " AND A.ASSET_TYPE = AT.ASSETTYPEID"
		+ " AND J.JOURNAL_ID = A.ASSET_ID"
		+ " AND JD.ISSN_NUMBER = J.ISSN_NUMBER"
		+ " UNION"
		//CONFERENCE PROCEEDINGS
		+ " SELECT JD.TITLE AS ASSET_NAME, AC.ID, AC.ISSUE_DATE, AC.RETURN_DATE, 'Returned' as ASSET_STATUS"
		+ " FROM ASSET_CHECKOUT AC, ASSET A, ASSET_TYPE AT, CONF_PROCEEDING CP, CONFERENCE_PROCEEDING_DETAIL CPD"
		+ " WHERE AC.PATRON_ID = ?"
		+ " AND AC.ISSUE_DATE IS NOT NULL"
		+ " AND AC.RETURN_DATE IS NOT NULL"
		+ " AND A.ASSET_ID = AC.ASSET_ASSET_ID"
		+ " AND AT.CATEGORY = 'Publication'"
		+ " AND AT.SUB_CATEGORY = 'ConferenceProceeding'"
		+ " AND A.ASSET_TYPE = AT.ASSETTYPEID"
		+ " AND CP.CONF_PROC_ID = A.ASSET_ID"
		+ " AND CPD.CONF_NUM = CP.CONF_NUM"
		+ " UNION"
		//CAMERAS
		+ " SELECT CONCAT(CD.MAKER, ' - ' , CD.MODEL) AS ASSET_NAME, AC.ID, AC.ISSUE_DATE, AC.RETURN_DATE, 'Returned' as ASSET_STATUS"
		+ " FROM ASSET_CHECKOUT AC, ASSET A, ASSET_TYPE AT, CAMERA C, CAMERA_DETAIL CD"
		+ " WHERE AC.PATRON_ID = ?"
		+ " AND AC.ISSUE_DATE IS NOT NULL"
		+ " AND AC.RETURN_DATE IS NOT NULL"
		+ " AND A.ASSET_ID = AC.ASSET_ASSET_ID"
		+ " AND AT.CATEGORY = 'Device'"
		+ " AND AT.SUB_CATEGORY = 'Camera'"
		+ " AND A.ASSET_TYPE = AT.ASSETTYPEID"
		+ " AND C.CAMERA_ID = A.ASSET_ID"
		+ " AND CD.CAMERA_DETAIL_ID = C.CAMERA_DETAIL_ID"
		+ " UNION"
		//STUDY ROOMS
		+ " SELECT CONCAT(L.LIBRARY_NAME, ': ', R.FLOORLEVEL, '-', R.ROOMNO) AS ASSET_NAME, AC.ID, AC.ISSUE_DATE, AC.RETURN_DATE, 'Returned' as ASSET_STATUS"
		+ " FROM ASSET_CHECKOUT AC, ASSET A, ASSET_TYPE AT, ROOM R, LIBRARY L"
		+ " WHERE AC.PATRON_ID = ?"
		+ " AND AC.ISSUE_DATE IS NOT NULL"
		+ " AND AC.RETURN_DATE IS NOT NULL"
		+ " AND A.ASSET_ID = AC.ASSET_ASSET_ID"
		+ " AND AT.CATEGORY = 'Room'"
		+ " AND AT.SUB_CATEGORY = 'Study Room'"
		+ " AND L.LIBRARY_ID = A.LIBRARY_ID"
		+ " AND A.ASSET_TYPE = AT.ASSETTYPEID"
		+ " AND R.ROOM_ID = A.ASSET_ID"
		+ " UNION"
		//CONFERENCE ROOMS
		+ " SELECT CONCAT(L.LIBRARY_NAME, ': ', R.FLOORLEVEL, '-', R.ROOMNO) AS ASSET_NAME, AC.ID, AC.ISSUE_DATE, AC.RETURN_DATE, 'Returned' as ASSET_STATUS"
		+ " FROM ASSET_CHECKOUT AC, ASSET A, ASSET_TYPE AT, ROOM R, LIBRARY L"
		+ " WHERE AC.PATRON_ID = ?"
		+ " AND AC.ISSUE_DATE IS NOT NULL"
		+ " AND AC.RETURN_DATE IS NOT NULL"
		+ " AND A.ASSET_ID = AC.ASSET_ASSET_ID"
		+ " AND AT.CATEGORY = 'Room'"
		+ " AND AT.SUB_CATEGORY = 'Conference Room'"
		+ " AND L.LIBRARY_ID = A.LIBRARY_ID"
		+ " AND A.ASSET_TYPE = AT.ASSETTYPEID"
		+ " AND R.ROOM_ID = A.ASSET_ID"
		
		+ " UNION"
		+ " SELECT "
		+ " FROM PUBLICATION_WAITLIST PW, BOOK_DETAIL BD"
		+ " WHERE PW.PATRONID = ?"
		+ " BD.ISBN_NUMBER = PW.PUBSECONDARYID"
		+ " ";
		
		
		Query returnedAssetsQuery = entityManager.createNativeQuery(returnedAssetsString).setParameter(1, SessionUtils.getPatronId()).setParameter(2, SessionUtils.getPatronId()).setParameter(3, SessionUtils.getPatronId()).setParameter(4, SessionUtils.getPatronId()).setParameter(5, SessionUtils.getPatronId()).setParameter(6, SessionUtils.getPatronId()).setParameter(7, SessionUtils.getPatronId());
		List returnedAssets = returnedAssetsQuery.getResultList();
		
		returnedAssetsQuery.setParameter("patronId", SessionUtils.getPatronId());
		List returnedAssetsData = returnedAssetsQuery.getResultList();
		
		String returnedResourcesQuery = "SELECT ac" +
											" FROM AssetCheckout ac" +
											" WHERE ac.patron.id=:patronId" +
											" AND ac.issueDate <> NULL" +
											" AND ac.returnDate <> NULL";
		
		//List<AssetCheckout> returnedResourcesList = (List<AssetCheckout>) DBUtils.fetchAllEntities(returnedResourcesQuery);
		
		System.out.println("Resource Requests");
		System.out.println("-------------------------------------------");
	
	
	}
}
