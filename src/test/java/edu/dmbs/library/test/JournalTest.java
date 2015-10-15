package edu.dmbs.library.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.Address;
import edu.dbms.library.entity.Author;
import edu.dbms.library.entity.FacultyCategory;
import edu.dbms.library.entity.Library;
import edu.dbms.library.entity.resource.Journal;
import edu.dbms.library.entity.resource.PublicationFormat;
import junit.framework.Assert;

public class JournalTest extends BaseTest {

	public static final long DEFAULT_FC_COUNT = 2;
	
	private List<Journal> _generateTestData() {
		
		
		Library lib = new Library(); 
		lib.setLibraryName("James Hunt Library");
		lib.setLibraryAddress(new Address("101 Partner's Drive", null, "Raleigh", 27606));
		
		DBUtils.persist(lib);
		
		PublicationFormat format1 = PublicationFormat.HARDCOPY;
		PublicationFormat format2 = PublicationFormat.ELECTRONIC;
		
		Author a1= new Author();
		a1.setId("101");
		a1.setName("Some Author1");
		
		
		Author a2= new Author();
		a2.setId("102");
		a2.setName("Some Author 2");
		
		DBUtils.persist(a1);
		DBUtils.persist(a2);
		
		
		
		List<Journal> journalList = new ArrayList<Journal>();
		
		Journal j1 = new Journal();
		j1.setEdition("2001");
		j1.setLibrary(lib);
		j1.setPublicationFormat(format1);
		j1.setPublicationYear(2004);
		j1.setTitle("This research is beyond your understanding");
		
		Journal j2 = new Journal();
		j2.setEdition("2002");
		j2.setLibrary(lib);
		j2.setPublicationFormat(format2);
		j2.setPublicationYear(2002);
		j2.setTitle("This research is really crazy dude....");
		
		journalList.add(j1); journalList.add(j2);
		return journalList;
	}
	
	@Before
	public void generateTestData() {
		
		List<Journal> journalList = _generateTestData();
		for(Journal journal: journalList)
			DBUtils.persist(journal);
			
	}
	
	@Test
	public void testDataGeneration() {
		
		Assert.assertEquals("Number of faculty categories persisted is different", 
				DEFAULT_FC_COUNT, getCount(Journal.class));
	}
	
	@After
	public void clearTestData() {
		
		removeAllEntities(Journal.class);
		removeAllEntities(Author.class);
		removeAllEntities(Library.class);
		
        System.out.println("@After: executedAfterEach");
	}
	
}
