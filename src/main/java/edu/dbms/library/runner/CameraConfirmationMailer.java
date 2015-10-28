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
				builder.append("Your camera is available for pick up till 10:00 am today. In case you are unable to check out by that time, "
						+ "your reservation will be cancelled");
			}
			MailUtils.sendMail(user.getEmailAddress(), builder.toString());
		}
		
	}
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		CameraConfirmationMailer mailer = new CameraConfirmationMailer();
		LocalDate date = DateUtils.getNextFriday();
		System.out.println(date.toString());
		Date d = mailer.formatToQueryDate(date);
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME);
		EntityManager em = emfactory.createEntityManager();
		
		Query query = em.createQuery("SELECT c.cameraReservationKey.cameraId, c.cameraReservationKey.patronId, "
				+ " p.firstName, p.emailAddress"
				+ " FROM Patron p, CameraReservation c"
				+ " WHERE c.cameraReservationKey.issueDate=:issueDate AND c.cameraReservationKey.patronId=p.id" 
				+ " ORDER BY c.cameraReservationKey.cameraId, c.reserveDate");
		query.setParameter("issueDate", d);
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = (List<Object[]>)query.getResultList();
		
		List<UserTO> users = new LinkedList<UserTO>(); 
		String prevCamId = null;
		int waitlist = 0;
		for(Object[] objArray: list) {
			String tempCam = (String)objArray[0];
			waitlist = (tempCam.equalsIgnoreCase(prevCamId))? waitlist+1 :0;
			prevCamId = tempCam;
			
			UserTO user = new UserTO((String)objArray[2], (String)objArray[3], waitlist);
			System.out.println(user);
			users.add(user);
		}
		mailer.sendCameraConfirmationMails(users);
	}
	
	public static void main(String[] args) {
		
		CameraConfirmationMailer mailer = new CameraConfirmationMailer();
		LocalDate date = DateUtils.getNextFriday();
		System.out.println(date.toString());
		Date d = mailer.formatToQueryDate(date);
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME);
		EntityManager em = emfactory.createEntityManager();
		
		Query query = em.createQuery("SELECT c.cameraReservationKey.cameraId, c.cameraReservationKey.patronId, "
				+ " p.firstName, p.emailAddress"
				+ " FROM Patron p, CameraReservation c"
				+ " WHERE c.cameraReservationKey.issueDate=:issueDate AND c.cameraReservationKey.patronId=p.id" 
				+ " ORDER BY c.cameraReservationKey.cameraId, c.reserveDate");
		query.setParameter("issueDate", d);
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = (List<Object[]>)query.getResultList();
		
		List<UserTO> users = new LinkedList<UserTO>(); 
		String prevCamId = null;
		int waitlist = 0;
		for(Object[] objArray: list) {
			String tempCam = (String)objArray[0];
			waitlist = (tempCam.equalsIgnoreCase(prevCamId))? waitlist+1 :0;
			prevCamId = tempCam;
			
			UserTO user = new UserTO((String)objArray[2], (String)objArray[3], waitlist);
			System.out.println(user);
			users.add(user);
		}
		mailer.sendCameraConfirmationMails(users);
		
	}
}
