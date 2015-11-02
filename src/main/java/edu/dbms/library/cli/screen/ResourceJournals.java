package edu.dbms.library.cli.screen;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.joda.time.DateTime;

import dnl.utils.text.table.TextTable;
import edu.dbms.library.cli.Constant;
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.db.DBUtils;
import edu.dbms.library.db.manager.LoginManager;
import edu.dbms.library.entity.AssetCheckout;
import edu.dbms.library.entity.Patron;
import edu.dbms.library.entity.reserve.PublicationWaitlist;
import edu.dbms.library.entity.resource.Journal;
import edu.dbms.library.session.SessionUtils;

public class ResourceJournals extends BaseScreen {

	public ResourceJournals() {
		super();
		//		options.put(1, new Route(RouteConstant.PATRON_PROFILE));
		//		options.put(2, new Route(RouteConstant.PATRON_RESOURCES));
		//		options.put(3, new Route(RouteConstant.PATRON_CHECKED_OUT));
		//		options.put(4, new Route(RouteConstant.PATRON_RES_REQUEST));
		//		options.put(5, new Route(RouteConstant.PATRON_NOTIFICATIONS));
		//		options.put(6, new Route(RouteConstant.PATRON_BALANCE));
		//		options.put(7, new Route(RouteConstant.LOGOUT));
	}

	List<Journal> journals = new ArrayList<Journal>();
	Object[][] jrnls;
	Object[][] jrnls1;


	@Override
	public void execute() {
		displayJournals();
		displayOptions();
		Object o = readInput();
		while(!(o instanceof Integer)) {
			System.out.println("Incorrect input.");
			readInputLabel();
			o = readInput();
		}

		if((int)o == 0){
			//do nothing - goto patron screen.
		}
		else{
			// call checkout methods
			checkout((int)o);

			// update the approproate tables with respect to availibility. Assign return dates to items
			// checkout possible only for available items
			// if resource not available..put the request in waitlist
			// checkout rnew possible only if waitlist is empty -- update the due dates nd relevant data.
			System.out.println("The item has been checked out!!");
		}


		// Redirect to Base screen after checkout notification
		BaseScreen nextScreen = getNextScreen(RouteConstant.PATRON_BASE);
		nextScreen.execute();

	}

	public void readInputLabel() {
		System.out.print("Enter your choice: ");
	}

	public Object readInput() {
		/*
		 * Buggy!! Returns incorrect input without any input
		 * 
		 * String option = inputScanner.nextLine();
		try {
			int correct = Integer.parseInt(option);
			return correct;
		} catch (Exception e) {
			return option;
		}*/
		int option = inputScanner.nextInt();
		return option;
	}

	@Override
	public void displayOptions() {

		String[] title = {""};
		String[][] options = { 
				{"To Checkout: Enter the order Nummber"},
				{"0 For Main Menu."},
				
		};
		TextTable tt = new TextTable(title, options);
		tt.setAddRowNumbering(true);
		tt.printTable();
	}

	/*public static void main(String []args) {
		SessionUtils.init("patron_id", true);
		SessionUtils.updateCurrentRoute("/patron");
		new PatronScreen().execute();
	}*/

	public void displayJournals() {
		// opt1: Display only those books tht a patron can checkout. 
		// if the patron has been issued some books. remove thos ISBN number wala books from the display list..
		// Display conditions for REserved books??
		//		journals = getJournalList(); // publisher is not joined with books yet.

		String[] title = {"ISSN", "TITLE",  "AUTHOR(S)", "PUB_YEAR", "FORMAT", "STATUS"};

		Object[][] jrnls = getJournalList(); 

		TextTable tt = new TextTable(title, jrnls);
		tt.setAddRowNumbering(true);
		tt.printTable();
	}


	protected Object[][] getJournalList() {

		Patron loggedInPatron = (Patron) DBUtils.findEntity(Patron.class, SessionUtils.getPatronId(), String.class);

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				"main");
		EntityManager entitymanager = emfactory.createEntityManager( );

