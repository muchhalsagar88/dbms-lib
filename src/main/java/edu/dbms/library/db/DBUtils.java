package edu.dbms.library.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
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

		EntityManager entitymanager = emfactory.createEntityManager();
		//entitymanager.getTransaction().begin( );
		EntityTransaction trx = entitymanager.getTransaction(); 
		trx.begin();
		for(Object obj: objects) {
			entitymanager.persist(obj);
			entitymanager.flush();
			entitymanager.clear();
		}
		trx.commit();

		entitymanager.close();
		emfactory.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public static List<? extends AbsEntity> fetchAllEntities(String query) {
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DEFAULT_PERSISTENCE_UNIT_NAME);

		EntityManager entitymanager = emfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		
		List<? extends AbsEntity> entities = entitymanager.createQuery(query).getResultList();
		
		entitymanager.close();
		emfactory.close();
		
		return entities;
	}
	
	@SuppressWarnings("unchecked")
	public static <T, S> boolean removeEntity(Class<T> c, Object id, Class<S> idType) {
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DEFAULT_PERSISTENCE_UNIT_NAME);
		EntityManager entitymanager = emfactory.createEntityManager( );
		
		T entity = (T) entitymanager.find(c, (S)id);
		
		entitymanager.getTransaction( ).begin( );
		entitymanager.remove(entity);
		entitymanager.getTransaction( ).commit();
		
		entitymanager.close();
		emfactory.close();
		
		return true;
	}
	
	
	public static  int removeAllEntities(String query) {
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DEFAULT_PERSISTENCE_UNIT_NAME);
		EntityManager entitymanager = emfactory.createEntityManager( );
		
		entitymanager.getTransaction( ).begin( );
		int deletedCount = entitymanager.createQuery(query).executeUpdate();
		
		entitymanager.getTransaction( ).commit();
		
		entitymanager.close();
		emfactory.close();
		
		return deletedCount;
	}
	
	public static <T extends AbsEntity, S> AbsEntity findEntity(Class<T> c, Object id, Class<S> idType) {
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DEFAULT_PERSISTENCE_UNIT_NAME);
		EntityManager entitymanager = emfactory.createEntityManager( );
		
		T entity = (T) entitymanager.find(c, (S)id);
		
		entitymanager.close();
		emfactory.close();
		
		return entity;
	}
	
}
