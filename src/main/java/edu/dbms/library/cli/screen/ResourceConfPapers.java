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
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.db.DBUtils;
import edu.dbms.library.db.manager.LoginManager;
import edu.dbms.library.db.manager.PublicationManager;
import edu.dbms.library.entity.AssetCheckout;
import edu.dbms.library.entity.Patron;
import edu.dbms.library.entity.reserve.PublicationWaitlist;
import edu.dbms.library.entity.resource.ConferenceProceeding;
import edu.dbms.library.session.SessionUtils;

public class ResourceConfPapers extends BaseScreen {

	List<ConferenceProceeding> confPapers = new ArrayList<ConferenceProceeding>();
	Object[][] confPprs;
	Object[][] confPprs1;

	public ResourceConfPapers() {
		super();
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
			int confProcNo = (int)o;
			String bookId  = (String) confPprs1[confProcNo-1][0];
			String pub_format = (String) confPprs1[confProcNo-1][6];

			checkout(bookId, pub_format);

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
		Object[][] confPprsTmp = new Object[obj1.size()][7];
		
		confPprs1 = new Object[obj1.size()][];
		int ctr=0;
		while(i<obj1.size()){
			Object[] arr = (Object[]) obj1.get(i);
			if(myChkoutList.contains((String)arr[0])){
				i++;
				continue;
			}
			else if(chkoutList.contains((String)arr[0])){
				confPprsTmp[ctr][6] = "ISSUED";
			}
			
			confPprs1[ctr] =  arr;
			confPprsTmp[ctr][0]=  arr[2];
			confPprsTmp[ctr][1]=  arr[3];
			confPprsTmp[ctr][2]=  arr[4];
			confPprsTmp[ctr][3]=  arr[7];
			confPprsTmp[ctr][4]=  arr[5];
			confPprsTmp[ctr][5]=  arr[6];
			i++; 
			ctr++;
		}

		
		confPprs  = new Object[ctr][7];		
		for(i = 0; i < ctr; i++) {
			confPprs[i] = confPprsTmp[i];
		}

		return confPprs ;



	}

