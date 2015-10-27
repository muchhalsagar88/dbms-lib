package edu.dmbs.library.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.catalog.AssetPatronConstraint;
import junit.framework.Assert;

public class AssetPatronConstraintTest extends BaseTest {

	@Before
	public void generateTestData() {
		
		AssetPatronConstraint constraint;/* =  new AssetPatronConstraint(1, );
		DBUtils.persist(constraint);*/
		
		constraint =  new AssetPatronConstraint(1, 'S', 14*24, 24 ,2.0f);
		DBUtils.persist(constraint);
		
		constraint =  new AssetPatronConstraint(2, 'F', 12, 24, 0.0f);
		DBUtils.persist(constraint);
		
		constraint =  new AssetPatronConstraint(2, 'S', 12, 24, 0.0f);
		DBUtils.persist(constraint);
		
		constraint =  new AssetPatronConstraint(3, 'F', 12, 24, 0.0f);
		DBUtils.persist(constraint);
		
		constraint =  new AssetPatronConstraint(3, 'S', 12, 24, 0.0f);
		DBUtils.persist(constraint);
		
		constraint =  new AssetPatronConstraint(4, 'S', 4, 24, 2.0f);
		DBUtils.persist(constraint);
		
	}

	@Test
	public void testDataGeneration() {
		
		Assert.assertEquals("Number of asset-patron constraints is different", 
				6, getCount(AssetPatronConstraint.class));
	}
	
	@After
	public void clearTestData() {
		
		removeAllEntities(AssetPatronConstraint.class);
		System.out.println("@After: executedAfterEach");
	}
}
