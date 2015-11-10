package edu.dbms.library.entity.resource;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.dbms.library.entity.AbsEntity;
import edu.dbms.library.entity.Author;
import edu.dbms.library.entity.Publisher;

@Entity
@Table(name="book_detail")
public class BookDetail extends AbsEntity {

	@Id
	@Column(name="isbn_number")
	private String isbnNumber;
	
	private String title;

	private String edition;

	private int publicationYear;

	@ManyToOne
	private Publisher publisher;
	
	@ManyToMany
	@JoinTable(
		      name="BOOK_AUTHOR",
		      joinColumns={@JoinColumn(name="BOOK_ID", referencedColumnName="isbn_number")},
		      inverseJoinColumns={@JoinColumn(name="AUTHOR_ID", referencedColumnName="ID")})
	private Collection<Author> authors;
	
	@OneToMany(mappedBy="detail")
	private Collection<Book> books;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
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
	
	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	
}
