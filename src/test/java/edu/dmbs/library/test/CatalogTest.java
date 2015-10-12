package edu.dmbs.library.test;

import org.junit.After;
import org.junit.Test;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.catalog.Classification;
import edu.dbms.library.entity.catalog.DegreeProgram;
import edu.dbms.library.entity.catalog.Year;
import junit.framework.Assert;

public class CatalogTest extends BaseTest {

	public static final int DEFAULT_CLASSIFICATION_COUNT = 3;
	
	public static final int DEFAULT_YEAR_COUNT = 5;
	
	public static final int DEFAULT_DEGPROG_COUNT = 3;
	
	public int generateTestData() {
		
		Classification c1 = new Classification("Undergraduate");
		DBUtils.persist(c1);
		Classification c2 = new Classification("Graduate");
		DBUtils.persist(c2);
		Classification c3 = new Classification("Post Graduate");
		DBUtils.persist(c3);
		
		Year y1 = new Year(1, "First");
		DBUtils.persist(y1);
		Year y2 = new Year(2, "Second");
		DBUtils.persist(y2);
		Year y3 = new Year(3, "Third");
		DBUtils.persist(y3);
		Year y4 = new Year(4, "Fourth");
		DBUtils.persist(y4);
		Year y5 = new Year(5, "Fifth");
		DBUtils.persist(y5);
		
		DegreeProgram dp1 = new DegreeProgram();
		dp1.setName("MS");
		dp1.setClassfication(c2);
		
		DegreeProgram dp2 = new DegreeProgram();
		dp2.setName("BS");
		dp2.setClassfication(c1);
		
		DegreeProgram dp3 = new DegreeProgram();
		dp3.setName("PostDoc");
		dp3.setClassfication(c3);
		
		DBUtils.persist(dp1);
		DBUtils.persist(dp2);
		DBUtils.persist(dp3);
		
		return 0;
	}

	/*@SuppressWarnings("unchecked")
	public int getClassificationCount() {
		
		String fetchAllCfnQuery = "SELECT c FROM Classification c";
		List<Classification> classifications = (List<Classification>) DBUtils.fetchAllEntities(fetchAllCfnQuery);
		
		return classifications.size();
	}
	
	@SuppressWarnings("unchecked")
	public int getYearCount() {
		
		String fetchAllQuery = "SELECT c FROM Year c";
		List<Year> classifications = (List<Year>) DBUtils.fetchAllEntities(fetchAllQuery);
		
		return classifications.size();
	}*/
	
	@Test
	public void testDataGeneration() {
		
		// Actually persist the test data
		generateTestData();
		
		Assert.assertEquals("Number of classifications persisted is different", 
				DEFAULT_CLASSIFICATION_COUNT, getCount(Classification.class));
		
		Assert.assertEquals("Number of years persisted is different", 
				DEFAULT_YEAR_COUNT, getCount(Year.class));
		
		Assert.assertEquals("Number of classifications persisted is different", 
				DEFAULT_DEGPROG_COUNT, getCount(DegreeProgram.class));
	}
	
	@After
	public void clearTestData() {
		
		// Actually persist the test data
	
		DBUtils.removeAllEntities("DegreeProgram");
		DBUtils.removeAllEntities("Year");
		DBUtils.removeAllEntities("Classification");
		
		
				
        System.out.println("@After: executedAfterEach");

	}
	
	
}
