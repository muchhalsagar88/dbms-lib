package edu.dmbs.library.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
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
	
	@Before
	public void generateTestData() {
		
		List<Department> departments = _generateTestData();
		for(Department dept: departments)
			DBUtils.persist(dept);
	}

	@Test
	public void testDataGeneration() {
		
		Assert.assertEquals("Number of departments persisted is different", 
				DEFAULT_DEPARTMENT_COUNT, getCount(Department.class));
	}
	
	@After
	public void clearTestData() {
		
		removeAllEntities(Department.class);
					
        System.out.println("@After: executedAfterEach");
	}
	
}
