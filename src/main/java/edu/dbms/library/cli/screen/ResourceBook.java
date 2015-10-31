package edu.dbms.library.cli.screen;

import java.math.BigDecimal;
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
import edu.dbms.library.entity.AssetCheckout;
import edu.dbms.library.entity.Patron;
import edu.dbms.library.entity.reserve.PublicationWaitlist;
import edu.dbms.library.entity.resource.Book;
import edu.dbms.library.entity.resource.Camera;
import edu.dbms.library.entity.resource.Journal;
import edu.dbms.library.entity.resource.PublicationFormat;
import edu.dbms.library.session.SessionUtils;

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

	public void displayBooks() {
		// opt1: Display only those books tht a patron can checkout. 
		// if the patron has been issued some books. remove those ISBN number wala books from the display list if they are not in waitlist
		// Display conditions for REserved books??
		//		books = getBookList(); // publisher is not joined with books yet.
		String[] title = {"ISBN", "TITLE", "EDITION", "AUTHOR(S)", "PUB_YEAR", "PUBLISHER", "FORMAT", "STATUS"};

		bks = getBookList(); 
		int count = 0;

		TextTable tt = new TextTable(title, bks);
		tt.setAddRowNumbering(true);
		tt.printTable();
	}


	protected Object[][] getBookList() {

		Patron loggedInPatron = (Patron) DBUtils.findEntity(Patron.class, SessionUtils.getPatronId(), String.class);

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				"main");
		EntityManager entitymanager = emfactory.createEntityManager( );
		String qer1 =
				"SELECT A1.ASSET_ID ,  B1.ISBN_NUMBER, BD1.TITLE, BD1.EDITION, BD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT , PB1.NAME   "
						+"		  ,rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS,  (CASE WHEN ASC1.ID IS NULL THEN 'AVLBLE' ELSE 'ISSUED' END) as STATUS "
						+"		  from Book b1, Publication p1,   BOOK_DETAIL bd1, Asset a1 ,Publisher pb1 ,  Author auth1, BOOK_AUTHOR BA1 , ASSET_CHECKOUT asc1 "
						+"		  WHERE B1.BOOK_ID = a1.ASSET_ID "
						+"		  AND a1.ASSET_ID = P1.PUBLICATION_ID "
						+"		  AND ( ASC1.ASSET_ASSET_ID(+) = A1.ASSET_ID "
						+"		  AND ( ASC1.RETURN_DATE is NULL OR ASC1.RETURN_DATE=ASC1.ISSUE_DATE)	  )  "
						+"		  AND B1.ISBN_NUMBER = BD1.ISBN_NUMBER "
						+"		  AND BD1.PUBLISHER_ID = PB1.ID "
						+"		  AND BA1.BOOK_ID = B1.ISBN_NUMBER		   "
						+"		  AND AUTH1.ID = BA1.AUTHOR_ID "
						+"		  AND  B1.ISBN_NUMBER  NOT in  "
						+"		  (SELECT R.BOOK_ISBN ISBN FROM RESERVE_BOOK R)  "
						+"		  GROUP BY A1.ASSET_ID ,  B1.ISBN_NUMBER, BD1.TITLE, BD1.EDITION, BD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT , PB1.NAME,  ASC1.ID "
						+" UNION "
						+" SELECT A1.ASSET_ID ,  B1.ISBN_NUMBER, BD1.TITLE, BD1.EDITION, BD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT , PB1.NAME    "
						+"		  ,rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS, (CASE WHEN ASC1.ID IS NULL THEN 'AVLBLE' ELSE 'ISSUED' END) as STATUS "
						+"		  from Book b1, Publication p1,   BOOK_DETAIL bd1, Asset a1 ,Publisher pb1 ,  Author auth1, BOOK_AUTHOR BA1 , ASSET_CHECKOUT asc1 "
						+"		  WHERE B1.BOOK_ID = a1.ASSET_ID "
						+"		  AND a1.ASSET_ID = P1.PUBLICATION_ID "
						+"		  AND ( ASC1.ASSET_ASSET_ID(+) = A1.ASSET_ID "
						+"		  AND ( ASC1.RETURN_DATE is NULL OR ASC1.RETURN_DATE=ASC1.ISSUE_DATE)	  ) "
						+"		  AND B1.ISBN_NUMBER = BD1.ISBN_NUMBER "
						+"		  AND BD1.PUBLISHER_ID = PB1.ID "
						+"		  AND BA1.BOOK_ID = B1.ISBN_NUMBER		   "
						+"		  AND AUTH1.ID = BA1.AUTHOR_ID "
						+"		  AND  B1.ISBN_NUMBER  in  "
						+"		  (SELECT R.BOOK_ISBN ISBN FROM RESERVE_BOOK R, ENROLL en  "
						+"           WHERE SYSDATE between R.FROM_DATE AND R.TODATE "
						+"          AND R.COURSE_ID = en.COURSE_ID AND en.STUDENT_ID = ?  )   "
						+"		  GROUP BY A1.ASSET_ID ,  B1.ISBN_NUMBER, BD1.TITLE, BD1.EDITION, BD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT , PB1.NAME,  ASC1.ID "
						+" MINUS "
						+" SELECT A1.ASSET_ID ,  B1.ISBN_NUMBER, BD1.TITLE, BD1.EDITION, BD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT , PB1.NAME    "
						+"		  ,rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS, (CASE WHEN ASC1.ID IS NULL THEN 'AVLBLE' ELSE 'ISSUED' END) "
						+"		  from Book b1, Publication p1,   BOOK_DETAIL bd1, Asset a1 ,Publisher pb1 ,  Author auth1, BOOK_AUTHOR BA1 , ASSET_CHECKOUT asc1 "
						+"		  WHERE B1.BOOK_ID = a1.ASSET_ID "
						+"		  AND a1.ASSET_ID = P1.PUBLICATION_ID "
						+"		  AND ( ASC1.ASSET_ASSET_ID(+) = A1.ASSET_ID "
						+"		  AND ( ASC1.RETURN_DATE is NULL OR ASC1.RETURN_DATE=ASC1.ISSUE_DATE)	  ) "
						+"		  AND B1.ISBN_NUMBER = BD1.ISBN_NUMBER "
						+"		  AND BD1.PUBLISHER_ID = PB1.ID "
						+"		  AND BA1.BOOK_ID = B1.ISBN_NUMBER		   "
						+"		  AND AUTH1.ID = BA1.AUTHOR_ID   "
						+"            AND  B1.ISBN_NUMBER IN   "
						+"			   (SELECT PUBSECONDARYID FROM PUBLICATION_WAITLIST WHERE PATRONID = ? " 
						+" 					UNION "
						+" 				SELECT ASSET_SECONDARY_ID FROM asset_checkout astchkt WHERE astchkt.patron_id = ? "   
						+" 					AND (astchkt.RETURN_DATE is NULL  OR astchkt.RETURN_DATE=astchkt.ISSUE_DATE) )    "
						+"        GROUP BY A1.ASSET_ID ,  B1.ISBN_NUMBER, BD1.TITLE, BD1.EDITION, BD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT , PB1.NAME,  ASC1.ID ";
		//						+" MINUS		   "
		//						+" SELECT A1.ASSET_ID ,    B1.ISBN_NUMBER, BD1.TITLE, BD1.EDITION, BD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT , PB1.NAME   "
		//						+"		  ,rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS , A1.ASSET_TYPE"
		//						+"		  from Book b1, Publication p1,   BOOK_DETAIL bd1, Asset a1 ,Publisher pb1 ,  Author auth1, BOOK_AUTHOR BA1  "
		//						+"		   WHERE ( EXISTS  "
		//						+"		         (SELECT * FROM asset_checkout astchkt, publication_waitlist wtlist, asset_checkout_constraint astchkcon   "
		//						+"		             WHERE astchkt.asset_secondary_id = B1.ISBN_NUMBER  "
		//						+"		                AND astchkt.patron_id = ?   "
		//						+"		                AND wtlist.PATRONID <> ?  "
		//						+"		                 AND wtlist.PUBSECONDARYID = B1.ISBN_NUMBER) )   "
		//						+"		  AND B1.BOOK_ID = a1.ASSET_ID "
		//						+"		  AND a1.ASSET_ID = P1.PUBLICATION_ID "
		//						+"		  AND B1.ISBN_NUMBER = BD1.ISBN_NUMBER "
		//						+"		  AND BD1.PUBLISHER_ID = PB1.ID "
		//						+"		  AND BA1.BOOK_ID = B1.ISBN_NUMBER		   "
		//						+"		  AND AUTH1.ID = BA1.AUTHOR_ID "
		//						+"		  GROUP BY A1.ASSET_ID ,    B1.ISBN_NUMBER, BD1.TITLE, BD1.EDITION, BD1.PUBLICATIONYEAR, P1.PUBLICATIONFORMAT , PB1.NAME, A1.ASSET_TYPE ";		
		//		



		//1- list of books checkout by me but not waitlisted can be renewd.
		//2- list of books 	not checked out by me (minus) reserved books (plus) books reserved for my courses 
		Query q = entitymanager.createNativeQuery(qer1).setParameter(1, loggedInPatron.getId()).setParameter(2, loggedInPatron.getId()).setParameter(3, loggedInPatron.getId()).setParameter(4, loggedInPatron.getId());
		List obj1 = q.getResultList();



		entitymanager.close();
		emfactory.close();
		obj1.size();
		int i =0;
		bks  = new Object[obj1.size()][8];
		bks1 = new Object[obj1.size()][];
		while(i<obj1.size()){
			Object[] arr = (Object[]) obj1.get(i);
			bks1[i] =  arr;
			bks[i][0]=  arr[1];
			bks[i][1]=  arr[2];
			bks[i][2]=  arr[3];
			bks[i][3]=  arr[7];
			bks[i][4]=  arr[4];
			bks[i][5]=  arr[6];
			bks[i][6]=  arr[5];
			if(arr[5].toString().equals("Electronic copy"))
				bks[i][7] = " ";
			else
				bks[i][7]=  arr[8];
			

			i++;
		}

		//		List<Book> blist = new ArrayList<>();

		return bks;
	}

	public void checkout(int bookNo) {


		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				"main");
		EntityManager entitymanager = emfactory.createEntityManager( );

		//	Book book = books.get(bookNo-1);
		String bookId  = (String)bks1[bookNo-1][0];
		BigDecimal ast_type = (BigDecimal)bks1[bookNo-1][8];
		String pub_format = (String)bks1[bookNo-1][5];

		String query = "SELECT cp FROM "+AssetCheckout.class.getName()
				+" cp where cp.asset.id = :num and cp.returnDate IS NULL";


		Query q = entitymanager.createQuery(query);

		q.setParameter("num", bookId);

		List<AssetCheckout> asc = (List<AssetCheckout>) q.getResultList();
		if(asc.size()>1){
			System.out.println("Error...more than 1 entry found");
		}



		Book book = (Book) DBUtils.findEntity(Book.class, bookId, String.class);

		boolean isStudent = SessionUtils.isStudent();
		Patron loggedInPatron = (Patron) DBUtils.findEntity(Patron.class, SessionUtils.getPatronId(), String.class);

		if(asc.size()==0){ //resource avlble in library

			AssetCheckout astChkOut = new AssetCheckout();

			Date issueDate = new Date();
			DateTime dt1 = new DateTime(issueDate);
			DateTime dt2 = null;
			Date dueDate ;

			if(pub_format.equals("Physical copy")){
				System.out.println("HARD\n\n\n");
				if (isStudent ){
					if(ast_type.intValue() == 4)
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

				System.out.println("The item has been checked out: The return time is :"+ dueDate);

			}
			else{
				System.out.println("SOFT\n\n\n");
				dt2= dt1.plusMinutes(0);

				astChkOut.setAssetSecondaryId(book.getDetail().getIsbnNumber());
				astChkOut.setAsset(book);
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
				PublicationWaitlist pb = new PublicationWaitlist(loggedInPatron.getId(),book.getDetail().getIsbnNumber(), new Date(), flag );

				DBUtils.persist(pb); 

				System.out.println("The item you have requested is not avlble. You are on waitlist");

			}

		}



		entitymanager.close();
		emfactory.close();



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
