package edu.dbms.library.runner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.to.UserTO;

/*
 * This class sends a confirmation mail to all the patrons who 
 * have reserved cameras 
 */
public class CameraConfirmationMailer {

	private LocalDate getNextFriday() {
		
		LocalDate currDate = LocalDate.now();
		int currDay = currDate.getDayOfWeek();
		
		int diff = DateTimeConstants.FRIDAY - currDay;
		if(diff < 0) 
			diff += 7;
		
		LocalDate nextFriday = currDate.plusDays(diff);
		return nextFriday;
	}
	
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
	
	private Properties getMailerProperties() {
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		return props;
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
			sendMail(user.getEmailAddress(), builder.toString());
		}
		
	}
	
	private void sendMail(String emailAddress, String messageBody) {
		
		final String username = "csc540.009@gmail.com";
		final String password = "dbms1234";

		Properties props = getMailerProperties(); 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(emailAddress));
			message.setSubject("Availability of reserved camera");
			message.setText(messageBody);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		
		CameraConfirmationMailer mailer = new CameraConfirmationMailer();
		LocalDate date = mailer.getNextFriday();
		System.out.println(date.toString());
		Date d = mailer.formatToQueryDate(date);
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME);
		EntityManager em = emfactory.createEntityManager();
		
		Query query = em.createQuery("SELECT c.cameraReservationKey.cameraId, c.cameraReservationKey.patronId, "
				+ " p.firstName, p.emailAddress"
				+ " FROM Patron p, CameraReservation c"
				+ " WHERE c.issueDate=:issueDate AND c.cameraReservationKey.patronId=p.id" 
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