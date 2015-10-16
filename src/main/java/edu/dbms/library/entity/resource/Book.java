package edu.dbms.library.entity.resource;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import edu.dbms.library.entity.Library;

@Entity
@Table(name="book")
@DiscriminatorValue("B")
public class Book extends Publication {
	
	private String isbnNumber;
	
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

}
