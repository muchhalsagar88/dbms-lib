package edu.dbms.library.cli.screen;

import java.math.BigDecimal;
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
import edu.dbms.library.db.manager.PublicationManager;
import edu.dbms.library.entity.AssetCheckout;
import edu.dbms.library.entity.Patron;
import edu.dbms.library.entity.reserve.PublicationWaitlist;
import edu.dbms.library.entity.resource.Book;
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


			int journalNo = (int)o;

			String journalId  = (String)jrnls1[journalNo-1][0];
			String pub_format = (String)jrnls1[journalNo-1][4];
			BigDecimal ast_type = (BigDecimal.valueOf(2L));

			checkout(journalId, ast_type, pub_format);




			// update the approproate tables with respect to availibility. Assign return dates to items
			// checkout possible only for available items
			// if resource not available..put the request in waitlist
			// checkout rnew possible only if waitlist is empty -- update the due dates nd relevant data.
			//System.out.println("The item has been checked out!!");
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

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager entitymanager = emfactory.createEntityManager( );

		String query =
				" SELECT A1.ASSET_ID , J1.ISSN_NUMBER ,  JD1.TITLE , JD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT     "
						+" ,rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS  "
						+"  from JOURNAL j1, Publication p1,   JOURNAL_DETAIL jd1 , Asset a1 ,  Author auth1, JOURNAL_AUTHOR ja1 "
						+" 						 		  WHERE J1.JOURNAL_ID = a1.ASSET_ID   "
						+" 						 		  AND a1.ASSET_ID = P1.PUBLICATION_ID   "
						+" 						 		  AND J1.ISSN_NUMBER = JD1.ISSN_NUMBER   "
						+" 						 		  AND JA1.JOURNAL_ID = J1.ISSN_NUMBER		     "
						+" 						 		  AND AUTH1.ID = JA1.AUTHOR_ID"
//						+ " AND J1.ISSN_NUMBER NOT IN  (SELECT wtlist.PUBSECONDARYID  FROM publication_waitlist wtlist  WHERE (wtlist.PATRONID = ?))	"
						+" 						 		  GROUP BY A1.ASSET_ID , J1.ISSN_NUMBER ,  JD1.TITLE , JD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT   ";
