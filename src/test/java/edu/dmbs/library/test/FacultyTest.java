package edu.dmbs.library.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.Department;
import edu.dbms.library.entity.Faculty;
import edu.dbms.library.entity.FacultyCategory;
import junit.framework.Assert;

public class FacultyTest extends BaseTest {

	public int DEFAULT_FACULTY_COUNT;
	
	private List<Department> testDepartments;
	
	private FacultyCategory testFacultyCategory;
	
	private List<Faculty> testFaculty;
	
	public FacultyTest() {
		this.testDepartments = new ArrayList<Department>();
		this.testFaculty = new ArrayList<Faculty>();
	}
	
	@Before
	public void generateTestData() {
		
		// Persist dependencies
		Department d = new Department("Faculty Department 1");
		DBUtils.persist(d);
		this.testDepartments.add(d);
		
		d = new Department("Faculty Department 2");
		DBUtils.persist(d);
		this.testDepartments.add(d);
		
		this.testFacultyCategory = new FacultyCategory("Faculty Category 1");
		DBUtils.persist(this.testFacultyCategory);
		
		Faculty f1 = new Faculty();
		f1.setFirstName("John");
		f1.setLastName("Hancock");
		f1.setNationality("American");
		f1.setDepartment(this.testDepartments.get(0));
		f1.setCategory(this.testFacultyCategory);
		DBUtils.persist(f1);
		this.testFaculty.add(f1);
		
		Faculty f2 = new Faculty();
		f2.setFirstName("Bruce");
		f2.setLastName("Wayne");
		f2.setNationality("Canadian");
		f2.setDepartment(this.testDepartments.get(1));
		f2.setCategory(this.testFacultyCategory);
		DBUtils.persist(f2);
		this.testFaculty.add(f2);
		
		this.DEFAULT_FACULTY_COUNT = this.testFaculty.size();
	}

	@Test
	public void testDataGeneration() {
		
		Assert.assertEquals("Number of faculties persisted is different", 
				DEFAULT_FACULTY_COUNT, getCount(Faculty.class));
	}
	
	@After
	public void clearGeneratedData() {
		
		removeAllEntities(Faculty.class);
		removeAllEntities(Department.class);
		removeAllEntities(FacultyCategory.class);
				
        System.out.println("@After: executedAfterEach");
	}
}
