package edu.dbms.library.entity.resource;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import edu.dbms.library.entity.Author;
import edu.dbms.library.entity.Library;

@Entity
@Table(name="book")
@DiscriminatorValue("B")
public class Book extends Publication {
	
	private String isbnNumber;
	
	@ManyToMany(mappedBy="books")
	private Collection<Author> authors;
	
	public Book() {
		super();
	}
	
	public Book(Library containingLibrary, PublicationFormat format, String title, 
			String edition, int year, String isbnNumber) {
		super(containingLibrary, format, title, edition, year);
		this.isbnNumber = isbnNumber;
	}
	
	public Book(String isbnNumber) {
		super();
		this.isbnNumber = isbnNumber;
	}
	
	public String getIsbnNumber() {
		return isbnNumber;
	}

	public void setIsbnNumber(String isbnNUmber) {
		this.isbnNumber = isbnNUmber;
	}

	public Collection<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Collection<Author> authors) {
		this.authors = authors;
	}
	
	public void setAuthor(Author author) {
		if(this.authors == null)
			this.authors = new ArrayList<Author>();
		this.authors.add(author);
	}
	
}
