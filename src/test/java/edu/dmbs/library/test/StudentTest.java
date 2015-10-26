package edu.dmbs.library.test;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.Address;
import edu.dbms.library.entity.Department;
import edu.dbms.library.entity.Student;
import junit.framework.Assert;

public class StudentTest extends BaseTest {
	
public static final int DEFAULT_STUDENT_COUNT = 2;
	
	@Before
	public void generateTestData() {
		
		Department d1 = new Department("Student Department 1");
		Department d2 = new Department("Student Department 2");
		DBUtils.persist(d1);
		DBUtils.persist(d2);
				
		Student s1 = new Student("919-985-9848", null, 
				new Address("2520 Avent Ferry Road", "Apt 102", "Raleigh", 27606), 
				new Date(), 'M');
		s1.setFirstName("Barry");
		s1.setLastName("Allen");
		s1.setNationality("American");
		s1.setDepartment(d1);
		DBUtils.persist(s1);
		
		Student s2 = new Student("919-985-9848", null, 
				new Address("2522 Avent Ferry Road", "Apt 104", "Raleigh", 27606), 
				new Date(), 'M');
		s2.setFirstName("Oliver");
		s2.setLastName("Queen");
		s2.setNationality("American");
		s2.setDepartment(d2);
		DBUtils.persist(s2);
		
	}

	@Test
	public void testDataGeneration() {
		
		Assert.assertEquals("Number of students persisted is different", 
				DEFAULT_STUDENT_COUNT, getCount(Student.class));
	}

	@After
	public void clearTestData() {
		
		removeAllEntities(Student.class);
		removeAllEntities(Department.class);
		
        System.out.println("@After: executedAfterEach");
	}

	
}
