package edu.dmbs.library.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.Address;
import edu.dbms.library.entity.Library;
import junit.framework.Assert;

public class LibraryTest implements ITest {

	public static final long DEFAULT_LIBRARY_COUNT = 2;
	
	private List<Library> _generateTestData() {
		
		List<Library> libraries = new ArrayList<Library>();
		
		Library lib = new Library(); 
		lib.setLibraryName("James Hunt Library");
		lib.setLibraryAddress(new Address("101 Partner's Drive", null, "Raleigh", 27606));
		libraries.add(lib);
		
		lib = new Library();
		lib.setLibraryName("D.H. Hill Library");
		lib.setLibraryAddress(new Address("3420 Hillsborough Street",
				null, "Raleigh", 27606));
		libraries.add(lib);
		
		return libraries;
	}
	
	public int generateTestData() {
		
		List<Library> libraries = _generateTestData();
		for(Library lib: libraries)
			DBUtils.persist(lib);
			
		return libraries.size();
	}
	
	@Test
	public void testDataGeneration() {
		Assert.assertEquals("Number of libraries persisted is different", 
				DEFAULT_LIBRARY_COUNT, generateTestData());
	}
	
	/*public static void main(String []args) {
		List<Library> list = new LibraryTest()._generateTestData();
		for(Library lib: list) {
			//DBUtils.persist(lib);
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("main");

			EntityManager entitymanager = emfactory.createEntityManager( );
			entitymanager.getTransaction( ).begin( );

			entitymanager.persist(lib);
			entitymanager.getTransaction().commit();

			entitymanager.close();
			emfactory.close();
		}
		System.out.println("Done");
	}*/
}
