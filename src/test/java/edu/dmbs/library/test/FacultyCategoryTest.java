package edu.dmbs.library.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.FacultyCategory;
import junit.framework.Assert;

public class FacultyCategoryTest extends BaseTest {

	public static final long DEFAULT_FC_COUNT = 4;
	
	private List<FacultyCategory> _generateTestData() {
		
		List<FacultyCategory> categories = new ArrayList<FacultyCategory>();
		
		FacultyCategory category = new FacultyCategory(); 
		category.setName("Assistant Professor");
		categories.add(category);
		
		category = new FacultyCategory();
		category.setName("Associate Professor");
		categories.add(category);
		
		category = new FacultyCategory();
		category.setName("Professor");
		categories.add(category);
		
		category = new FacultyCategory();
		category.setName("Lecturer");
		categories.add(category);
		
		return categories;
	}
	
	public int generateTestData() {
		
		List<FacultyCategory> categories = _generateTestData();
		for(FacultyCategory category: categories)
			DBUtils.persist(category);
			
		return categories.size();
	}
	
	@Test
	public void testDataGeneration() {
		
		// Actually persist the test data
		generateTestData();
		
		Assert.assertEquals("Number of faculty categories persisted is different", 
				DEFAULT_FC_COUNT, getCount(FacultyCategory.class));
	}
	
	@After
	public void clearTestData() {
		
		// Actually persist the test data
	
		DBUtils.removeAllEntities("FacultyCategory");
				
        System.out.println("@After: executedAfterEach");

	}
	
}
