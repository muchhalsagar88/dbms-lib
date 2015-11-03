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
import edu.dbms.library.db.manager.LoginManager;
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

	public void displayConfProcs() {
		// opt1: Display only those books tht a patron can checkout.
		// if the patron has been issued some books. remove thos ISBN number wala books from the display list..
		// Display conditions for REserved books??
		//		confPapers = getPapersList(); // publisher is not joined with books yet.
		String[] title = {"CONF_NUM", "TITLE", "CONF NAME", "AUTHOR(S)", "PUB_YEAR", "Format","STATUS"};

		Object[][] cp = getPapersList();

		TextTable tt = new TextTable(title, cp);
		tt.setAddRowNumbering(true);
		tt.printTable();
	}


	protected Object[][] getPapersList() {

		Patron loggedInPatron = (Patron) DBUtils.findEntity(Patron.class, SessionUtils.getPatronId(), String.class);

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				"main", DBUtils.getPropertiesMap());
		EntityManager entitymanager = emfactory.createEntityManager( );

		String query = " SELECT A1.ASSET_ID , CONF1.CONF_PROC_ID, CONF1.CONF_NUM, CPD1.TITLE,CPD1.CONFERENCENAME,CPD1.PUB_YEAR, P1.PUBLICATIONFORMAT  "
				+" ,rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS  "
				+" from CONF_PROCEEDING conf1, Publication p1,   CONFERENCE_PROCEEDING_DETAIL cpd1 , Asset a1 ,  Author auth1, CONF_PROC_AUTHOR cpa1    "
				+"						 		  WHERE CONF1.CONF_PROC_ID = a1.ASSET_ID   "
				+"						 		  AND a1.ASSET_ID = P1.PUBLICATION_ID   "
				+"						 		  AND CONF1.CONF_NUM = CPD1.CONF_NUM   "
				+"						 		  AND CPA1.CONF_NUM = CONF1.CONF_NUM		     "
				+"						 		  AND AUTH1.ID = CPA1.AUTHOR_ID    "
//				+ " AND CONF1.CONF_NUM NOT IN  (SELECT wtlist.PUBSECONDARYID  FROM publication_waitlist wtlist  WHERE (wtlist.PATRONID = ?))	"
				+"						 		  GROUP BY A1.ASSET_ID , CONF1.CONF_PROC_ID, CONF1.CONF_NUM, CPD1.TITLE,CPD1.CONFERENCENAME,CPD1.PUB_YEAR, P1.PUBLICATIONFORMAT  ";
