package edu.dbms.library.db.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.Notification;
import edu.dbms.library.entity.Patron;

public class NotificationManager extends DBManager {

	public static void createNotification(String patronId, String notification) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager em = emfactory.createEntityManager( );

		em.getTransaction().begin();
		Patron patron = em.find(Patron.class, patronId);
		Notification n = new Notification(notification, 1, patron);
		em.persist(n);
		em.getTransaction().commit();
		em.clear();

		em.close();emfactory.close();
	}

	public static int getDueNotifications(String patronId) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager em = emfactory.createEntityManager( );

		Query query = em.createQuery("SELECT n FROM Notification n "
				+ "WHERE n.patron.id=:patronId AND "
				+ "n.isRead=1 ");
		query.setParameter("patronId", patronId);

		List<Notification> notifs= query.getResultList();
		em.close();emfactory.close();

		if(notifs!= null && notifs.size()!=0)
			return notifs.size();
		return 0;
	}

	public static List<Notification> getUnreadNotifications(String patronId) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager em = emfactory.createEntityManager( );

		Query query = em.createQuery("SELECT n FROM Notification n "
				+ "WHERE n.patron.id=:patronId AND "
				+ "n.isRead=1 ");
		query.setParameter("patronId", patronId);

		List<Notification> notifs= query.getResultList();

		em.getTransaction().begin();
		for(Notification n: notifs)
			n.setIsRead(0);

		em.getTransaction().commit();
		em.close();emfactory.close();

		return notifs;
	}
}
