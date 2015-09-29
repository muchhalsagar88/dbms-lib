package edu.dbms.library.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.dbms.library.entity.AbsEntity;

public class DBUtils {

	public static final String DEFAULT_PERSISTENCE_UNIT_NAME = "main";
	
	public static void persist(Object obj) {
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DEFAULT_PERSISTENCE_UNIT_NAME);

		EntityManager entitymanager = emfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );

		entitymanager.persist(obj);
		entitymanager.getTransaction().commit();

		entitymanager.close();
		emfactory.close();
	}
	
	/*
	 * Persist a list of objects having the same entity type and having no 
	 * dependency on each other
	 */
	public static void persist(List<? extends AbsEntity> objects) {
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DEFAULT_PERSISTENCE_UNIT_NAME);

		EntityManager entitymanager = emfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );

		for(Object obj: objects)
			entitymanager.persist(obj);
		entitymanager.getTransaction().commit();

		entitymanager.close();
		emfactory.close();
		
	}
}