//				+" MINUS "
//				+" SELECT A1.ASSET_ID , CONF1.CONF_PROC_ID, CONF1.CONF_NUM, CPD1.TITLE,CPD1.CONFERENCENAME,CPD1.PUB_YEAR, P1.PUBLICATIONFORMAT      "
//				+" ,rtrim (xmlagg (xmlelement (e, AUTH1.NAME || ',')).extract ('//text()'), ',') AUTHORS,  "
//				+ " (CASE WHEN ( ASC1.RETURN_DATE is NULL  AND ASC1.ASSET_ASSET_ID = A1.ASSET_ID ) THEN 'ISSUED' ELSE 'AVLBLE' END) as STATUS   "
//				+" from CONF_PROCEEDING conf1, Publication p1,   CONFERENCE_PROCEEDING_DETAIL cpd1 , Asset a1 ,  Author auth1, CONF_PROC_AUTHOR cpa1 , ASSET_CHECKOUT asc1   "
//				+"						 		  WHERE CONF1.CONF_PROC_ID = a1.ASSET_ID   "
//				+"						 		  AND a1.ASSET_ID = P1.PUBLICATION_ID   "
//				+"						 		  AND ( ASC1.ASSET_ASSET_ID(+) = A1.ASSET_ID   "
//				+"						 		  	  )    "
//				+"						 		  AND CONF1.CONF_NUM = CPD1.CONF_NUM   "
//				+"						 		  AND CPA1.CONF_NUM = CONF1.CONF_NUM		     "
//				+"						 		  AND AUTH1.ID = CPA1.AUTHOR_ID    "
//				+"						 		  AND conf1.CONF_NUM IN   "
//				+"						 		  			   ( SELECT ASSET_SECONDARY_ID FROM asset_checkout astchkt WHERE astchkt.patron_id = ?     "
//				+"						 					AND (astchkt.RETURN_DATE is NULL  )  )   "
//				+"						 		  GROUP BY A1.ASSET_ID , CONF1.CONF_PROC_ID, CONF1.CONF_NUM, CPD1.TITLE,CPD1.CONFERENCENAME,CPD1.PUB_YEAR, P1.PUBLICATIONFORMAT, ASC1.ID ,ASC1.RETURN_DATE, ASC1.ISSUE_DATE, ASC1.ASSET_ASSET_ID " ;		


		Query q = entitymanager.createNativeQuery(query);

		q.setParameter(1, loggedInPatron.getId()).setParameter(2, loggedInPatron.getId()).setParameter(3, loggedInPatron.getId());

		String query0 = "SELECT cp.asset.id FROM "+AssetCheckout.class.getName()
				+" cp where cp.returnDate IS NULL";

		String query1 = "SELECT cp.asset.id FROM "+AssetCheckout.class.getName()
				+" cp where cp.returnDate IS NULL AND cp.patron.id = :id1";

		Query q1 = entitymanager.createQuery(query0);
		Query q2 = entitymanager.createQuery(query1).setParameter("id1", loggedInPatron.getId());
		
		
		//list of all issued assets
		List<String> chkoutList = (List<String>) q1.getResultList();
		List<String> myChkoutList = (List<String>) q2.getResultList();


		List obj1 = q.getResultList();
		entitymanager.close();
		emfactory.close();

		int i =0;
		confPprs  = new Object[obj1.size()][7];
		confPprs1 = new Object[obj1.size()][];
		int ctr=0;
		while(i<obj1.size()){
			Object[] arr = (Object[]) obj1.get(i);
			if(myChkoutList.contains((String)arr[0])){
				i++;
				continue;
			}
			else if(chkoutList.contains((String)arr[0])){
				confPprs[ctr][6] = "ISSUED";
			}
			
			confPprs1[ctr] =  arr;
			confPprs[ctr][0]=  arr[2];
			confPprs[ctr][1]=  arr[3];
			confPprs[ctr][2]=  arr[4];
			confPprs[ctr][3]=  arr[7];
			confPprs[ctr][4]=  arr[5];
			confPprs[ctr][5]=  arr[6];
			i++; ctr++;
		}


		return confPprs ;



	}


	public void checkout(int bookNo) {

		if(LoginManager.isPatronAccountOnHold(SessionUtils.getPatronId())) {
			System.out.println("Your library privileges have been suspended. Please pay your dues to checkout assets.");
			return;
		}

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("main");
		EntityManager entitymanager = emfactory.createEntityManager( );

	//		Journal journal = journals.get(bookNo-1);
		String cpId  = (String)confPprs1[bookNo-1][0];
		String pub_sec_id = (String)confPprs1[bookNo-1][1];
		String pub_format = (String)confPprs1[bookNo-1][6];
		String patron_id = SessionUtils.getPatronId();
		boolean isStudent = SessionUtils.isStudent();

		String wailist_query = "SELECT pb FROM PublicationWaitlist pb WHERE pb.pubSecondaryId = :sec_id";
		Query q0 = entitymanager.createQuery(wailist_query);

		q0.setParameter("sec_id", pub_sec_id);

		List<PublicationWaitlist> wtList = (List<PublicationWaitlist>) q0.getResultList();

		Patron loggedInPatron = (Patron) DBUtils.findEntity(Patron.class, SessionUtils.getPatronId(), String.class);
		ConferenceProceeding cp1 = (ConferenceProceeding) DBUtils.findEntity(ConferenceProceeding.class, cpId, String.class);


		String query = "SELECT cp FROM "+AssetCheckout.class.getName()
				+" cp where cp.asset.id = :num and cp.returnDate IS NULL";
		Query q = entitymanager.createQuery(query);
		
		q.setParameter("num", cpId);
		
		List<AssetCheckout> asc = (List<AssetCheckout>) q.getResultList();
		long count = asc.size();
		System.out.println("No Of results:"+ count);

		if(wtList.size()==0  && asc.size() == 0){  //no waitlist for this secondary id && resource avlble in library
			// initiateCheckout();
			
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

					astChkOut.setAssetSecondaryId(cp1.getDetails().getConfNumber());
					astChkOut.setAsset(cp1);
					astChkOut.setIssueDate(issueDate );
					astChkOut.setDueDate(dueDate);
					astChkOut.setPatron(loggedInPatron);
					DBUtils.persist(astChkOut);
					System.out.println("The item has been checked out: The return time is :"+ dueDate);

				}
				else{

					System.out.println("SOFT\n\n\n");
					dt2= dt1.plusMinutes(0);

					astChkOut.setAssetSecondaryId(cp1.getDetails().getConfNumber());
					astChkOut.setAsset(cp1);
					astChkOut.setIssueDate(issueDate );
					astChkOut.setDueDate(issueDate);
					astChkOut.setReturnDate(issueDate);
					astChkOut.setPatron(loggedInPatron);
					DBUtils.persist(astChkOut);

					System.out.println("The item has been checked out: The return time is : N/A");

				}

			
		}
		else{

			boolean in_waiList= false;
			for (PublicationWaitlist w0: wtList){
				if(w0.getPatronId()==patron_id) {
					in_waiList = true;
					System.out.println("You have already requested for this book");
					entitymanager.close();
					emfactory.close();
					return;

				}
			}

			if(in_waiList== false){
				//put in waitlist
				int flag = 0;
				if (isStudent) flag =1;
				PublicationWaitlist pb = new PublicationWaitlist(loggedInPatron.getId(),cp1.getDetails().getConfNumber(), new Date(), flag );
				DBUtils.persist(pb); 
				System.out.println("The item you have requested is not avlble. You are on waitlist and will be notified when the item is available");
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
