package edu.dmbs.library.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.Address;
import edu.dbms.library.entity.Library;
import junit.framework.Assert;

public class LibraryTest extends BaseTest {

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
	
	@Before
	public void generateTestData() {
		
		List<Library> libraries = _generateTestData();
		for(Library lib: libraries)
			DBUtils.persist(lib);
	}
	
	@Test
	public void testDataGeneration() {
		
		Assert.assertEquals("Number of libraries persisted is different", 
				DEFAULT_LIBRARY_COUNT, getCount(Library.class));
	}
	
	@After
	public void clearTestData() {
		
		removeAllEntities(Library.class);
	    System.out.println("@After: executedAfterEach");
	}
	
}
