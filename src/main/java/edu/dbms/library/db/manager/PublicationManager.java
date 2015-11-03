package edu.dbms.library.db.manager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.AssetCheckout;
import edu.dbms.library.entity.reserve.PublicationWaitlist;
import edu.dbms.library.utils.MailUtils;

public class PublicationManager {

	public static void returnPublication(long assetCheckoutId) {

		removedExpiredWaitlists();
		String assetSecId = checkinPublication(assetCheckoutId);
		updateWaitlistedPatronsOnReturn(assetSecId);
	}

	//Delete all expired waitlisted entries
	public static void removedExpiredWaitlists() {

		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager entityManager = emFactory.createEntityManager();

		String deleteExpiredWaitlistsString = "DELETE FROM PUBLICATION_WAITLIST"
				+ " WHERE START_TIME IS NOT NULL"
				+ " AND END_TIME IS NOT NULL"
				+ " AND END_TIME < SYSDATE";

		entityManager.getTransaction().begin();
		Query viewRenewBookQuery = entityManager.createNativeQuery(deleteExpiredWaitlistsString);
		viewRenewBookQuery.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		emFactory.close();
	}

	private static String checkinPublication(long assetCheckoutId) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager em = emfactory.createEntityManager();

		Query query = em.createQuery("SELECT a from AssetCheckout a WHERE a.id=:checkoutId");
		query.setParameter("checkoutId", assetCheckoutId);

		AssetCheckout checkout = (AssetCheckout)query.getSingleResult();


//		Query fineQuery = em.createNativeQuery("SELECT fine_amount from FINE_SNAPSHOT where checkout_id = :checkoutId");
//		fineQuery.setParameter("checkoutId", assetCheckoutId);
//		float fineDue = ((BigDecimal)fineQuery.getSingleResult()).floatValue();

		
		String updateReturnString = "UPDATE ASSET_CHECKOUT asc1 "
				+ "set ASC1.RETURN_DATE = sysdate, ASC1.FINE = (SELECT fine_amount from FINE_SNAPSHOT where checkout_id = ?) "
				+"	WHERE ASC1.ID = ? ";
		
		em.getTransaction().begin();
		
		Query q = em.createNativeQuery(updateReturnString).setParameter(1, assetCheckoutId).setParameter(2, assetCheckoutId);
		int outNo = q.executeUpdate();

		em.getTransaction().commit();

		em.close();
		emfactory.close();

		return checkout.getAssetSecondaryId();
	}



	@SuppressWarnings("unchecked")
	public static void updateWaitlistedPatronsOnReturn(String secAssetId) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager em = emfactory.createEntityManager();

		Query query = em.createQuery("SELECT w FROM PublicationWaitlist w "
				+ "WHERE w.key.pubSecondaryId=:secAssetId "
				+ "ORDER BY w.requestDate, w.isStudent ");
		query.setParameter("secAssetId", secAssetId);

		List<PublicationWaitlist> waitlistedPatrons = query.getResultList();
		int count = 0; LocalDateTime ldt = LocalDateTime.now();
		for(PublicationWaitlist pub: waitlistedPatrons) {

		String pubDetailQry = "SELECT d.title FROM BOOK_DETAIL d WHERE d.isbn_number=? "
				+ "UNION "
				+ "SELECT d.title FROM JOURNAL_DETAIL d WHERE d.issn_number=? "
				+ "UNION "
				+ "SELECT d.title FROM CONFERENCE_PROCEEDING_DETAIL d WHERE d.conf_num=?";

		Query publicationDetailQuery = em.createNativeQuery(pubDetailQry);
		publicationDetailQuery.setParameter(1, secAssetId);
		publicationDetailQuery.setParameter(2, secAssetId);
		publicationDetailQuery.setParameter(3, secAssetId);

		String title = (String)publicationDetailQuery.getSingleResult();

		// check if start time is NULL
		if(waitlistedPatrons.size() > 0) {
			if(waitlistedPatrons.get(0).getStartTime() == null) {
				LocalDateTime ldt1 = LocalDateTime.now();

				em.getTransaction().begin();
				for(PublicationWaitlist pub1: waitlistedPatrons) {
					pub1.setStartTime(ldt1.toDate());
					String start = ldt1.toString();
					ldt1 = ldt1.plusMinutes(30);
					pub1.setEndTime(ldt1.toDate());
					sendAvailabilityMail(pub1.getPatron().getEmailAddress(), start, ldt1.toString(), title);
				}
				em.getTransaction().commit();

			}
			else {

				Period period = new Period(new LocalDateTime(waitlistedPatrons.get(0).getStartTime()), LocalDateTime.now(), PeriodType.minutes());
				int diff = period.getMinutes();
				System.out.println(diff);

				em.getTransaction().begin();
				for(PublicationWaitlist pub1: waitlistedPatrons) {
					pub1.setEndTime(new LocalDateTime(pub1.getEndTime()).plusMinutes(diff).toDate());
					sendAvailabilityMail(pub1.getPatron().getEmailAddress(), pub1.getStartTime().toString(), pub1.getEndTime().toString(), title);
				}
				em.getTransaction().commit();
			}
		}
	}
	}
		
	public static void sendBookAvailabilityMail(String emailAddress, String startTime, String endTime, String publicationTitle) {
		sendPublicationAvailabilityMail(emailAddress, startTime, endTime, publicationTitle, "book");
	}
	
	public static void sendConfProcAvailabilityMail(String emailAddress, String startTime, String endTime, String publicationTitle) {
		sendPublicationAvailabilityMail(emailAddress, startTime, endTime, publicationTitle, "conference proceeding");
	}
	
	public static void sendJournalAvailabilityMail(String emailAddress, String startTime, String endTime, String publicationTitle) {
		sendPublicationAvailabilityMail(emailAddress, startTime, endTime, publicationTitle, "journal");
	}
	
	public static void sendAvailabilityMail(String emailAddress, String startTime, String endTime, String publicationTitle) {
		sendPublicationAvailabilityMail(emailAddress, startTime, endTime, publicationTitle, "publication");
	}
	
	private static void sendPublicationAvailabilityMail(String emailAddress, String startTime, String endTime, String publicationTitle, String assetName) {

		StringBuilder builder = new StringBuilder();
		builder.append("The " + assetName + " ").append(publicationTitle)
		.append(" that you had requested for is now available. You can check out the " + assetName + " from the library between ")
		.append(startTime).append(" and ").append(endTime);

		MailUtils.sendMail(emailAddress, "Status of waitlisted " + assetName + ": "+publicationTitle,
				builder.toString());
	}
}
