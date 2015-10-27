package edu.dbms.library.db.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import edu.dbms.library.entity.LoginDetails;
import edu.dbms.library.to.LoginTO;
import oracle.net.aso.b;

public class LoginManager extends DBManager {

	public static String getOraHash(String string, int bucketSize, int salt){
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DEFAULT_PERSISTENCE_UNIT_NAME);
		EntityManager entitymanager = emfactory.createEntityManager( );
		Query q = entitymanager.createNativeQuery("SELECT ORA_HASH(?, ?, ?) FROM DUAL");
		q.setParameter(1, string);
		q.setParameter(2, bucketSize);
		q.setParameter(3, salt);
		return q.getSingleResult().toString();
	}
	public static LoginTO checkCredentials(String username, String password) {
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DEFAULT_PERSISTENCE_UNIT_NAME);
		EntityManager entitymanager = emfactory.createEntityManager( );
		
		LoginDetails login = (LoginDetails) entitymanager.find(LoginDetails.class, username);
		if(login==null || !login.getPassword().equals(password)) 
			return null;
		
		Query q = entitymanager.createQuery("SELECT s FROM Student s WHERE "
				+ "s.id = :id");
		q.setParameter("id", login.getPatron().getId());
		
		Object student;
		if(q.getResultList().size() > 0)
			student = q.getSingleResult();
		else
			student = null;
		
		LoginTO loginTo = new LoginTO(login.getPatron().getId());
		if(student != null)
			loginTo.setStudent(true);
		
		entitymanager.close();
		emfactory.close();
		
		return loginTo;
	}
	
}

