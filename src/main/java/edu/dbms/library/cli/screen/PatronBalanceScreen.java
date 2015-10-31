package edu.dbms.library.cli.screen;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.ColumnResult;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.SqlResultSetMapping;

import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.AssetCheckout;
import edu.dbms.library.entity.Patron;
import edu.dbms.library.session.SessionUtils;

@SqlResultSetMapping(name="OngoingFines",
entities={
    @EntityResult(entityClass=edu.dbms.library.entity.catalog.AssetPatronConstraint.class, fields={
        @FieldResult(name="fine_duration", column="fine_duration"),
        @FieldResult(name="fine", column="fine")}
    )
},
columns={
    @ColumnResult(name="overdue_hrs")
}
)
public class PatronBalanceScreen extends BaseScreen {

	private Double dueFines;

	private double ongoingFineVal = 0d;

	@Override
	public void execute() {

		double totalFineDue = calculateBalance();
		System.out.println(String.format("Total fine due is $%f", totalFineDue));

		if(totalFineDue != 0) {
			String answer = readInput("Do you wish to pay the fines (YES/NO)? ");
			if(answer.equalsIgnoreCase("YES")) {

				payDueFines();
				if(ongoingFineVal == 0)
					clearPatronHold(SessionUtils.getPatronId());
				StringBuffer buffer = new StringBuffer();
				buffer.append("Fine paid off is $").append(totalFineDue - ongoingFineVal).append('\n');
				if(ongoingFineVal != 0d){
					buffer.append("Entire fine can be paid only after you return your checked out items.");
				}
				System.out.println(buffer.toString());
			}
		}
		BaseScreen nextScreen = getNextScreen(RouteConstant.PATRON_BASE);
		nextScreen.execute();
	}

	private void clearPatronHold(String patronId) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager em = emfactory.createEntityManager();

		Patron patron = em.find(Patron.class, patronId);

		em.getTransaction().begin();
		patron.setHold(false);
		em.getTransaction().commit();

		em.close();
		emfactory.close();
	}

	@SuppressWarnings("unchecked")
	private void payDueFines() {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager em = emfactory.createEntityManager();

		// Late returns fines
		Query query = em.createQuery("SELECT ac "
				+ "FROM AssetCheckout ac "
				+ "WHERE ac.patron.id=:patronId AND "
				+ "ac.id IN (SELECT c.id FROM AssetCheckout c WHERE c.returnDate > c.dueDate "
				+ "			 AND c.returnDate IS NOT NULL )");
		query.setParameter("patronId", SessionUtils.getPatronId());

		List<AssetCheckout> fineDueAssets = query.getResultList();
		em.getTransaction().begin();
		for(AssetCheckout temp: fineDueAssets)
			temp.setFine(0f);
		em.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	private double calculateBalance() {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager em = emfactory.createEntityManager();

		// Late returns fines
		Query query = em.createQuery("SELECT SUM(ac.fine) "
				+ "FROM AssetCheckout ac "
				+ "WHERE ac.patron.id=:patronId AND "
				+ "ac.id IN (SELECT c.id FROM AssetCheckout c WHERE c.returnDate > c.dueDate "
				+ "			 AND c.returnDate IS NOT NULL )");
		query.setParameter("patronId", SessionUtils.getPatronId());
		dueFines = (Double)query.getSingleResult();
		if(dueFines == null)
			dueFines = 0d;

		Query currCheckoutQuery = em.createNativeQuery("SELECT o.due_date AS overdue_hrs, apc.fine_duration AS fine_duration, apc.fine AS fine "
							+"FROM ongoing_checkouts o, asset_patron_constraint apc, asset a, patron p "
							+"WHERE o.patron_id=?1 AND a.asset_id = o.asset_id AND a.asset_type = apc.asset_type_id "
							+"AND p.patron_id=?2 AND p.patron_type=apc.patron_type ", "OngoingFines");

		currCheckoutQuery.setParameter(1, SessionUtils.getPatronId());
		currCheckoutQuery.setParameter(2, SessionUtils.getPatronId());
		List<Object[]> ongoingFines = currCheckoutQuery.getResultList();

		if(null != ongoingFines && ongoingFines.size()!=0){
			for(Object[] array: ongoingFines) {
				Date actualDueDate = (Date)array[0];
				LocalDateTime ldt = LocalDateTime.fromDateFields(actualDueDate);

				Period betweenDates = new Period(ldt, LocalDateTime.now(), PeriodType.hours());
				int diff = /*betweenDates.getDays()*24 + */betweenDates.getHours();

				double temp = Math.ceil(diff / ((BigDecimal)array[1]).intValue()) * ((BigDecimal)array[2]).intValue();
				ongoingFineVal += temp;
			}
		}

		return ongoingFineVal+dueFines;
	}

	@Override
	public void displayOptions() {
		// TODO Auto-generated method stub

	}

}
