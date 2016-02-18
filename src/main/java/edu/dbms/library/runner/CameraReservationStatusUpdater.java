package edu.dbms.library.runner;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.reserve.CameraReservation;
import edu.dbms.library.session.SessionUtils;

public class CameraReservationStatusUpdater implements Job {

	private void updateReservationStatus() {

		/*LocalDate date = DateUtils.getNextFriday();
		System.out.println(date.toString());
		Date d = DateUtils.formatToQueryDate(date);*/
		/*
		 * WHEN RUNNING VIA SCHEDULER, the issueDate should be
		 *  LocalDate localDate = LocalDate.now();
		 * 	LocalDateTime time = localDate.toLocalDateTime(LocalTime.MIDNIGHT);
		 *  Date d = time.toDate();
		 */

		LocalDate localDate = LocalDate.now();
		LocalDateTime time = localDate.toLocalDateTime(LocalTime.MIDNIGHT);
		Date d = time.toDate();

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager em = emfactory.createEntityManager();

		Query query = em.createNativeQuery("SELECT res.* FROM Camera_Reservation res, Min_Reservation_View TEMP "
				+"WHERE res.camera_Id=TEMP.camera_id AND "
				+"res.reserve_Date=TEMP.min_reserve_date AND "
				+"res.reservation_status='ACTIVE' AND "
				+"TEMP.issue_date=?1 "
				, CameraReservation.class);
		query.setParameter(1, d);

		List<CameraReservation> reservationToCancel = query.getResultList();

		em.getTransaction().begin();
		for(CameraReservation reservation: reservationToCancel)
			reservation.setStatus("CANCELLED");
		em.getTransaction().commit();

		em.close();
		emfactory.close();
	}

	public static void main(String []args) {

		CameraReservationStatusUpdater updater = new CameraReservationStatusUpdater();
		updater.updateReservationStatus();
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		CameraReservationStatusUpdater updater = new CameraReservationStatusUpdater();
		updater.updateReservationStatus();
	}
}
