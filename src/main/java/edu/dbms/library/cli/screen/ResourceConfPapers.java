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
import edu.dbms.library.cli.route.Route;
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.AssetCheckout;
import edu.dbms.library.entity.Patron;
import edu.dbms.library.entity.reserve.PublicationWaitlist;
import edu.dbms.library.entity.resource.Book;
import edu.dbms.library.entity.resource.ConferenceProceeding;
import edu.dbms.library.entity.resource.Journal;
import edu.dbms.library.session.SessionUtils;

public class ResourceConfPapers extends BaseScreen {

	List<ConferenceProceeding> confPapers = new ArrayList<ConferenceProceeding>();
	Object[][] confPprs;
	Object[][] confPprs1;

	public ResourceConfPapers() {
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

		displayConfProcs();
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
				{Constant.OPTION_ASSET_CHECKOUT},
				{Constant.OPTION_EXIT},
				{"0 to exit the menu."},
				{"Enter the Paper no. to checkout the book"}
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

	public void displayConfProcs() {
		// opt1: Display only those books tht a patron can checkout. 
		// if the patron has been issued some books. remove thos ISBN number wala books from the display list..
		// Display conditions for REserved books??
//		confPapers = getPapersList(); // publisher is not joined with books yet.
		String[] title = {"CONF_NUM", "TITLE", "CONF NAME", "AUTHOR(S)", "PUB_YEAR"};

		Object[][] cp = getPapersList();  
		
		TextTable tt = new TextTable(title, cp);
		tt.setAddRowNumbering(true);
		tt.printTable();
	}


	protected Object[][] getPapersList() {

		Patron loggedInPatron = (Patron) DBUtils.findEntity(Patron.class, SessionUtils.getPatronId(), String.class);

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				"main");
		EntityManager entitymanager = emfactory.createEntityManager( );

		String query = " SELECT conf1.CONF_PROC_ID, conf1.CONFNUMBER, pub1.TITLE, CONF1.CONFNAME,  " 
				+" rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS, pub1.PUBLICATIONYEAR, pub1.PUBLICATIONFORMAT   "
				+" FROM asset ast1, CONF_PROCEEDING conf1, publication pub1 , Author auth1, PUBLICATION_AUTHOR pa1 " 
				+" WHERE conf1.CONF_PROC_ID = ast1.asset_id "
				+" AND pub1.publication_id = ast1.asset_id "
				+" AND pub1.publication_id = ast1.asset_id "
				+" AND ast1.asset_type = 2 "
				+" AND AUTH1.ID = PA1.AUTHORS_ID "  
				+" AND PA1.PUBLICATIONS_ASSET_ID = conf1.CONF_PROC_ID " 
				+" Group By conf1.CONF_PROC_ID, conf1.CONFNUMBER,CONF1.CONFNAME, pub1.TITLE, pub1.PUBLICATIONFORMAT, pub1.PUBLICATIONYEAR "
		+" MINUS "
		+" SELECT conf1.CONF_PROC_ID, conf1.CONFNUMBER, pub1.TITLE, CONF1.CONFNAME,  " 
		+" rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS, pub1.PUBLICATIONYEAR, pub1.PUBLICATIONFORMAT   "
		+" FROM asset ast1, CONF_PROCEEDING conf1, publication pub1 , Author auth1, PUBLICATION_AUTHOR pa1 " 
		+" WHERE conf1.CONF_PROC_ID = ast1.asset_id "
		+" AND pub1.publication_id = ast1.asset_id "
		+" AND pub1.publication_id = ast1.asset_id "
		+" AND ast1.asset_type = 2 "
		+" AND AUTH1.ID = PA1.AUTHORS_ID "  
		+" AND PA1.PUBLICATIONS_ASSET_ID = conf1.CONF_PROC_ID "
		+" AND conf1.CONFNUMBER IN "
		+"     (SELECT wtlist.PUBSECONDARYID " 
		+"         FROM publication_waitlist wtlist " 
		+"         WHERE (wtlist.PATRONID = ?)) "
		+" Group By conf1.CONF_PROC_ID, conf1.CONFNUMBER,CONF1.CONFNAME, pub1.TITLE, pub1.PUBLICATIONFORMAT, pub1.PUBLICATIONYEAR "
		+" MINUS "
		+" SELECT conf1.CONF_PROC_ID, conf1.CONFNUMBER, pub1.TITLE, CONF1.CONFNAME,  " 
		+" rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS, pub1.PUBLICATIONYEAR, pub1.PUBLICATIONFORMAT   "
		+" FROM asset ast1, CONF_PROCEEDING conf1, publication pub1 , Author auth1, PUBLICATION_AUTHOR pa1 "
		+" WHERE ( EXISTS "
		+"         (SELECT * FROM asset_checkout astchkt, publication_waitlist wtlist, asset_checkout_constraint astchkcon " 
		+"             WHERE (((((astchkt.asset_secondary_id = conf1.CONFNUMBER) "
		+"                 AND (astchkt.patron_id = ?)) " 
		+"                 AND (wtlist.PATRONID <> ?)) "
		+"                 AND (wtlist.PUBSECONDARYID = conf1.CONFNUMBER)) "
		+" AND (astchkt.ID = astchkcon.ASSETCHECKOUT_ID)))  "
		+" AND (((conf1.CONF_PROC_ID = ast1.asset_id) "
		+" AND ((pub1.publication_id = ast1.asset_id) " 
		+" AND (pub1.publication_id = ast1.asset_id))) " 
		+" AND (ast1.asset_type = 2))) "
		+" AND AUTH1.ID = PA1.AUTHORS_ID " 
		+" AND PA1.PUBLICATIONS_ASSET_ID = conf1.CONF_PROC_ID " 
		+" Group By conf1.CONF_PROC_ID, conf1.CONFNUMBER, pub1.TITLE, CONF1.CONFNAME,pub1.PUBLICATIONYEAR, pub1.PUBLICATIONFORMAT  "; 

		
		
		
		
//				+" MINUS "
//				+" SELECT jour1.journal_id, jour1.ISSNNUMBER, pub1.TITLE, pub1.PUBLICATIONFORMAT , pub1.PUBLICATIONYEAR," 
//				+" rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS "
//				+" FROM asset ast1, journal jour1, publication pub1 , Author auth1, PUBLICATION_AUTHOR pa1 "
//				+" WHERE  jour1.journal_id = ast1.asset_id  "
//				+" AND pub1.publication_id = ast1.asset_id "
//				+" AND pub1.publication_id = ast1.asset_id "
//				+" AND AUTH1.ID = PA1.AUTHORS_ID "
//				+" AND PA1.PUBLICATIONS_ASSET_ID = jour1.journal_id " 
//				+" AND ast1.asset_type = 3 "
//				+" AND jour1.ISSNNUMBER IN "
//				+"     (SELECT wtlist.PUBSECONDARYID " 
//				+"         FROM publication_waitlist wtlist " 
//				+"         WHERE (wtlist.PATRONID = ?)) "
//				+" Group By jour1.journal_id, jour1.ISSNNUMBER, pub1.TITLE, pub1.PUBLICATIONFORMAT,pub1.PUBLICATIONYEAR "
//				+" MINUS "
//				+" SELECT jour1.journal_id, jour1.ISSNNUMBER, pub1.TITLE, pub1.PUBLICATIONFORMAT , pub1.PUBLICATIONYEAR," 
//				+" rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS "
//				+" FROM asset ast1, journal jour1, publication pub1 , Author auth1, PUBLICATION_AUTHOR pa1 "
//				+" WHERE ( EXISTS "
//				+"         (SELECT * FROM asset_checkout astchkt, publication_waitlist wtlist, asset_checkout_constraint astchkcon " 
//				+"             WHERE (((((astchkt.asset_secondary_id = jour1.ISSNNUMBER) "
//				+"                 AND (astchkt.patron_id = ?)) " 
//				+"                 AND (wtlist.PATRONID <> ?)) "
//				+"                 AND (wtlist.PUBSECONDARYID = jour1.ISSNNUMBER)) "
//				+" AND (astchkt.ID = astchkcon.ASSETCHECKOUT_ID)))  "
//				+" AND (((jour1.journal_id = ast1.asset_id) "
//				+" AND ((pub1.publication_id = ast1.asset_id) " 
//				+" AND (pub1.publication_id = ast1.asset_id))) " 
//				+" AND (ast1.asset_type = 3))) "
//				+" AND AUTH1.ID = PA1.AUTHORS_ID " 
//				+" AND PA1.PUBLICATIONS_ASSET_ID = jour1.journal_id " 
//				+" Group By jour1.journal_id, jour1.ISSNNUMBER, pub1.TITLE, pub1.PUBLICATIONFORMAT, pub1.PUBLICATIONYEAR"; 

		Query q = entitymanager.createNativeQuery(query);

		String w1 = "SELECT CP from ConferenceProceeding CP";
		
		Query q1 = entitymanager.createQuery(w1);
		
		q.setParameter(1, loggedInPatron.getId()).setParameter(2, loggedInPatron.getId()).setParameter(3, loggedInPatron.getId());


		List obj1 = q.getResultList();
		List onj2 = q1.getResultList();
		entitymanager.close();
		emfactory.close();
		
		int i =0;
		confPprs  = new Object[obj1.size()][5];
		confPprs1 = new Object[obj1.size()][];
		while(i<obj1.size()){
		Object[] arr = (Object[]) obj1.get(i);
		confPprs1[i] =  arr;
		confPprs[i][0]=  arr[1];
		confPprs[i][1]=  arr[2];
		confPprs[i][2]=  arr[3];
		confPprs[i][3]=  arr[4];
		confPprs[i][4]=  arr[5];
				
		i++;
		}
		
		
		return confPprs ;



	}


