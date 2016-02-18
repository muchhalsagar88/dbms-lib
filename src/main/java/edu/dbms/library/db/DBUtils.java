package edu.dbms.library.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import edu.dbms.library.entity.AbsEntity;

public class DBUtils {

	private static Properties readDatabaseProps() {
		Properties props = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("config.properties");
			props.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return props;
	}

	public static final String DEFAULT_PERSISTENCE_UNIT_NAME = "main";

	private static Map<String, String> databaseParams;

	public static Map<String, String> getPropertiesMap() {

		if(databaseParams == null) {
			databaseParams = new HashMap<String, String>();
			Properties props = readDatabaseProps();

			databaseParams.put("javax.persistence.jdbc.url", props.getProperty("url"));
			databaseParams.put("javax.persistence.jdbc.user", props.getProperty("user"));
			databaseParams.put("javax.persistence.jdbc.password", props.getProperty("password"));
		}

		return databaseParams;
	}

	public static Date validateDate(String date, String format, boolean isFutureDate){
		SimpleDateFormat f = new SimpleDateFormat(format);
		f.setLenient(false);
		Date d = null;
		try{
			d = f.parse(date);
			if(d.getTime() <= new Date().getTime()&&isFutureDate)
				return null;
		}
		catch(ParseException e){
			return null;
		}
		return d;
	}


	public static void persist(Object obj) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());

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
				DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());

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
				DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());

		EntityManager entitymanager = emfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );

		List<? extends AbsEntity> entities = entitymanager.createQuery(query).getResultList();

		entitymanager.close();
		emfactory.close();

		return entities;
	}

	public static <T, S> boolean removeEntity(Class<T> c, Object id, Class<S> idType) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager entitymanager = emfactory.createEntityManager( );

		T entity = entitymanager.find(c, id);

		entitymanager.getTransaction( ).begin( );
		entitymanager.remove(entity);
		entitymanager.getTransaction( ).commit();

		entitymanager.close();
		emfactory.close();

		return true;
	}


	public static  int removeAllEntities(String query) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
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
				DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());
		EntityManager entitymanager = emfactory.createEntityManager( );

		T entity = entitymanager.find(c, id);

		entitymanager.close();
		emfactory.close();

		return entity;
	}

	public static Object executeCountQuery(String query) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());

		EntityManager entitymanager = emfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );

		Object result = entitymanager.createQuery(query).getSingleResult();

		entitymanager.close();
		emfactory.close();

		return result;
	}

}
