package edu.dbms.library.db.manager;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.joda.time.LocalDate;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.session.SessionUtils;

public class FineDueManager extends DBManager {

	/*
	 * Checks if current day is last day and bill has been already generated
	 */
	public static void generateMonthlyBill() {

		int lastDayOfMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
		if(LocalDate.now().getDayOfMonth() == lastDayOfMonth) {

			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
					DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
			EntityManager em = emfactory.createEntityManager( );

			Query query = em.createNativeQuery("SELECT FINE_FOR_PATRON(?) FROM DUAL ");
			query.setParameter(1, SessionUtils.getPatronId());

			List<BigDecimal> fines = query.getResultList();
			if(fines.size() == 0)
				return;

			float fine = fines.get(0).floatValue();

			StringBuilder builder = new StringBuilder();
			builder.append("Dear user,\n")
				.append("You have an outstanding fine amount of $")
				.append(fine);

			em.close();
			emfactory.close();

			NotificationManager.createNotification(SessionUtils.getPatronId(), builder.toString());
		}

	}
}