	public void checkout(int bookNo) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("main");
		EntityManager entitymanager = emfactory.createEntityManager( );


//		Journal journal = journals.get(bookNo-1);
		String cpId  = (String)confPprs1[bookNo-1][0];
		String query = "SELECT cp FROM "+AssetCheckout.class.getName()
				+" cp where cp.asset.id = :num and cp.returnDate IS NULL";
		Query q = entitymanager.createQuery(query);
		
		q.setParameter("num", cpId);
		
		List<AssetCheckout> asc = (List<AssetCheckout>) q.getResultList();

		long count = asc.size();

		System.out.println("No Of results:"+ count);
		entitymanager.close();
		emfactory.close();

		ConferenceProceeding cp1 = (ConferenceProceeding) DBUtils.findEntity(ConferenceProceeding.class, cpId, String.class);
		
		boolean isStudent = SessionUtils.isStudent();

		Patron loggedInPatron = (Patron) DBUtils.findEntity(Patron.class, SessionUtils.getPatronId(), String.class);
		if(asc.size()==0){ //resource avlble in library
			AssetCheckout astChkOut = new AssetCheckout();

			Date issueDate = new Date();
			DateTime dt1 = new DateTime(issueDate);
			DateTime dt2;
			if (isStudent)
				dt2 = dt1.plusDays(14);
			else
				dt2 = dt1.plusMonths(1);

			Date dueDate = dt2.toDate();

			astChkOut.setAssetSecondaryId(cp1.getConfNumber());
			astChkOut.setAsset(cp1);
			astChkOut.setIssueDate(issueDate );
			astChkOut.setDueDate(dueDate);
			astChkOut.setPatron(loggedInPatron);
			DBUtils.persist(astChkOut);
			System.out.println("The item has been checked out: The return time is :"+ dueDate);

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
				PublicationWaitlist pb = new PublicationWaitlist(loggedInPatron.getId(),cp1.getConfNumber(), new Date(), flag );

				DBUtils.persist(pb); 

				System.out.println("The item you have requested is not avlble. You are on waitlist");

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
