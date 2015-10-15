package edu.dmbs.library.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.Author;
import junit.framework.Assert;

public class AuthorTest extends BaseTest {

	public static final long DEFAULT_AUTH_COUNT = 3;
	
	private List<Author> _generateTestData() {
		
		List<Author> authorList = new ArrayList<Author>();
		
		Author a1= new Author();
		a1.setId("101");
		a1.setName("Salman Rushdie");
		authorList.add(a1);
		
		Author a2= new Author();
		a2.setId("102");
		a2.setName("J K Rowling");
		authorList.add(a2);
		
		Author a3= new Author();
		a3.setId("103");
		a3.setName("Rudyard Kipling");
		authorList.add(a3);
		
			
		return authorList;
	}
	
	@Before
	public void generateTestData() {
		
		List<Author> authorList = _generateTestData();
		for(Author author: authorList)
			DBUtils.persist(author);
	
	}
	
	@Test
	public void testDataGeneration() {
		
		Assert.assertEquals("Number of Authors persisted is different", 
				DEFAULT_AUTH_COUNT, getCount(Author.class));
	}
	
	@After
	public void clearTestData() {
		
		removeAllEntities(Author.class);
	
		System.out.println("@After: executedAfterEach");
	}
}

