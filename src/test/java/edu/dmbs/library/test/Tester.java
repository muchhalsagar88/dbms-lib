package edu.dmbs.library.test;

import org.junit.Test;

import junit.framework.Assert;

@Deprecated
public class Tester {
	
	public void testDataGeneration() {
		
		// Can use reflection to find all classes implementing ITest and
		// can call the appropriate methods
		/*Assert.assertEquals("Number of libraries persisted is different", 
				LibraryTest.DEFAULT_LIBRARY_COUNT, 
				new LibraryTest().generateTestData());*/
	}
}
