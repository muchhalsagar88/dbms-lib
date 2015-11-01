package edu.dbms.library.runner;

import static edu.dbms.library.db.manager.DBManager.DEFAULT_PERSISTENCE_UNIT_NAME;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import edu.dbms.library.utils.MailUtils;

public class DueDateReminders implements Runnable {

	public DueDateReminders() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DEFAULT_PERSISTENCE_UNIT_NAME);
		
		EntityManager entitymanager = emfactory.createEntityManager( );
		
		Query q = entitymanager.createNativeQuery("SELECT * FROM DUE_IN_NEXT_3_DAYS");
		List list = q.getResultList();
		sendMail(list, "3 Days");
		q = entitymanager.createNativeQuery("SELECT * FROM DUE_IN_NEXT_24_HRS");
		list = q.getResultList();
		sendMail(list, "24 Hours");
	}

	public static void sendMail(List list, String text){
		for (Object object : list) {
			Object[] mail = (Object[])object;
			String mailId = ""+mail[2];
			StringBuffer _subject = new StringBuffer();
			_subject.append("Reminder for asset/s due in next "+text);
			StringBuffer _message = new StringBuffer();
			_message.append("Dear ").append(mail[0]).append(",\n\n").append("This is a reminder mail for following asset due in next "+text+" :\n\n");
			_message.append(mail[10]).append("\n").append("Due Date: ").append(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(((Timestamp)mail[4]).getTime())));
			_message.append("\n\nKindly check in above asset before due date to avoid any fine.");
			System.out.println("DDR: "+text);
			System.out.println("Sending mail to "+mailId);
			System.out.println("Subject: "+_subject.toString());
			System.out.println("Content: "+_message.toString());
			MailUtils.sendMail(mailId, _subject.toString() ,_message.toString());
		}
	}
	
	public static void main(String[] args) {
		new DueDateReminders().run();
	}

}
