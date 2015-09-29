package edu.dmbs.library.test;

import java.awt.RadialGradientPaint;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.dbms.library.entity.Address;
import edu.dbms.library.entity.Library;

public class LibraryTest {

	public static void main(String []args) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "main" );

		EntityManager entitymanager = emfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );

		Library lib = new Library(); 
		lib.setLibraryName("James Hunt Library");
		lib.setLibraryAddress(new Address("101 Partner's Drive", null, "Raleigh", 27606));
		entitymanager.persist(lib);
		entitymanager.getTransaction().commit();

		entitymanager.close();
		emfactory.close();
	}
}
