package edu.dmbs.library.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.Address;
import edu.dbms.library.entity.Library;
import edu.dbms.library.entity.resource.Camera;
import junit.framework.Assert;

public class CameraTest extends BaseTest {

public static final long DEFAULT_CAMERA_COUNT = 2;
	
	private List<Camera> _generateTestData() {
		
		Library lib = new Library(); 
		lib.setLibraryName("James Hunt Library");
		lib.setLibraryAddress(new Address("101 Partner's Drive", null, "Raleigh", 27606));
		
		DBUtils.persist(lib);
		
		List<Camera> bookList = new ArrayList<Camera>();
		Camera b1 = new Camera(lib, "Canon", "Model1", "Lens Detail 1", 4);
		bookList.add(b1);
		
				
		Camera b2 = new Camera(lib, "Canon", "Model2", "Lens Detail 2", 8);
		bookList.add(b2);
		
		return bookList;
	}
	
	@Before
	public void generateTestData() {
		
		List<Camera> bookList = _generateTestData();
		for(Camera book: bookList)
			DBUtils.persist(book);
		
	}
	
	@Test
	public void testDataGeneration() {
		
		Assert.assertEquals("Number of Cameras persisted is different", 
				DEFAULT_CAMERA_COUNT, getCount(Camera.class));
	}
	
	@After
	public void clearTestData() {
		
		removeAllEntities(Camera.class);
		removeAllEntities(Library.class);
		
        System.out.println("@After: executedAfterEach");
	}
}
