package edu.dbms.library.db.manager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.joda.time.LocalDateTime;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.AssetCheckout;
import edu.dbms.library.entity.reserve.PublicationWaitlist;
import edu.dbms.library.utils.MailUtils;

public class PublicationManager {

	public static void returnBook(String assetCheckoutId) {

		String assetSecId = checkinBook(assetCheckoutId);
		checkForWaitlistedPatrons(assetSecId);
	}

	private static String checkinBook(String assetCheckoutId) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager em = emfactory.createEntityManager();

		Query query = em.createQuery("SELECT a AssetCheckout a WHERE a.id=:checkoutId");
		query.setParameter("checkoutId", assetCheckoutId);

		AssetCheckout checkout = (AssetCheckout)query.getSingleResult();


		Query fineQuery = em.createNativeQuery("SELECT fine_amount from FINE_SNAPSHOT where checkout_id = :checkoutId");
		fineQuery.setParameter("checkoutId", assetCheckoutId);
		float fineDue = ((BigDecimal)fineQuery.getSingleResult()).floatValue();

		em.getTransaction().begin();
		checkout.setReturnDate(new Date());
		checkout.setFine(fineDue);
		em.getTransaction().commit();

		em.close();
		emfactory.close();

		return checkout.getAssetSecondaryId();
	}

	@SuppressWarnings("unchecked")
	private static void checkForWaitlistedPatrons(String secAssetId) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager em = emfactory.createEntityManager();

		Query query = em.createQuery("SELECT w FROM PublicationWaitlist w, Patron p"
				+ "WHERE w.key.patronId=p.patronId AND "
				+ "w.key.pubSecondaryId=:secAssetId "
				+ "ORDER BY w.requestDate, w.isStudent ");
		query.setParameter("secAssetId", secAssetId);

		List<PublicationWaitlist> waitlistedPatrons = query.getResultList();
		int count = 0; LocalDateTime ldt = LocalDateTime.now();
		for(PublicationWaitlist pub: waitlistedPatrons) {

			StringBuilder builder = new StringBuilder();
			builder.append("You can check out the book from the library at ");

			if(count > 0){
				builder.append(ldt.plusMinutes(count * 30)).append(". The book is subject to availability.");
			} else {
				builder.append(ldt.toString());
			}
			count++;

			MailUtils.sendMail(pub.getPatron().getEmailAddress(), "Status of waitlisted book",
					builder.toString());
		}

	}
}
