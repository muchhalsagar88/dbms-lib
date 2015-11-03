package edu.dbms.library.cli.screen;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import dnl.utils.text.table.TextTable;
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.db.DBUtils;
import edu.dbms.library.session.SessionUtils;
import edu.dbms.library.utils.DateUtils;

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

		BaseScreen nextScreen = getNextScreen(RouteConstant.PATRON_BASE);
		nextScreen.execute();
	}

	@Override
	public void displayOptions() {

		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager entityManager = emFactory.createEntityManager();

		String returnedResourcesString = "SELECT BD.TITLE || ' - ' || BD.EDITION AS ASSET_NAME, AC.ISSUE_DATE, AC.RETURN_DATE, 'Returned' as ASSET_STATUS"
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
		+ " SELECT BD.TITLE || ' - ' || BD.EDITION AS ASSET_NAME, AC.ISSUE_DATE, AC.RETURN_DATE, 'Returned' as ASSET_STATUS"
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
		+ " SELECT JD.TITLE AS ASSET_NAME, AC.ISSUE_DATE, AC.RETURN_DATE, 'Returned' as ASSET_STATUS"
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
		+ " SELECT CPD.CONFERENCENAME || ' - ' || CPD.TITLE AS ASSET_NAME, AC.ISSUE_DATE, AC.RETURN_DATE, 'Returned' as ASSET_STATUS"
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
		+ " SELECT CD.MAKER || ' - ' || CD.MODEL AS ASSET_NAME, AC.ISSUE_DATE, AC.RETURN_DATE, 'Returned' as ASSET_STATUS"
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
		+ " SELECT L.LIBRARY_NAME || ': ' || R.FLOORLEVEL || '-' || R.ROOMNO AS ASSET_NAME, AC.ISSUE_DATE, AC.RETURN_DATE, 'Returned' as ASSET_STATUS"
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
		+ " SELECT L.LIBRARY_NAME || ': ' || R.FLOORLEVEL || '-' || R.ROOMNO AS ASSET_NAME, AC.ISSUE_DATE, AC.RETURN_DATE, 'Returned' as ASSET_STATUS"
		+ " FROM ASSET_CHECKOUT AC, ASSET A, ASSET_TYPE AT, ROOM R, LIBRARY L"
		+ " WHERE AC.PATRON_ID = ?"
		+ " AND AC.ISSUE_DATE IS NOT NULL"
		+ " AND AC.RETURN_DATE IS NOT NULL"
		+ " AND A.ASSET_ID = AC.ASSET_ASSET_ID"
		+ " AND AT.CATEGORY = 'Room'"
		+ " AND AT.SUB_CATEGORY = 'Conference Room'"
		+ " AND L.LIBRARY_ID = A.LIBRARY_ID"
		+ " AND A.ASSET_TYPE = AT.ASSETTYPEID"
		+ " AND R.ROOM_ID = A.ASSET_ID";

		String waitlistedResourcesString = "SELECT BD.TITLE || ' - ' || BD.EDITION AS ASSET_NAME, PW.REQUEST_DATE, 'Waitlisted' AS ASSET_STATUS"
		+ " FROM PUBLICATION_WAITLIST PW, BOOK_DETAIL BD"
		+ " WHERE PW.PATRON_ID = ?"
		+ " AND BD.ISBN_NUMBER = PW.PUB_SECONDARY_ID"
		+ " UNION"
		//CONFERENCE PROCEEDINGS
		+ " SELECT CPD.CONFERENCENAME || ' - ' || CPD.TITLE AS ASSET_NAME, PW.REQUEST_DATE, 'Waitlisted' as ASSET_STATUS"
		+ " FROM PUBLICATION_WAITLIST PW, CONFERENCE_PROCEEDING_DETAIL CPD"
		+ " WHERE PW.PATRON_ID = ?"
		+ " AND CPD.CONF_NUM = PW.PUB_SECONDARY_ID"
		+ " UNION"
		//JOURNALS
		+ " SELECT JD.TITLE AS ASSET_NAME, PW.REQUEST_DATE, 'Waitlisted' as ASSET_STATUS"
		+ " FROM PUBLICATION_WAITLIST PW, JOURNAL_DETAIL JD"
		+ " WHERE PW.PATRON_ID = ?"
		+ " AND JD.ISSN_NUMBER = PW.PUB_SECONDARY_ID"
		+ " UNION"
		//INCLUDE CAMERA CHECKLIST
		+ " SELECT CD.MAKER || ' - ' || CD.MODEL AS ASSET_NAME, CR.RESERVE_DATE AS REQUEST_DATE, 'Waitlisted' AS ASSET_STATUS"
		+ " FROM CAMERA C, CAMERA_RESERVATION CR, CAMERA_DETAIL CD"
		+ " WHERE CR.PATRON_ID = ?"
		+ " AND CR.RESERVATION_STATUS = 'ACTIVE'"
		+ " AND CR.ISSUE_DATE >= ?"
		+ " AND C.CAMERA_ID = CR.CAMERA_ID"
		+ " AND CD.CAMERA_DETAIL_ID = C.CAMERA_DETAIL_ID";

		Query returnedResourcesQuery = entityManager.createNativeQuery(returnedResourcesString).setParameter(1, SessionUtils.getPatronId()).setParameter(2, SessionUtils.getPatronId()).setParameter(3, SessionUtils.getPatronId()).setParameter(4, SessionUtils.getPatronId()).setParameter(5, SessionUtils.getPatronId()).setParameter(6, SessionUtils.getPatronId()).setParameter(7, SessionUtils.getPatronId());
		List returnedResourcesResult = returnedResourcesQuery.getResultList();
		int numReturnedResources = returnedResourcesResult.size();

		Query waitlistedResourcesQuery = entityManager.createNativeQuery(waitlistedResourcesString).setParameter(1, SessionUtils.getPatronId())
				.setParameter(2, SessionUtils.getPatronId())
				.setParameter(3, SessionUtils.getPatronId())
				.setParameter(4, SessionUtils.getPatronId())
				.setParameter(5, DateUtils.formatToQueryDate(DateUtils.getNextFriday()));
		List waitlistedResourcesResult = waitlistedResourcesQuery.getResultList();
		int numWaitlistedResources = waitlistedResourcesResult.size();

		int i = 0;
		int j = 0;
		Object[][] resources  = new Object[numReturnedResources + numWaitlistedResources][4];

		while(j < numReturnedResources){
			Object[] arr = (Object[]) returnedResourcesResult.get(j);
			resources[i][0] = arr[0];
			resources[i][1]=  arr[1];
			resources[i][2]=  arr[2];
			resources[i][3]=  arr[3];
			i++;
			j++;
		}

		j = 0;
		while(j < numWaitlistedResources){
			Object[] arr = (Object[]) waitlistedResourcesResult.get(j);
			resources[i][0] = arr[0];
			resources[i][1]=  arr[1];
			resources[i][2]=  "";
			resources[i][3]=  arr[2];
			i++;
			j++;
		}

		System.out.println("Resource Requests");
		System.out.println("-------------------------------------------");

		String[] title = {"Asset Title","Issue / Request Date", "Return Date", "Status"};
		TextTable tt = new TextTable(title, resources);
		tt.setAddRowNumbering(true);
		tt.printTable();

		int option = readOptionNumber("Enter 0 to go back", 0, 0);

		if(option == 0) {
			return;
		}
	}
}
