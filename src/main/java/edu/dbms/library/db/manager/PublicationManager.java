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

	public static void returnBook(EntityManager entityManager, int assetCheckoutId) {

		String assetSecId = checkinBook(entityManager,assetCheckoutId);
		checkForWaitlistedPatrons(entityManager,assetSecId);
	}

	private static String checkinBook(EntityManager em, int assetCheckoutId) {

		
		Query query = em.createQuery("SELECT a FROM AssetCheckout a WHERE a.id=:checkoutId");
		query.setParameter("checkoutId", assetCheckoutId);

		AssetCheckout checkout = (AssetCheckout)query.getSingleResult();


//		Query fineQuery = em.createNativeQuery("SELECT fine_amount from FINE_SNAPSHOT where checkout_id = ?");
//		fineQuery.setParameter(1, assetCheckoutId);
//		float fineDue = ((BigDecimal)fineQuery.getSingleResult()).floatValue();

//		em.getTransaction().begin();
//		checkout.setReturnDate(new Date());
//		checkout.setFine(fineDue);
//		em.getTransaction().commit();
		
		
		String updateReturnString = "UPDATE ASSET_CHECKOUT asc1 "
				+ "set ASC1.RETURN_DATE = sysdate, ASC1.FINE = (SELECT fine_amount from FINE_SNAPSHOT where checkout_id = ?) "
				+"	WHERE ASC1.ID = ? ";
		
		em.getTransaction().begin();
		
		Query q = em.createNativeQuery(updateReturnString).setParameter(1, assetCheckoutId).setParameter(2, assetCheckoutId);
		int outNo = q.executeUpdate();
		
		em.getTransaction().commit();
		
		
		return checkout.getAssetSecondaryId();
	}

	@SuppressWarnings("unchecked")
	private static void checkForWaitlistedPatrons(EntityManager em, String secAssetId) {

		Query query = em.createQuery("SELECT w FROM PublicationWaitlist w"
				+ " WHERE  w.key.pubSecondaryId=:secAssetId "
				+ " ORDER BY w.requestDate, w.isStudent ");
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