		String query = 
				" SELECT A1.ASSET_ID , J1.ISSN_NUMBER ,  JD1.TITLE , JD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT     " 
						+" ,rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS,  "
						+ " (CASE WHEN ( ASC1.RETURN_DATE is NULL  AND ASC1.ASSET_ASSET_ID = A1.ASSET_ID ) THEN 'ISSUED' ELSE 'AVLBLE' END) as STATUS   " 
						+"  from JOURNAL j1, Publication p1,   JOURNAL_DETAIL jd1 , Asset a1 ,  Author auth1, JOURNAL_AUTHOR ja1 , ASSET_CHECKOUT asc1   " 
						+" 						 		  WHERE J1.JOURNAL_ID = a1.ASSET_ID   " 
						+" 						 		  AND a1.ASSET_ID = P1.PUBLICATION_ID   " 
						+" 						 		  AND ( ASC1.ASSET_ASSET_ID(+) = A1.ASSET_ID   " 
						+" 						 		  	  )    " 
						+" 						 		  AND J1.ISSN_NUMBER = JD1.ISSN_NUMBER   " 
						+" 						 		  AND JA1.JOURNAL_ID = J1.ISSN_NUMBER		     " 
						+" 						 		  AND AUTH1.ID = JA1.AUTHOR_ID						  " 
						+" 						 		  GROUP BY A1.ASSET_ID , J1.ISSN_NUMBER ,  JD1.TITLE , JD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT, ASC1.ID ,ASC1.RETURN_DATE, ASC1.ISSUE_DATE, ASC1.ASSET_ASSET_ID   " 
						+"  MINUS " 
						+"  SELECT A1.ASSET_ID , J1.ISSN_NUMBER ,  JD1.TITLE , JD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT      " 
						+"  ,rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS,  "
						+ " (CASE WHEN ( ASC1.RETURN_DATE is NULL  AND ASC1.ASSET_ASSET_ID = A1.ASSET_ID ) THEN 'ISSUED' ELSE 'AVLBLE' END) as STATUS   " 
						+"  from JOURNAL j1, Publication p1,   JOURNAL_DETAIL jd1 , Asset a1 ,  Author auth1, JOURNAL_AUTHOR ja1 , ASSET_CHECKOUT asc1   " 
						+" 						 		  WHERE J1.JOURNAL_ID = a1.ASSET_ID   " 
						+" 						 		  AND a1.ASSET_ID = P1.PUBLICATION_ID   " 
						+" 						 		  AND ( ASC1.ASSET_ASSET_ID(+) = A1.ASSET_ID   " 
						+" 						 		   )    " 
						+" 						 		  AND J1.ISSN_NUMBER = JD1.ISSN_NUMBER   " 
						+" 						 		  AND JA1.JOURNAL_ID = J1.ISSN_NUMBER		     " 
						+" 						 		  AND AUTH1.ID = JA1.AUTHOR_ID " 
						+" 						 		  AND J1.ISSN_NUMBER IN    " 
						+"                                         (SELECT PUBSECONDARYID FROM PUBLICATION_WAITLIST WHERE PATRONID = ? " 
						+" 						 					UNION  " 
						+"                                          SELECT ASSET_SECONDARY_ID FROM asset_checkout astchkt WHERE astchkt.patron_id = ?     " 
						+"                                          AND (astchkt.RETURN_DATE is NULL ) )    " 
						+" 						 		  GROUP BY A1.ASSET_ID , J1.ISSN_NUMBER ,  JD1.TITLE , JD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT, ASC1.ID,ASC1.RETURN_DATE, ASC1.ISSUE_DATE, ASC1.ASSET_ASSET_ID    " ;				


		Query q = entitymanager.createNativeQuery(query);


		q.setParameter(1, loggedInPatron.getId()).setParameter(2, loggedInPatron.getId()).setParameter(3, loggedInPatron.getId());


		List obj1 = q.getResultList();

		entitymanager.close();
		emfactory.close();

		int i =0;
		jrnls  = new Object[obj1.size()][6];
		jrnls1 = new Object[obj1.size()][];
		while(i<obj1.size()){
			Object[] arr = (Object[]) obj1.get(i);
			jrnls1[i] =  arr;
			jrnls[i][0]=  arr[1];
			jrnls[i][1]=  arr[2];
			jrnls[i][2]=  arr[5];
			jrnls[i][3]=  arr[3];
			jrnls[i][4]=  arr[4];
			if(arr[4].toString().equals("Electronic copy"))
				jrnls[i][5]=  " ";
			else
				jrnls[i][5]=  arr[6];
			

			i++;
		}


