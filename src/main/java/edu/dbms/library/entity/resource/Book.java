package edu.dbms.library.entity.resource;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import edu.dbms.library.entity.Author;
import edu.dbms.library.entity.Library;
import edu.dbms.library.entity.Publisher;

@Entity
@Table(name="book")
@PrimaryKeyJoinColumn(name="book_id", referencedColumnName="asset_id")
@DiscriminatorValue("1")
public class Book extends Publication {

	private String isbnNumber;

	public Book() {
		super();
	}

	public Book(Library containingLibrary, PublicationFormat format, String title, 
			String edition, int year, String isbnNumber,Publisher publisher) {
		super(containingLibrary, format, title, edition, year);
		this.isbnNumber = isbnNumber;
		this.publisher = publisher;
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
	
	
	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public Object[] toObjectArray() {
		List<Object> objects = new LinkedList<Object>();
		objects.add(this.isbnNumber);
		objects.add(this.getTitle());
		objects.add(this.getEdition());
		Collection<Author> authList = getAuthors();
		
		StringBuffer authrs = new StringBuffer(""); 
		for(Author a: authList){
			authrs.append(a.getName()+",");
		}
		objects.add(authrs.toString());
		objects.add(this.getPublicationYear());
		objects.add(this.getPublicationFormat());
		objects.add(publisher.getName());
		return objects.toArray();
	}
	
	@ManyToOne
	private Publisher publisher;
}
