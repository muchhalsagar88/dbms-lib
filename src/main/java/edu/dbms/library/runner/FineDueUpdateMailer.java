package edu.dbms.library.runner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.AssetCheckout;
import edu.dbms.library.entity.Patron;
import edu.dbms.library.utils.MailUtils;

public class FineDueUpdateMailer implements Job {

	private Map<String, AssetCheckout> patronDues = new HashMap<String, AssetCheckout>();

	private void addToPatronDues(AssetCheckout checkout) {

		System.out.println(checkout);

		if(patronDues.containsKey(checkout.getPatron().getId())) {
			Period betweenDatesNew = new Period(LocalDateTime.fromDateFields(checkout.getDueDate()), LocalDateTime.now());

			AssetCheckout temp = patronDues.get(checkout.getPatron().getId());
			Period betweenDates = new Period(LocalDateTime.fromDateFields(temp.getDueDate()), LocalDateTime.now());
			System.out.println("betweenDatesNew.getDays() > betweenDates.getDays(): "+betweenDatesNew.getDays() +" > "+betweenDates.getDays());
			if(betweenDatesNew.getDays() > betweenDates.getDays())
				patronDues.put(checkout.getPatron().getId(), checkout);
		} else {
			patronDues.put(checkout.getPatron().getId(), checkout);
		}
	}

	@SuppressWarnings("unchecked")
	private void checkOverdues() {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager em = emfactory.createEntityManager();

		Query query = em.createQuery("SELECT a FROM AssetCheckout a "
				+ "WHERE a.returnDate IS NOT NULL AND a.fine > 0");
		List<AssetCheckout> finedCheckouts = query.getResultList();
		for(AssetCheckout temp: finedCheckouts)
			 addToPatronDues(temp);
		System.out.println("Fined checkouts: "+finedCheckouts.size());

		query = em.createQuery("SELECT a FROM AssetCheckout a "
				+ "WHERE a.returnDate IS NULL");
		List<AssetCheckout> unreturnedCheckouts = query.getResultList();
		for(AssetCheckout temp: unreturnedCheckouts)
			 addToPatronDues(temp);
		System.out.println("Unreturned checkouts: "+unreturnedCheckouts.size());

		Iterator<Map.Entry<String, AssetCheckout>> iter = patronDues.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, AssetCheckout> entry = iter.next();

			LocalDateTime ldt = LocalDateTime.fromDateFields(entry.getValue().getDueDate());
			Period betweenDates = new Period(ldt, LocalDateTime.now(), PeriodType.days());
			String message = null, subject = "Fine due reminder";

			int val = betweenDates.getDays();
			System.out.println(val);
			switch (val) {
			case 30:
				message = "Your library fines are due for 30 days now. Please pay them as soon as possible.";
				break;

			case 60:
				message = "Your library fines are due for 60 days now. Please pay them before 90 days to avoid account holds.";
				break;

			case 90:
				message = "Your library fines are due for 90 days now. Your account has been put on hold. Please clear the "
						+ "fines to get the account hold released.";
				setHoldOnAccount(entry.getValue().getPatron().getId());
				break;
			default:
				System.out.println("Do nothing!");
				break;
			}

			if(null != message)
				sendReminderMail(message, entry.getValue().getPatron().getEmailAddress(), subject);
		}
	}

	private void setHoldOnAccount(String patronId) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager em = emfactory.createEntityManager();

		Query query = em.createQuery("SELECT p FROM Patron p "
				+ "WHERE p.id=:patronId ");
		query.setParameter("patronId", patronId);
		Patron tempPatron = (Patron) query.getSingleResult();

		em.getTransaction().begin();
		tempPatron.setHold(true);
		em.getTransaction().commit();

		em.close();
		emfactory.close();
	}

	private void sendReminderMail(String message, String emailAddress, String subject) {

		MailUtils.sendMail(emailAddress, subject, message);
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		FineDueUpdateMailer dueMailer = new FineDueUpdateMailer();
		dueMailer.checkOverdues();
	}

	public static void main(String []args) {

		FineDueUpdateMailer dueMailer = new FineDueUpdateMailer();
		dueMailer.checkOverdues();
	}
}
