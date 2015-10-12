package edu.dmbs.library.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.Department;
import junit.framework.Assert;

public class DepartmentTest extends BaseTest {

	public static final long DEFAULT_DEPARTMENT_COUNT = 3;
	
	private List<Department> _generateTestData() {
		
		List<Department> departments = new ArrayList<Department>();
		
		Department dept = new Department("Computer Science");
		departments.add(dept);
		
		dept = new Department("Computer Engineering");
		departments.add(dept);
		
		dept = new Department("Operations Research");
		departments.add(dept);
		
		return departments;
	}
	
	public int generateTestData() {
		
		List<Department> departments = _generateTestData();
		for(Department dept: departments)
			DBUtils.persist(dept);
			
		return departments.size();
	}

	@Test
	public void testDataGeneration() {
		
		// Actually persist the test data
		generateTestData();
		
		Assert.assertEquals("Number of libraries persisted is different", 
				DEFAULT_DEPARTMENT_COUNT, getCount(Department.class));
	}
	
	@After
	public void clearTestData() {
		
		// Actually persist the test data
	
	
		DBUtils.removeAllEntities("Department");
					
        System.out.println("@After: executedAfterEach");

	}
	
}
