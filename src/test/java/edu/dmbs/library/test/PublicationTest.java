package edu.dmbs.library.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.Author;
import edu.dbms.library.entity.Library;
import edu.dbms.library.entity.resource.Book;
import edu.dbms.library.entity.resource.PublicationFormat;
import edu.dmbs.library.test.utils.TestEntityGenerator;
import junit.framework.Assert;

public class PublicationTest extends BaseTest {

	private Library testLib;
	private List<Author> authors;
	private List<Book> books;
	
	private int DEFAULT_BOOK_COUNT;
	
	public PublicationTest() {
		this.authors = new ArrayList<Author>();
		this.books = new ArrayList<Book>();
	}
	
	@Before
	public void generateTestData() {
		
		// Persist dependencies
		this.testLib = TestEntityGenerator.generateLibrary("Library 1");
		this.authors.add(TestEntityGenerator.generateAuthor("Author One"));
		this.authors.add(TestEntityGenerator.generateAuthor("Author Two"));
		DBUtils.persist(this.testLib);
		DBUtils.persist(this.authors);
		
		Book book = new Book(testLib, PublicationFormat.HARDCOPY, "Book Title One", "First edition", 2001,
				"1-84356-028-3");
		book.setAuthors(authors);
		books.add(book);
		
		book = new Book(testLib, PublicationFormat.HARDCOPY, "Book Title Two", "Seocnd edition", 2004,
				"0-684-84328-5");
		book.setAuthors(authors);
		books.add(book);
		
		DBUtils.persist(books);
		
		this.DEFAULT_BOOK_COUNT = books.size();
	}
	
	@Test
	public void testDataGeneration() {
		
		Assert.assertEquals("Number of books persisted is different", 
				DEFAULT_BOOK_COUNT, getCount(Book.class));
	}
	
	@After
	public void clearGeneratedData() {
		removeAllEntities(Book.class);
		removeAllEntities(Author.class);
		removeAllEntities(Library.class);
		/*for(Book b: this.books) {
			DBUtils.removeEntity(Book.class, b.getId(), String.class);
		}
		for(Author a: this.authors) {
			DBUtils.removeEntity(Author.class, a.getId(), String.class);
		}
		DBUtils.removeEntity(Library.class, this.testLib.getLibraryId(), long.class);*/
	}
	
	/*public static void main(String []args) {
		PublicationTest test = new PublicationTest();
		test.generateTestData();
		test.testDataGeneration();
		test.clearGeneratedData();
		System.out.println("............ DONE ..............");
	}*/
}
