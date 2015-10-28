package edu.dmbs.library.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.Address;
import edu.dbms.library.entity.Department;
import edu.dbms.library.entity.LoginDetails;
import edu.dbms.library.entity.Student;
import junit.framework.Assert;

public class LoginTest extends BaseTest {

	public static final long DEFAULT_LOGIN_COUNT = 4;
	
	private List<LoginDetails> _generateTestData() {
		
		Department d1 = new Department("Faculty Department 1");
		DBUtils.persist(d1);
				
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
		s2.setDepartment(d1);
		DBUtils.persist(s2);
		
		Student s3 = new Student("919-985-9848", null, 
				new Address("2524 Avent Ferry Road", "Apt 104", "Raleigh", 27606), 
				new Date(), 'M');
		s3.setFirstName("Tom");
		s3.setLastName("Hanks");
		s3.setNationality("American");
		s3.setDepartment(d1);
		DBUtils.persist(s3);
		
		Student s4 = new Student("919-985-9848", null, 
				new Address("2526 Avent Ferry Road", "Apt 104", "Raleigh", 27606), 
				new Date(), 'M');
		s4.setFirstName("Tom");
		s4.setLastName("Cruise");
		s4.setNationality("American");
		s4.setDepartment(d1);
		DBUtils.persist(s4);
		
		List<LoginDetails> logins = new ArrayList<LoginDetails>();
		LoginDetails loginDetail = new LoginDetails("user_name_1", "password_123");
		loginDetail.setPatron(s1);
		logins.add(loginDetail);
		
		loginDetail = new LoginDetails("user_name_2", "password_123");
		logins.add(loginDetail);
		loginDetail.setPatron(s2);
		
		loginDetail = new LoginDetails("user_name_3", "password_123");
		loginDetail.setPatron(s3);
		logins.add(loginDetail);
		
		loginDetail = new LoginDetails("user_name_4", "password_123");
		logins.add(loginDetail);
		loginDetail.setPatron(s4);
		
		return logins;
	}
	
	@Before
	public void generateTestData() {
		
		List<LoginDetails> logins = _generateTestData();
		for(LoginDetails loginDetail: logins)
			DBUtils.persist(loginDetail);
	}
	
	@Test
	public void testDataGeneration() {
		
		Assert.assertEquals("Number of logins persisted is different", 
				DEFAULT_LOGIN_COUNT, getCount(LoginDetails.class));
	}
	
	@After
	public void clearTestData() {
		
		removeAllEntities(LoginDetails.class);
		removeAllEntities(Student.class);
		removeAllEntities(Department.class);

		System.out.println("@After: executedAfterEach");
	}
}
