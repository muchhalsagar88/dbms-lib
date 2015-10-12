package edu.dmbs.library.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.Publisher;
import junit.framework.Assert;

public class PublisherTest extends BaseTest {

	public static final long DEFAULT_FC_COUNT = 5;
	
	private List<Publisher> _generateTestData() {
		
		List<Publisher> publisherList = new ArrayList<Publisher>();
			
		Publisher p1 = new Publisher();
		p1.setId(1001);
		p1.setName("Publisher 1");
		
		
		Publisher p2 = new Publisher();
		p2.setId(1002);
		p2.setName("Publisher 2");
		
		Publisher p3 = new Publisher();
		p3.setId(1003);
		p3.setName("Publisher 3");
		
		Publisher p4 = new Publisher();
		p4.setId(1004);
		p4.setName("Publisher 4");
		
		Publisher p5 = new Publisher();
		p5.setId(1005);
		p5.setName("Publisher 5");
		
		publisherList.add(p5); publisherList.add(p2);
		publisherList.add(p4); publisherList.add(p1);
		publisherList.add(p3);
		
		
		return publisherList;
	}
	
	public int generateTestData() {
		
		List<Publisher> pubList = _generateTestData();
		
		for(Publisher publisher: pubList)
			DBUtils.persist(publisher);
			
		return pubList.size();
	}
	
	@Test
	public void testDataGeneration() {
		
		// Actually persist the test data
		generateTestData();
		
		Assert.assertEquals("Number of Publishers persisted is different", 
				DEFAULT_FC_COUNT, getCount(Publisher.class));
	}
	
	@After
	public void clearTestData() {
		
		// Actually persist the test data
		
		DBUtils.removeAllEntities("Publisher");
			
        System.out.println("@After: executedAfterEach");

	}
	
	
}
