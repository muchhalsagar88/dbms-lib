package edu.dmbs.library.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.Address;
import edu.dbms.library.entity.Author;
import edu.dbms.library.entity.Library;
import edu.dbms.library.entity.resource.Book;
import edu.dbms.library.entity.resource.PublicationFormat;
import junit.framework.Assert;

public class BooksTest extends BaseTest {

	public static final long DEFAULT_BOOK_COUNT = 2;
	
	private List<Book> _generateTestData() {
		
		Library lib = new Library(); 
		lib.setLibraryName("James Hunt Library");
		lib.setLibraryAddress(new Address("101 Partner's Drive", null, "Raleigh", 27606));
		
		DBUtils.persist(lib);
		
		PublicationFormat format1 = PublicationFormat.HARDCOPY;
		PublicationFormat format2 = PublicationFormat.ELECTRONIC;
		
		Author a1= new Author();
		a1.setId("101");
		a1.setName("Salman Rushdie");
		
		
		Author a2= new Author();
		a2.setId("102");
		a2.setName("J K Rowling");
		
		DBUtils.persist(a1);
		DBUtils.persist(a2);
		
		List<Book> bookList = new ArrayList<Book>();
		Book b1 = new Book(lib, format1, "Satanic Verses", "2001", 2002,"isbn201012" );
		b1.setAuthor(a1);
		bookList.add(b1);
		
				
		Book b2 = new Book(lib, format2, "Harry Potter", "2001", 2005,"isbn201012" );
		b2.setAuthor(a2);
		bookList.add(b2);
	
		
		return bookList;
	}
	
	@Before
	public void generateTestData() {
		
		List<Book> bookList = _generateTestData();
		for(Book book: bookList)
			DBUtils.persist(book);
		
	}
	
	@Test
	public void testDataGeneration() {
		
		Assert.assertEquals("Number of Books persisted is different", 
				DEFAULT_BOOK_COUNT, getCount(Book.class));
	}
	
	@After
	public void clearTestData() {
		
		removeAllEntities(Book.class);
		removeAllEntities(Author.class);
		removeAllEntities(Library.class);
		
        System.out.println("@After: executedAfterEach");
	}
}
