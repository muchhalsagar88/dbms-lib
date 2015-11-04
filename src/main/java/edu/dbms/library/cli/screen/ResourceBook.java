package edu.dbms.library.cli.screen;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.joda.time.DateTime;

import dnl.utils.text.table.TextTable;
import edu.dbms.library.cli.Constant;
import edu.dbms.library.cli.route.Route;
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.db.DBUtils;
import edu.dbms.library.db.manager.LoginManager;
import edu.dbms.library.db.manager.PublicationManager;
import edu.dbms.library.entity.AssetCheckout;
import edu.dbms.library.entity.Course;
import edu.dbms.library.entity.Faculty;
import edu.dbms.library.entity.Patron;
import edu.dbms.library.entity.ReserveBook;
import edu.dbms.library.entity.ReserveBookKey;
import edu.dbms.library.entity.reserve.PublicationWaitlist;
import edu.dbms.library.entity.resource.Book;
import edu.dbms.library.entity.resource.Camera;
import edu.dbms.library.entity.resource.Journal;
import edu.dbms.library.entity.resource.PublicationFormat;
import edu.dbms.library.session.SessionUtils;
import edu.dbms.library.utils.MailUtils;

public class ResourceBook extends BaseScreen {

	//	List<Book> books = new ArrayList<Book>();
	Object[][] bks;
	Object[][] bks1;

	public ResourceBook() {
		super();
		//		options.put(1, new Route(RouteConstant.PATRON_PROFILE));
		//		options.put(2, new Route(RouteConstant.PATRON_RESOURCES));
		//		options.put(3, new Route(RouteConstant.PATRON_CHECKED_OUT));
		//		options.put(4, new Route(RouteConstant.PATRON_RES_REQUEST));
		//		options.put(5, new Route(RouteConstant.PATRON_NOTIFICATIONS));
		//		options.put(6, new Route(RouteConstant.PATRON_BALANCE));
		//		options.put(7, new Route(RouteConstant.LOGOUT));
	}