	public void checkout(String confProcId, String pub_format) {

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

			//Get Conf Proc details
			ConferenceProceeding confProc = (ConferenceProceeding) DBUtils.findEntity(ConferenceProceeding.class, confProcId, String.class);
			//Query that returns a result if the book is currently checked out
			String confProcAvlbl = "SELECT cp FROM "
					+ AssetCheckout.class.getName()
					+ " cp where cp.asset.id = :num and cp.returnDate IS NULL";

			Query qcpAvlbl = entitymanager.createQuery(confProcAvlbl);
			qcpAvlbl.setParameter("num", confProcId);
			List<AssetCheckout> isCheckedOutResult = qcpAvlbl.getResultList();

			//Query that returns all the waitlisted entries for this secondary ID
			String query = "SELECT cp FROM PublicationWaitlist cp"
					+" where cp.key.pubSecondaryId = :sec_id ";

			Query q = entitymanager.createQuery(query);
			q.setParameter("sec_id", confProc.getDetails().getConfNumber());
			List<PublicationWaitlist> wtList = q.getResultList();

			//If there is no waitlist
			if(wtList.size() == 0 ){
				if(isCheckedOutResult.size()==0){ //No one has checked out, so checkout the conf Proc to this user
				
					try{
						addConfProcToAssetCheckout(pub_format, isStudent,confProc, loggedInPatron);
					}
					catch(Exception e){
						e.printStackTrace();
						System.out.println("Some error occured");
					}

				} else {// Some one has the conf proc checked out
					
					//Query to check if this user has the conf proc checked out
					String query04 = "SELECT cp FROM "+AssetCheckout.class.getName()
							+" cp where cp.asset.id = :num  AND cp.patron.id = :pat_id and cp.returnDate IS NULL";

					Query q4 = entitymanager.createQuery(query04);
					q4.setParameter("num", confProcId).setParameter("pat_id", loggedInPatron.getId());
					List<AssetCheckout> isCheckedOutByThisUserResult = q4.getResultList();
					
					if(isCheckedOutByThisUserResult.size() == 0){
						//Add user to waitlist as the conf proc is checked out by someone
						addToWaitList(isStudent, loggedInPatron.getId(),loggedInPatron, confProc.getDetails().getConfNumber());
					}
					else{
						updateAssetCheckout(entitymanager, isCheckedOutByThisUserResult.get(0),pub_format,isStudent);
					}
				}
			} else {//If there is a waitlist for this conf proc
				
				boolean isCurrUserinWaitList = false;
				PublicationWaitlist waitingUser = null;
				//check if this user is in waitlist
				for(PublicationWaitlist wlT : wtList){
					if(wlT.getPatron().getId() == loggedInPatron.getId()){
						isCurrUserinWaitList = true;
						waitingUser = wlT;
						break;
					}
				}
				
				if(!isCurrUserinWaitList){	//User is not in waitlist
					addToWaitList(isStudent, loggedInPatron.getId(),loggedInPatron, confProc.getDetails().getConfNumber());
				} else { // user is in waitlist....check if conf proc avlble then chkout else do nothing

					//Query to check if the conf proc is available and this person is in waitlist
					String query01 = "SELECT cp FROM "+AssetCheckout.class.getName()
							+" cp where cp.asset.id = :num and cp.returnDate IS NULL";
					
					Query q0 = entitymanager.createQuery(query01);
					q0.setParameter("num", confProcId);
					List<AssetCheckout> asc1 = q0.getResultList();
					
					if(asc1.size()==0){ //conf proc is avlble
						Date startTime = waitingUser.getStartTime();
						Date endTime = waitingUser.getEndTime();
						Date currTime = new Date();
						//If this guys assigned checkout time is now
						if(startTime.before(currTime) && endTime.after(currTime)){
							try{
								addConfProcToAssetCheckout(pub_format, isStudent, confProc, loggedInPatron);
								String removequery = "DELETE  FROM PUBLICATION_WAITLIST pw "
										+"  where pw.PUB_SECONDARY_ID = ? and  PATRON_ID = ? ";

								Query retq0 = entitymanager.createNativeQuery(removequery);
								retq0.setParameter(1, confProc.getDetails().getConfNumber()).setParameter(2, loggedInPatron.getId());

								entitymanager.getTransaction().begin();
								retq0.executeUpdate();
								entitymanager.getTransaction().commit();

							}
							catch(Exception e){
								e.printStackTrace();
								System.out.println("The conference proceeding is already issued by you...");
							}
						} else 
							System.out.println("You can not checkout the conference proceeding at this time");
					} else{
						//Do nothing
						System.out.println(" You are already in waitlist. The conference proceeding hasnt been returned yet. You will be notified");
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
	
	private void updateAssetCheckout(EntityManager entitymanager, AssetCheckout assetCheckout, String pub_format, boolean isStudent) {


		Date issueDate = new Date();
		DateTime dt1 = new DateTime(issueDate);
		DateTime dt2 = null;
		Date dueDate ;

		if(pub_format.equals("Physical copy")){
			if (isStudent ){
				dt2 = dt1.plusHours(12);
			}
			else{
				dt2 = dt1.plusHours(12);
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

			System.out.println("The item has been checked out: The return time is :"+ dueDate);
		}


	}

	private void addToWaitList(boolean isStudent, String patronid, Patron loggedInPatron, String confNumber) {
		// TODO Auto-generated method stub

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager entitymanager = emfactory.createEntityManager( );

		int flag = 0;
		if (isStudent) flag =1;

		String query = "SELECT cp FROM PublicationWaitlist cp"
				+" where cp.key.pubSecondaryId = :sec_id ORDER BY cp.requestDate desc, cp.isStudent desc";

		Query q = entitymanager.createQuery(query);
		q.setParameter("sec_id", confNumber);
		List<PublicationWaitlist> wtList = q.getResultList();

		PublicationWaitlist pb = new PublicationWaitlist(patronid, confNumber, new Date(), flag );
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
				publicationDetailQuery.setParameter(1, confNumber);
				publicationDetailQuery.setParameter(2, confNumber);
				publicationDetailQuery.setParameter(3, confNumber);

				String title = (String)publicationDetailQuery.getSingleResult();

				PublicationManager.sendConfProcAvailabilityMail(loggedInPatron.getEmailAddress(), pb.getStartTime().toString(), pb.getEndTime().toString(), title);
			}

			DBUtils.persist(pb);
		}
		
		entitymanager.close();
		emfactory.close();
		System.out.println("The item you have requested is not avlble. You are on waitlist and will be notified when the item is available");

	}

	private void addConfProcToAssetCheckout(String pub_format, boolean isStudent, ConferenceProceeding confProc, Patron loggedInPatron) {
		AssetCheckout astChkOut = new AssetCheckout();

		Date issueDate = new Date();
		DateTime dt1 = new DateTime(issueDate);
		DateTime dt2 = null;
		Date dueDate ;

		if(pub_format.equals("Physical copy")){
			if (isStudent ){
				dt2 = dt1.plusHours(12);
			}
			else{
				dt2 = dt1.plusHours(12);
			}

			dueDate = dt2.toDate();
			astChkOut.setAssetSecondaryId(confProc.getDetails().getConfNumber());
			astChkOut.setAsset(confProc);
			astChkOut.setIssueDate(issueDate);
			astChkOut.setDueDate(dueDate);
			astChkOut.setPatron(loggedInPatron);
			DBUtils.persist(astChkOut);
			System.out.println("The item has been checked out: The return time is :"+ dueDate);
		}
		else{
			dt2= dt1.plusMinutes(0);

			astChkOut.setAssetSecondaryId(confProc.getDetails().getConfNumber());
			astChkOut.setAsset(confProc);
			astChkOut.setIssueDate(issueDate);
			astChkOut.setDueDate(issueDate);
			astChkOut.setReturnDate(issueDate);
			astChkOut.setPatron(loggedInPatron);
			DBUtils.persist(astChkOut);
			System.out.println("The item has been checked out: The return time is : N/A");
		}
	}		
}