		return jrnls ;



	}


	public void checkout(int bookNo) {
		
		if(LoginManager.isPatronAccountOnHold(SessionUtils.getPatronId())) {
			System.out.println("Your library privileges have been suspended. Please pay your dues to checkout assets.");
			return;
		}

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("main");
		EntityManager entitymanager = emfactory.createEntityManager( );


		//		Journal journal = journals.get(bookNo-1);
		String journalId  = (String)jrnls1[bookNo-1][0];
		String pub_format = (String)jrnls1[bookNo-1][4];

		String query = "SELECT cp FROM "+AssetCheckout.class.getName()
				+" cp where cp.asset.id = :num and cp.returnDate IS NULL";
		Query q = entitymanager.createQuery(query);

		q.setParameter("num", journalId);

		List<AssetCheckout> asc = (List<AssetCheckout>) q.getResultList();

		long count = asc.size();

		System.out.println("No Of results:"+ count);
		entitymanager.close();
		emfactory.close();

		Journal journal = (Journal) DBUtils.findEntity(Journal.class, journalId, String.class);

		boolean isStudent = SessionUtils.isStudent();

		Patron loggedInPatron = (Patron) DBUtils.findEntity(Patron.class, SessionUtils.getPatronId(), String.class);
		if(asc.size()==0){ //resource avlble in library
			AssetCheckout astChkOut = new AssetCheckout();

			Date issueDate = new Date();
			DateTime dt1 = new DateTime(issueDate);
			DateTime dt2;

			if(pub_format.equals("Physical copy")){

				if (isStudent)
					dt2 = dt1.plusHours(12);
				else
					dt2 = dt1.plusHours(12);

				Date dueDate = dt2.toDate();

				astChkOut.setAssetSecondaryId(journal.getDetails().getIssnNumber());
				astChkOut.setAsset(journal);
				astChkOut.setIssueDate(issueDate );
				astChkOut.setDueDate(dueDate);
				astChkOut.setPatron(loggedInPatron);
				DBUtils.persist(astChkOut);
				System.out.println("The item has been checked out: The return time is :"+ dueDate);

			}
			else{
				System.out.println("SOFT\n\n\n");
				dt2= dt1.plusMinutes(0);

				astChkOut.setAssetSecondaryId(journal.getDetails().getIssnNumber());
				astChkOut.setAsset(journal);
				astChkOut.setIssueDate(issueDate );
				astChkOut.setDueDate(issueDate);
				astChkOut.setReturnDate(issueDate);
				astChkOut.setPatron(loggedInPatron);
				DBUtils.persist(astChkOut);

				System.out.println("The item has been checked out: The return time is : N/A");
			}

		}
		else{

			AssetCheckout asc1 = asc.get(0);
			if(asc.size()==1 && loggedInPatron.getId() == asc1.getPatron().getId()){ 
				//renewe condition
				Date issueDate = new Date();
				DateTime dt1 = new DateTime(issueDate);
				DateTime dt2;
				if (isStudent)
					dt2 = dt1.plusDays(14);
				else
					dt2 = dt1.plusMonths(1);

				Date dueDate = dt2.toDate();

				entitymanager.getTransaction().begin();
				asc1.setDueDate(dueDate);
				entitymanager.getTransaction().commit();
			}
			else{ // reosurce nt avlble.. add to the waitlist.
				int flag = 0;
				if (isStudent) flag =1;
				PublicationWaitlist pb = new PublicationWaitlist(loggedInPatron.getId(),journal.getDetails().getIssnNumber(), new Date(), flag );

				DBUtils.persist(pb); 

				System.out.println("The item you have requested is not avlble. You are on waitlist and will be notified when the item is available");

			}




			//check of the book is already issues?
			// if issued by the same patron - check the waitlist for renew condition
			// if issued by other user -- add the entry to waitlist
			// if the book is not issued
			// entry int asset_checkout table.

			// checkout Rules:
			//	1. Reserved books can be checked out for maximum of 4hrs and by only students of the class for which the book is reserved.
			//	2. Electronic publications Have	no checkout duration.
			//	3. Journals and Conference Proceedings can be checked out for a period of 12 hours 
			//	4. Every other book Students: 2 weeks // faculty : 1 month.
		}
	}
}