package edu.dbms.library.init;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.job.QuartzJob;
//import edu.dbms.library.test.CameraTest;

public class Initializer {

	private static void createDbTables() {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager em = emfactory.createEntityManager( );

		Query query = em.createQuery("SELECT p FROM Patron p");
		query.getResultList();

		System.out.println("SQL Tables created successfully\n");
	}

	private static void initializeCronJobs() {

		new Thread(new QuartzJob()).start();
        System.out.println("Initialized CRON jobs\n");
    }

	public static void initSystem() {

		createDbTables();
		initializeCronJobs();
	}
}