	@Override
	public void execute() {

		displayBooks();
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

			int bookNo = (int)o;
			String bookId  = (String)bks1[bookNo-1][0]; //assetId
			BigDecimal ast_type = (BigDecimal)bks1[bookNo-1][8];
			String pub_format = (String)bks1[bookNo-1][5];

			checkout(bookId, ast_type, pub_format);

			// update the approproate tables with respect to availibility. Assign return dates to items
			// checkout possible only for available items
			// if resource not available..put the request in waitlist
			// checkout rnew possible only if waitlist is empty -- update the due dates nd relevant data.
			//			System.out.println("The item has been checked out!!");
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

	public void displayBooks() {
		// opt1: Display only those books tht a patron can checkout.
		// if the patron has been issued some books. remove those ISBN number wala books from the display list if they are not in waitlist
		// Display conditions for REserved books??
		//		books = getBookList(); // publisher is not joined with books yet.
		clearConsole();
		String[] title = {"ISBN", "TITLE", "EDITION", "AUTHOR(S)", "PUB_YEAR", "PUBLISHER", "FORMAT", "STATUS"};

		bks = getBookList();
		int count = 0;

		TextTable tt = new TextTable(title, bks);
		tt.setAddRowNumbering(true);
		tt.printTable();
	}


	protected Object[][] getBookList() {

		Patron loggedInPatron = (Patron) DBUtils.findEntity(Patron.class, SessionUtils.getPatronId(), String.class);

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager entitymanager = emfactory.createEntityManager( );

		String _fin_q = "SELECT A1.ASSET_ID, B1.ISBN_NUMBER, BD1.TITLE, BD1.EDITION, BD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT, PB1.NAME,"
				+"Rtrim (Xmlagg (XMLELEMENT (e, AUTH1.NAME|| ',')).extract ('//text()'), ',') AUTHORS, A1.ASSET_TYPE "
				+" FROM   BOOK b1, PUBLICATION p1, BOOK_DETAIL bd1, ASSET a1,  PUBLISHER pb1,  AUTHOR auth1, BOOK_AUTHOR BA1 "
				+" WHERE  B1.BOOK_ID = a1.ASSET_ID AND a1.ASSET_ID = P1.PUBLICATION_ID  "
				+"       AND B1.ISBN_NUMBER = BD1.ISBN_NUMBER AND BD1.PUBLISHER_ID = PB1.ID  AND BA1.BOOK_ID = B1.ISBN_NUMBER "
				+"       AND AUTH1.ID = BA1.AUTHOR_ID "
				+"       AND B1.ISBN_NUMBER IN "
				+"       (  (  (  (SELECT ISBN_NUMBER FROM  BOOK_DETAIL"
				+"                                 MINUS "
				+"                                 SELECT R.BOOK_ISBN ISBN FROM   RESERVE_BOOK R ) "
				+"                               UNION "
				+"                         SELECT R.BOOK_ISBN ISBN FROM   RESERVE_BOOK R, ENROLL en "
				+"                                WHERE  SYSDATE BETWEEN R.FROM_DATE AND R.TODATE "
				+"                                       AND R.COURSE_ID = en.COURSE_ID "
				+"                                       AND en.STUDENT_ID = ?    ) "
				+"                        MINUS "
				+"                     (SELECT ASSET_SECONDARY_ID FROM   ASSET_CHECKOUT astchkt "
				+"                              WHERE  astchkt.PATRON_ID = ? AND ( astchkt.RETURN_DATE IS NULL )    )   )    ) "
				+" GROUP  BY A1.ASSET_ID, B1.ISBN_NUMBER, BD1.TITLE, BD1.EDITION, BD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT, PB1.NAME, A1.ASSET_TYPE";




		//1- list of books checkout by me but not waitlisted can be renewd.
		//2- list of books 	not checked out by me (minus) reserved books (plus) books reserved for my courses 
		Query q = entitymanager.createNativeQuery(_fin_q).setParameter(1, loggedInPatron.getId()).setParameter(2, loggedInPatron.getId());
		List obj1 = q.getResultList();

		String query0 = "SELECT cp.asset.id FROM "+AssetCheckout.class.getName()
				+" cp where cp.returnDate IS NULL";

		String query1 = "SELECT cp.asset.id FROM "+AssetCheckout.class.getName()
				+" cp where cp.returnDate IS NULL AND cp.patron.id = :id1";

		Query q1 = entitymanager.createQuery(query0);
		Query q2 = entitymanager.createQuery(query1).setParameter("id1", loggedInPatron.getId());


		//list of all issued assets
		List<String> chkoutList = (List<String>) q1.getResultList();
		List<String> myChkoutList = (List<String>) q2.getResultList();



		entitymanager.close();
		emfactory.close();
		obj1.size();
		int i =0;
		bks  = new Object[obj1.size()][8];
		bks1 = new Object[obj1.size()][];
		while(i<obj1.size()){
			Object[] arr = (Object[]) obj1.get(i);
			if(myChkoutList.contains((String)arr[0])){
				i++;
				continue;
			}
			else if(chkoutList.contains((String)arr[0])){
				bks[i][7] = "ISSUED";
			}
			bks1[i] =  arr;
			bks[i][0]=  arr[1];
			bks[i][1]=  arr[2];
			bks[i][2]=  arr[3];
			bks[i][3]=  arr[7];
			bks[i][4]=  arr[4];
			bks[i][5]=  arr[6];
			bks[i][6]=  arr[5];

			i++;
		}

		//		List<Book> blist = new ArrayList<>();

		return bks;
	}

	//Delete all expired waitlisted entries
	private void removedExpiredWaitlists() {

		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager entityManager = emFactory.createEntityManager();

		String deleteExpiredWaitlistsString = "DELETE FROM PUBLICATION_WAITLIST"
				+ " WHERE START_TIME IS NOT NULL"
				+ " AND END_TIME IS NOT NULL"
				+ " AND END_TIME < SYSDATE()";

		Query viewRenewBookQuery = entityManager.createNativeQuery(deleteExpiredWaitlistsString);
		viewRenewBookQuery.executeUpdate();

		entityManager.close();
		emFactory.close();
	}

	private void sendWaitlistMailforSecondaryId(String pubSecondaryId) {

		String getPubWaitlistDetailsForEmailString = "SELECT"
				+ " FROM PUBLICATION_WAITLIST PW, BOOK_DETAIL BD, ";
	}

	private void sendWaitlistMail(String patronName, String patronEmailId, String pubDescription, Date startTime, Date endTime) {

		String emailSubject = "Waitlisted book " + pubDescription + " is now available!";
		String emailBody = "Hello " + patronName + ", \n"
				+ "The book: " + pubDescription + " that you requested for is currently available for checkout.\n"
				+ "You can now checkout this book between " 
				+ (new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(startTime)) 
				+ " and " 
				+ (new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(startTime)) 
				+ ".\n"
				+ "Note: The availability of the book is subject to other ahead of you in the waitlist.\n\n"
				+ "Have a great day,\n"
				+ "The Library Team";
		MailUtils.sendMail(patronEmailId, emailSubject, emailBody);
	}

	public void checkout(String bookId , BigDecimal ast_type, String pub_format) {

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
			Book book = (Book) DBUtils.findEntity(Book.class, bookId, String.class);
			//Query that returns a result if the book is currently checked out
			String bookAvlbl = "SELECT cp FROM "
					+ AssetCheckout.class.getName()
					+ " cp where cp.asset.id = :num and cp.returnDate IS NULL";

			Query qbkAvlbl = entitymanager.createQuery(bookAvlbl);
			qbkAvlbl.setParameter("num", bookId);
			List<AssetCheckout> isCheckedOutResult = qbkAvlbl.getResultList();

			//Query that returns all the waitlisted entries for this secondary ID
			String query = "SELECT cp FROM PublicationWaitlist cp"
					+" where cp.key.pubSecondaryId = :sec_id ";

			Query q = entitymanager.createQuery(query);
			q.setParameter("sec_id", book.getDetail().getIsbnNumber());
			List<PublicationWaitlist> wtList = q.getResultList();

			//If there is no waitlist
			if(wtList.size() == 0 ){
				if(isCheckedOutResult.size()==0){ //No one has checked out, so checkout the book to this user
				
					try{
						addBookToAssetCheckout(pub_format, isStudent, book, loggedInPatron, ast_type);
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
					q4.setParameter("num", bookId).setParameter("pat_id", loggedInPatron.getId());
					List<AssetCheckout> isCheckedOutByThisUserResult = q4.getResultList();
					
					if(isCheckedOutByThisUserResult.size() == 0){
						//Add user to waitlist as the book is checked out by someone
						addToWaitList(isStudent, loggedInPatron.getId(),loggedInPatron, book.getDetail().getIsbnNumber());
					}
					else{
						updateAssetCheckout(entitymanager, isCheckedOutByThisUserResult.get(0),pub_format,isStudent, ast_type, book.getDetail().getIsbnNumber());
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
					addToWaitList(isStudent, loggedInPatron.getId(),loggedInPatron, book.getDetail().getIsbnNumber());
				} else { // user is in waitlist....check if book avlble then chkout else donothing

					//Query to check if the book is available and this guy is in waitlist
					String query01 = "SELECT cp FROM "+AssetCheckout.class.getName()
							+" cp where cp.asset.id = :num and cp.returnDate IS NULL";
					
					Query q0 = entitymanager.createQuery(query01);
					q0.setParameter("num", bookId);
					List<AssetCheckout> asc1 = q0.getResultList();
					
					if(asc1.size()==0){ //book is avlble
						Date startTime = waitingUser.getStartTime();
						Date endTime = waitingUser.getEndTime();
						Date currTime = new Date();
						//If this guys assigned checkout time is now
						if(startTime.before(currTime) && endTime.after(currTime)){
							try{
								addBookToAssetCheckout(pub_format, isStudent, book, loggedInPatron, ast_type);
								String removequery = "DELETE  FROM PUBLICATION_WAITLIST pw "
										+"  where pw.PUB_SECONDARY_ID = ? and  PATRON_ID = ? ";

								Query retq0 = entitymanager.createNativeQuery(removequery);
								retq0.setParameter(1, book.getDetail().getIsbnNumber()).setParameter(2, loggedInPatron.getId());

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

	private void updateAssetCheckout(EntityManager entitymanager, AssetCheckout assetCheckout, String pub_format, boolean isStudent, BigDecimal ast_type, String isbnNo) {


		Date issueDate = new Date();
		DateTime dt1 = new DateTime(issueDate);
		DateTime dt2 = null;
		Date dueDate ;

		boolean resBook = false;
		String getResBooks = "SELECT BOOK_ISBN FROM RESERVE_BOOK";
		
	
		Query bookQuery = entitymanager.createNativeQuery(getResBooks);
		List<String> isbnList= bookQuery.getResultList();
		if(isbnList.contains(isbnNo)){
			resBook = true;
		}
	
	
		
		if(pub_format.equals("Physical copy")){
			//			System.out.println("HARD\n\n\n");
			if (isStudent ){
				if(resBook)
					dt2 = dt1.plusHours(4);
				else
					dt2 = dt1.plusDays(14);
			}
			else{
				dt2 = dt1.plusMonths(1);
			}

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

				PublicationManager.sendBookAvailabilityMail(loggedInPatron.getEmailAddress(), pb.getStartTime().toString(), pb.getEndTime().toString(), title);
			}

			DBUtils.persist(pb);
		}
		
		entitymanager.close();
		emfactory.close();
		System.out.println("The item you have requested is not avlble. You are on waitlist and will be notified when the item is available");

	}

	private void addBookToAssetCheckout(String pub_format, boolean isStudent, Book book, Patron loggedInPatron, BigDecimal ast_type) {
		AssetCheckout astChkOut = new AssetCheckout();

		boolean resBook = false;
		Date issueDate = new Date();
		DateTime dt1 = new DateTime(issueDate);
		DateTime dt2 = null;
		Date dueDate ;
		
		String getResBooks = "SELECT BOOK_ISBN FROM RESERVE_BOOK";
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());

		EntityManager entitymanager = emfactory.createEntityManager( );

		Query bookQuery = entitymanager.createNativeQuery(getResBooks);
		List<String> isbnList= bookQuery.getResultList();
		if(isbnList.contains(book.getDetail().getIsbnNumber())){
			resBook = true;
		}
		

		entitymanager.close();
		emfactory.close();

		
		
		if(pub_format.equals("Physical copy")){
			//			System.out.println("HARD\n\n\n");
			if (isStudent ){
				if(resBook)
					dt2 = dt1.plusHours(4);
				else
					dt2 = dt1.plusDays(14);
			}
			else{
				dt2 = dt1.plusMonths(1);
			}

			dueDate = dt2.toDate();
			astChkOut.setAssetSecondaryId(book.getDetail().getIsbnNumber());
			astChkOut.setAsset(book);
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

			astChkOut.setAssetSecondaryId(book.getDetail().getIsbnNumber());
			astChkOut.setAsset(book);
			astChkOut.setIssueDate(issueDate );
			astChkOut.setDueDate(issueDate);
			astChkOut.setReturnDate(issueDate);
			astChkOut.setPatron(loggedInPatron);
			DBUtils.persist(astChkOut);
			//			clearConsole();
			System.out.println("The item has been checked out: The return time is : N/A");
		}
	}


	//	public void reserveBook(int bookNo){
	//
	//
	//		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
	//				"main");
	//		EntityManager entitymanager = emfactory.createEntityManager( );
	//
	//
	//
	//		String sdate = null;
	//		Date startDate = null;
	//		String edate = null;
	//		Date endDate = null;
	//		String faculty_id =  SessionUtils.getPatronId();
	//		boolean is_stud =  SessionUtils.isStudent();
	//
	//		Faculty faculty = (Faculty) DBUtils.findEntity(Faculty.class, faculty_id, String.class);
	//		ArrayList<Course> cList = (ArrayList<Course>) faculty.getCourses();
	//		String query1 = "SELECT bk FROM Book bk"	;
	//		Query q = entitymanager.createQuery(query1);
	//
	//		List<Book> asc = (List<Book>) q.getResultList();
	//
	//
	//		while(true){
	//
	//			sdate = readInput("Enter Start Date in MM/DD/YYYY Format");
	//
	//			startDate = DBUtils.validateDate(sdate, "MM/dd/yyyy", true);
	//
	//			if(startDate==null){
	//				System.out.println("Enter a valid Start date(future date/time)");
	//				continue;
	//			}
	//			else
	//				break;
	//		}
	//
	//		while(true){
	//			edate = readInput("Enter End Date in MM/DD/YYYY Format");
	//
	//			endDate = DBUtils.validateDate(edate, "MM/dd/yyyy", true);
	//DateTime
	//			if(endDate==null){
	//				System.out.println("Enter a valid End date(future date/time)");
	//				continue;
	//			}
	//			else
	//				break;
	//		}
	//
	//		System.out.print("COURSE #: ");
	//		for(Course c:cList)
	//			System.out.print(c.getCourseId()+ " / ");
	//
	//		System.out.println("\n");
	//
	//		System.out.print("BOOK ISBN#: ");
	//		for(Book b:asc)
	//			System.out.print(b.getDetail().getIsbnNumber()+ " / ");
	//
	//		String crs_id = readInput("Enter CourseID");
	//		String book_id = readInput("Enter Book ISBN No");
	//
	//
	//
	//
	//			String insertStmnt = "Insert into RESERVE_BOOK (FROM_DATE, TODATE, BOOK_ISBN, COURSE_ID, FACULTY_ID)"
	//						+ "Values ( ?,?,?,?,?) ";
	//
	//				Query query = entitymanager.createNativeQuery(insertStmnt);
	//
	//				entitymanager.getTransaction( ).begin( );
	//
	//				query.setParameter(1, startDate);
	//				query.setParameter(2, endDate);
	//				query.setParameter(3, book_id);
	//				query.setParameter(4, crs_id);
	//				query.setParameter(5, faculty_id);
	//
	//				entitymanager.getTransaction().commit();
	//
	//
	//				entitymanager.close();
	//				emfactory.close();
	//
	//
	//
	//		Course c1 = new Course();
	//
	//
	//		Faculty f1 = new Faculty();
	//
	//
	//
	//		Book b1 = new Book();
	//
	//
	//
	//
	////		ReserveBookKey key1 = new ReserveBookKey();
	////		key1.setCourseId(c1.getCourseId());
	////		key1.setFacultyId(f1.getId());
	////		key1.setIsbnNumber(b1.getDetail().getIsbnNumber());
	////
	////		ReserveBook rb = new ReserveBook(key1, new Date(),new Date(), c1, f1);
	////		DBUtils.persist(rb);
	//
	//	}
}
