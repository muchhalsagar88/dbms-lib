package edu.dbms.library.runner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.joda.time.LocalDate;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.db.manager.NotificationManager;
import edu.dbms.library.entity.AssetCheckout;
import edu.dbms.library.to.UserTO;
import edu.dbms.library.utils.DateUtils;
import edu.dbms.library.utils.MailUtils;

/*
 * This class sends a confirmation mail to all the patrons who
 * have reserved cameras
 */
public class CameraConfirmationMailer implements Job {

	private Date formatToQueryDate(LocalDate localDate) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(localDate.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	private void sendCameraConfirmationMails(List<UserTO> users) {

		for(UserTO user: users) {

			StringBuilder builder = new StringBuilder();
			builder.append("Dear ").append(user.getFirstName()).append('\n');

			if(user.getWaitlistNumber() != 0) {
				builder.append("Unfortunately, your camera is not available. "
						+ "You will be reached out in case the camera becomes available");
			} else {
				if(user.isCamAvailable())
					builder.append("Your camera is available for pick up till 10:00 am today. In case you are unable to check out by that time, "
							+ "your reservation will be cancelled");
				else
					builder.append("Unfortunately, your camera is not available. "
							+ "You will be reached out in case the camera becomes available");
			}
			MailUtils.sendMail(user.getEmailAddress(), "Availability of reserved camera", builder.toString());
			NotificationManager.createNotification(user.getPatronId(), builder.toString());
		}

	}

	private boolean checkForCameraAvailability(String cameraId) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager em = emfactory.createEntityManager();

		Query query = em.createQuery("SELECT a FROM AssetCheckout a "
				+ "WHERE a.asset.id=:cameraId AND a.returnDate IS NULL ");
		query.setParameter("cameraId", cameraId);

		List<AssetCheckout> checkout = query.getResultList();

		em.close();
		emfactory.close();

		if(checkout != null && checkout.isEmpty())
			return false;
		return true;
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		CameraConfirmationMailer mailer = new CameraConfirmationMailer();
		LocalDate date = DateUtils.getNextFriday();
		Date d = mailer.formatToQueryDate(date);

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager em = emfactory.createEntityManager();

		Query query = em.createQuery("SELECT c.cameraReservationKey.cameraId, c.cameraReservationKey.patronId, "
				+ " p.firstName, p.emailAddress"
				+ " FROM Patron p, CameraReservation c"
				+ " WHERE c.cameraReservationKey.issueDate=:issueDate AND c.cameraReservationKey.patronId=p.id"
				+ " ORDER BY c.cameraReservationKey.cameraId, c.reserveDate");
		query.setParameter("issueDate", d);

		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();

		List<UserTO> users = new LinkedList<UserTO>();
		String prevCamId = null;
		int waitlist = 0;
		for(Object[] objArray: list) {
			String tempCam = (String)objArray[0];
			waitlist = (tempCam.equalsIgnoreCase(prevCamId))? waitlist+1 :0;
			prevCamId = tempCam;

			UserTO user = new UserTO((String)objArray[2], (String)objArray[3], waitlist, (String)objArray[1]);
			boolean camReturned = false;
			if(waitlist == 0) {
				camReturned = checkForCameraAvailability(tempCam);
				user.setCamAvailable(camReturned);
			}
			users.add(user);
		}
		mailer.sendCameraConfirmationMails(users);
	}

}
