package edu.dbms.library.db.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.Address;
import edu.dbms.library.entity.Library;
import edu.dbms.library.entity.resource.Room;

public class RoomsManager extends DBManager {

	public static List<Library> getLibraryList(){
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DEFAULT_PERSISTENCE_UNIT_NAME);
		EntityManager entitymanager = emfactory.createEntityManager( );

		List<Library> libraries = entitymanager.createQuery("select l from Library l").getResultList();
		return libraries;
	}

	public static List<Room> getAvailableRooms() {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
				DEFAULT_PERSISTENCE_UNIT_NAME);
		EntityManager entitymanager = emfactory.createEntityManager( );

		List<Room> Rooms = entitymanager.createQuery("select l from Library l").getResultList();
		return null;
	}

}
