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
@Table(name="journal")
@DiscriminatorValue("J")
public class Journal extends Publication {

	private String issnNumber;

	@ManyToMany(mappedBy="journals")
	private Collection<Author> authors;

	public Journal() {
		super();
	}

	public Journal(Library containingLibrary, PublicationFormat format, String title, 
			String edition, int year, String issnNumber) {
		super(containingLibrary, format, title, edition, year);
		this.issnNumber = issnNumber;
	}

	public Journal(String issnNumber) {
		super();
		this.issnNumber = issnNumber;
	}

	public String getIssnNumber() {
		return issnNumber;
	}

	public void setIssnNumber(String issnNUmber) {
		this.issnNumber = issnNUmber;
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