//						+"  MINUS "
//						+"  SELECT A1.ASSET_ID , J1.ISSN_NUMBER ,  JD1.TITLE , JD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT      "
//						+"  ,rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS,  "
//						+ " (CASE WHEN ( ASC1.RETURN_DATE is NULL  AND ASC1.ASSET_ASSET_ID = A1.ASSET_ID ) THEN 'ISSUED' ELSE 'AVLBLE' END) as STATUS   "
//						+"  from JOURNAL j1, Publication p1,   JOURNAL_DETAIL jd1 , Asset a1 ,  Author auth1, JOURNAL_AUTHOR ja1 , ASSET_CHECKOUT asc1   "
//						+" 						 		  WHERE J1.JOURNAL_ID = a1.ASSET_ID   "
//						+" 						 		  AND a1.ASSET_ID = P1.PUBLICATION_ID   "
//						+" 						 		  AND ( ASC1.ASSET_ASSET_ID(+) = A1.ASSET_ID   "
//						+" 						 		   )    "
//						+" 						 		  AND J1.ISSN_NUMBER = JD1.ISSN_NUMBER   "
//						+" 						 		  AND JA1.JOURNAL_ID = J1.ISSN_NUMBER		     "
//						+" 						 		  AND AUTH1.ID = JA1.AUTHOR_ID "
//						+" 						 		  AND J1.ISSN_NUMBER IN    "
//						+"                                         (   SELECT ASSET_SECONDARY_ID FROM asset_checkout astchkt WHERE astchkt.patron_id = ?     "
//						+"                                          AND (astchkt.RETURN_DATE is NULL ) )    "
//						+" 						 		  GROUP BY A1.ASSET_ID , J1.ISSN_NUMBER ,  JD1.TITLE , JD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT, ASC1.ID,ASC1.RETURN_DATE, ASC1.ISSUE_DATE, ASC1.ASSET_ASSET_ID    " ;


		Query q = entitymanager.createNativeQuery(query);


		q.setParameter(1, loggedInPatron.getId()).setParameter(2, loggedInPatron.getId()).setParameter(3, loggedInPatron.getId());

		String query0 = "SELECT cp.asset.id FROM "+AssetCheckout.class.getName()
				+" cp where cp.returnDate IS NULL";

		String query1 = "SELECT cp.asset.id FROM "+AssetCheckout.class.getName()
				+" cp where cp.returnDate IS NULL AND cp.patron.id = :id1";

		Query q1 = entitymanager.createQuery(query0);
		Query q2 = entitymanager.createQuery(query1).setParameter("id1", loggedInPatron.getId());


		//list of all issued assets
		List<String> chkoutList = q1.getResultList();
		List<String> myChkoutList = q2.getResultList();




		List obj1 = q.getResultList();

		entitymanager.close();
		emfactory.close();

		int i =0;
		int ctr=0;

		Object[][] jrnlsTmp = new Object[obj1.size()][6];
		jrnls1 = new Object[obj1.size()][];
		while(i<obj1.size()){
			Object[] arr = (Object[]) obj1.get(i);
			if(myChkoutList.contains(arr[0])){
				i++;
				continue;
			}
			else if(chkoutList.contains(arr[0])){
				jrnlsTmp[ctr][5] = "ISSUED";
			}

			jrnls1[ctr] =  arr;
			jrnlsTmp[ctr][0]=  arr[1];
			jrnlsTmp[ctr][1]=  arr[2];
			jrnlsTmp[ctr][2]=  arr[5];
			jrnlsTmp[ctr][3]=  arr[3];
			jrnlsTmp[ctr][4]=  arr[4];

			ctr++;
			i++;
		}

		jrnls  = new Object[ctr][6];

		for(i = 0; i < ctr; i++) {

			jrnls[i] = jrnlsTmp[i];
		}


		return jrnls ;



	}


	public void checkout(String journalId , BigDecimal ast_type, String pub_format) {

		//Removing expired Waitlisted entries
		PublicationManager.removedExpiredWaitlists();
		if(LoginManager.isPatronAccountOnHold(SessionUtils.getPatronId())) {
			System.out.println("Your library privileges have been suspended. Please pay your dues to checkout assets.");
			return;
		}

		boolean isStudent = SessionUtils.isStudent();
		Patron loggedInPatron = (Patron) DBUtils.findEntity(Patron.class, SessionUtils.getPatronId(), String.class);
		try{
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
			EntityManager entitymanager = emfactory.createEntityManager( );

			//Get Book details
			Journal journal = (Journal) DBUtils.findEntity(Journal.class, journalId, String.class);
			//Query that returns a result if the book is currently checked out
			String bookAvlbl = "SELECT cp FROM "
					+ AssetCheckout.class.getName()
					+ " cp where cp.asset.id = :num and cp.returnDate IS NULL";

			Query qbkAvlbl = entitymanager.createQuery(bookAvlbl);
			qbkAvlbl.setParameter("num", journalId);
			List<AssetCheckout> isCheckedOutResult = qbkAvlbl.getResultList();

			//Query that returns all the waitlisted entries for this secondary ID
			String query = "SELECT cp FROM PublicationWaitlist cp"
					+" where cp.key.pubSecondaryId = :sec_id ";

			Query q = entitymanager.createQuery(query);
			q.setParameter("sec_id", journal.getDetails().getIssnNumber());
			List<PublicationWaitlist> wtList = q.getResultList();

			//If there is no waitlist
			if(wtList.size() == 0 ){
				if(isCheckedOutResult.size()==0){ //No one has checked out, so checkout the book to this user

					try{
						addJournalToAssetCheckout(pub_format, isStudent, journal, loggedInPatron, ast_type);
					}
					catch(Exception e){
						e.printStackTrace();
						System.out.println("Some error occured");
					}

				} else {// Some one has the book checked out

					//Query to check if this user has the book checked out
					String query04 = "SELECT cp FROM "+AssetCheckout.class.getName()
							+" cp where cp.asset.id = :num  AND cp.patron.id = :pat_id and cp.returnDate IS NULL";

					Query q4 = entitymanager.createQuery(query04);
					q4.setParameter("num", journalId).setParameter("pat_id", loggedInPatron.getId());
					List<AssetCheckout> isCheckedOutByThisUserResult = q4.getResultList();

					if(isCheckedOutByThisUserResult.size() == 0){
						//Add user to waitlist as the book is checked out by someone
						addToWaitList(isStudent, loggedInPatron.getId(),loggedInPatron, journal.getDetails().getIssnNumber());
					}
					else{
						updateAssetCheckout(entitymanager, isCheckedOutByThisUserResult.get(0),pub_format,isStudent, ast_type);
					}
				}
			} else {//If there is a waitlist for this book

				boolean isCurrUserinWaitList = false;
				PublicationWaitlist waitingUser = null;
				//check ig this user is in waitlist
				for(PublicationWaitlist wlT : wtList){
					if(wlT.getPatron().getId() == loggedInPatron.getId()){
						isCurrUserinWaitList = true;
						waitingUser = wlT;
						break;
					}
				}

				if(!isCurrUserinWaitList){	//User is not in waitlist
					addToWaitList(isStudent, loggedInPatron.getId(),loggedInPatron, journal.getDetails().getIssnNumber());
				} else { // user is in waitlist....check if book avlble then chkout else donothing

					//Query to check if the book is available and this guy is in waitlist
					String query01 = "SELECT cp FROM "+AssetCheckout.class.getName()
							+" cp where cp.asset.id = :num and cp.returnDate IS NULL";

					Query q0 = entitymanager.createQuery(query01);
					q0.setParameter("num", journalId);
					List<AssetCheckout> asc1 = q0.getResultList();

					if(asc1.size()==0){ //book is avlble
						Date startTime = waitingUser.getStartTime();
						Date endTime = waitingUser.getEndTime();
						Date currTime = new Date();
						//If this guys assigned checkout time is now
						if(startTime.before(currTime) && endTime.after(currTime)){
							try{
								addJournalToAssetCheckout(pub_format, isStudent, journal, loggedInPatron, ast_type);
								String removequery = "DELETE  FROM PUBLICATION_WAITLIST pw "
										+"  where pw.PUB_SECONDARY_ID = ? and  PATRON_ID = ? ";

								Query retq0 = entitymanager.createNativeQuery(removequery);
								retq0.setParameter(1, journal.getDetails().getIssnNumber()).setParameter(2, loggedInPatron.getId());

								entitymanager.getTransaction().begin();
								retq0.executeUpdate();
								entitymanager.getTransaction().commit();

							}
							catch(Exception e){
								e.printStackTrace();
								System.out.println("The book is already issued by you...");
							}
						} else
							System.out.println("You can not checkout the book at this time");
					} else{
						//Do nothing
						System.out.println(" You are already in waitlist. The book hasnt been returned yet. You will be notified");
					}
				}
			}

			entitymanager.close();
			emfactory.close();
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Error..Asset reserved More than one time by the same patron");
		}
	}



	private void updateAssetCheckout(EntityManager entitymanager, AssetCheckout assetCheckout, String pub_format, boolean isStudent, BigDecimal ast_type) {


		Date issueDate = new Date();
		DateTime dt1 = new DateTime(issueDate);
		DateTime dt2 = null;
		Date dueDate ;

		if(pub_format.equals("Physical copy")){
			//			System.out.println("HARD\n\n\n");
			dt2 = dt1.plusHours(12);

			dueDate = dt2.toDate();
			assetCheckout.setDueDate(dueDate);

			int flag = 0;
			if (isStudent) flag =1;

			String updatequery = "UPDATE ASSET_CHECKOUT asc1 set asc1.DUE_DATE = ?"
					+" where asc1.id = ? ";


			Query upq = entitymanager.createNativeQuery(updatequery);

			upq.setParameter(1, dueDate).setParameter(2, assetCheckout.getId());

			entitymanager.getTransaction().begin();
			int output = upq.executeUpdate();
			entitymanager.getTransaction().commit();

			//			clearConsole();
			System.out.println("The item has been checked out: The return time is :"+ dueDate);


		}


	}

	public void addToWaitList(boolean isStudent, String patronid, Patron loggedInPatron, String isbnNumber) {
		// TODO Auto-generated method stub

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager entitymanager = emfactory.createEntityManager( );

		int flag = 0;
		if (isStudent) flag =1;

		String query = "SELECT cp FROM PublicationWaitlist cp"
				+" where cp.key.pubSecondaryId = :sec_id ORDER BY cp.requestDate desc, cp.isStudent desc";


		Query q = entitymanager.createQuery(query);

		q.setParameter("sec_id", isbnNumber);

		List<PublicationWaitlist> wtList = q.getResultList();


		PublicationWaitlist pb = new PublicationWaitlist(patronid,isbnNumber, new Date(), flag );
		pb.setPatron(loggedInPatron);
		if(wtList.size() == 0){
			DBUtils.persist(pb);
		}
		else{
			PublicationWaitlist wl = wtList.get(0);
			Date maxDate  = wl.getEndTime();
			if(maxDate == null){

			}
			else{
				DateTime dt1= new DateTime(maxDate);
				DateTime dt2= dt1.plusMinutes(1);
				DateTime dt3= dt1.plusMinutes(30);
				pb.setStartTime(dt2.toDate());
				pb.setEndTime(dt3.toDate());

				String pubDetailQry = "SELECT d.title FROM BOOK_DETAIL d WHERE d.isbn_number=? "
						+ "UNION "
						+ "SELECT d.title FROM JOURNAL_DETAIL d WHERE d.issn_number=? "
						+ "UNION "
						+ "SELECT d.title FROM CONFERENCE_PROCEEDING_DETAIL d WHERE d.conf_num=?";

				Query publicationDetailQuery = entitymanager.createNativeQuery(pubDetailQry);
				publicationDetailQuery.setParameter(1, isbnNumber);
				publicationDetailQuery.setParameter(2, isbnNumber);
				publicationDetailQuery.setParameter(3, isbnNumber);

				String title = (String)publicationDetailQuery.getSingleResult();

				PublicationManager.sendJournalAvailabilityMail(loggedInPatron.getEmailAddress(), pb.getStartTime().toString(), pb.getEndTime().toString(), title);
			}

			DBUtils.persist(pb);
		}

		entitymanager.close();
		emfactory.close();
		System.out.println("The item you have requested is not avlble. You are on waitlist and will be notified when the item is available");

	}

	private void addJournalToAssetCheckout(String pub_format, boolean isStudent, Journal journal, Patron loggedInPatron, BigDecimal ast_type) {
		AssetCheckout astChkOut = new AssetCheckout();

		Date issueDate = new Date();
		DateTime dt1 = new DateTime(issueDate);
		DateTime dt2 = null;
		Date dueDate ;

		if(pub_format.equals("Physical copy")){
			//			System.out.println("HARD\n\n\n");
			dt2 = dt1.plusHours(12);


			dueDate = dt2.toDate();
			astChkOut.setAssetSecondaryId(journal.getDetails().getIssnNumber());
			astChkOut.setAsset(journal);
			astChkOut.setIssueDate(issueDate );
			astChkOut.setDueDate(dueDate);
			astChkOut.setPatron(loggedInPatron);
			DBUtils.persist(astChkOut);
			//			clearConsole();
			System.out.println("The item has been checked out: The return time is :"+ dueDate);


		}
		else{
			//				System.out.println("SOFT\n\n\n");
			dt2= dt1.plusMinutes(0);

			astChkOut.setAssetSecondaryId(journal.getDetails().getIssnNumber());
			astChkOut.setAsset(journal);
			astChkOut.setIssueDate(issueDate );
			astChkOut.setDueDate(issueDate);
			astChkOut.setReturnDate(issueDate);
			astChkOut.setPatron(loggedInPatron);
			DBUtils.persist(astChkOut);
			//			clearConsole();
			System.out.println("The item has been checked out: The return time is : N/A");
		}
	}



	/*public void checkout(int bookNo) {

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

		List<AssetCheckout> asc = q.getResultList();

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
	}*/
}