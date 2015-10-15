package edu.dmbs.library.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
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
	
	@Before
	public void generateTestData() {
		
		List<FacultyCategory> categories = _generateTestData();
		for(FacultyCategory category: categories)
			DBUtils.persist(category);
			
	}
	
	@Test
	public void testDataGeneration() {
		
		Assert.assertEquals("Number of faculty categories persisted is different", 
				DEFAULT_FC_COUNT, getCount(FacultyCategory.class));
	}
	
	@After
	public void clearTestData() {
		
		removeAllEntities(FacultyCategory.class);
				
        System.out.println("@After: executedAfterEach");
	}
	
}
