package edu.dbms.library.listener;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PostUpdate;
import javax.persistence.Query;

import org.joda.time.LocalDateTime;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.db.manager.NotificationManager;
import edu.dbms.library.entity.reserve.CameraReservation;
import edu.dbms.library.utils.MailUtils;

public final class CameraReservationListener {

	@PostUpdate
	public void sendUpdatedReservationStatusMails(final CameraReservation reservation) {

		if(reservation.getStatus().equalsIgnoreCase("CANCELLED")) {

			System.out.println("cancelled: "+reservation);

			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
					DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
			EntityManager em = emfactory.createEntityManager();

			Query query = em.createQuery("SELECT c FROM CameraReservation c "
					+ "WHERE c.cameraReservationKey.cameraId=:cameraId "
					+ "	 AND c.cameraReservationKey.issueDate=:issueDate AND c.status<> 'CANCELLED' "
					+ "ORDER BY c.reserveDate");
			query.setParameter("cameraId", reservation.getCamera().getId());
			query.setParameter("issueDate", reservation.getCameraReservationKey().getIssueDate());

			//send reservation cancellation mail
			StringBuilder builder = new StringBuilder();
			builder.append("Hi ").append(reservation.getPatron().getFirstName()).append('\n')
					.append("Since, you were unable to pick up the camera inside the stipulated time"
							+ ", your reservation has been cancelled");
			MailUtils.sendMail(reservation.getPatron().getEmailAddress(), "Availability of reserved camera",
					builder.toString());

			// send reservation update mail
			if(query.getResultList().size() > 1){
				CameraReservation nextReservation = (CameraReservation) query.getResultList().get(1);
				System.out.println("nextReservariton: "+nextReservation);
				builder = new StringBuilder();
				builder.append("Hi ").append(nextReservation.getPatron().getFirstName()).append('\n')
						.append("The camera that you had reserved is available for checkout. Please visit the library till ");
				int tempHour = LocalDateTime.now().getHourOfDay()+1;
				System.out.println("tempHour: "+tempHour);
				if(tempHour< 12)
					builder.append(tempHour).append("am");
				else if(tempHour == 12)
					builder.append(tempHour).append("pm");
				else
					builder.append(tempHour - 12).append("pm");

				MailUtils.sendMail(nextReservation.getPatron().getEmailAddress(), "Availability of reserved camera", builder.toString());
				NotificationManager.createNotification(nextReservation.getPatron().getId(), builder.toString());
			}
		}
	}

}
