package edu.dmbs.library.test;

import org.junit.After;
import org.junit.Test;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.Department;
import edu.dbms.library.entity.Faculty;
import edu.dbms.library.entity.FacultyCategory;
import junit.framework.Assert;

public class FacultyTest extends BaseTest {

	public static final int DEFAULT_FACULTY_COUNT = 2;
	
	public int generateTestData() {
		
		Department d1 = new Department("Faculty Department 1");
		Department d2 = new Department("Faculty Department 2");
		DBUtils.persist(d1);
		DBUtils.persist(d2);
				
		FacultyCategory fc1 = new FacultyCategory();
		fc1.setName("Faculty Category 1");
		DBUtils.persist(fc1);
		
		Faculty f1 = new Faculty();
		f1.setFirstName("John");
		f1.setLastName("Hancock");
		f1.setNationality("American");
		f1.setDepartment(d1);
		f1.setCategory(fc1);
		DBUtils.persist(f1);
		
		Faculty f2 = new Faculty();
		f2.setFirstName("Bruce");
		f2.setLastName("Wayne");
		f2.setNationality("Canadian");
		f2.setDepartment(d2);
		f2.setCategory(fc1);
		DBUtils.persist(f2);
			
		return DEFAULT_FACULTY_COUNT;
	}

	@Test
	public void testDataGeneration() {
		
		// Actually persist the test data
		generateTestData();
		
		Assert.assertEquals("Number of faculties persisted is different", 
				DEFAULT_FACULTY_COUNT, getCount(Faculty.class));
	}
	
	@After
	public void clearTestData() {
		
		// Actually persist the test data
	
		DBUtils.removeAllEntities("Faculty");
		DBUtils.removeAllEntities("Department");
		DBUtils.removeAllEntities("FacultyCategory");
				
        System.out.println("@After: executedAfterEach");

	}
}
