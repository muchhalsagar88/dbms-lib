package edu.dbms.library.entity.resource;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.dbms.library.entity.AbsEntity;
import edu.dbms.library.entity.Author;

@Entity
@Table(name="journal_detail")
public class JournalDetail extends AbsEntity {
	
	@Id
	@Column(name="issn_number")
	private String issnNumber;
	
	private String title;

	private int publicationYear;

	@OneToMany(mappedBy="details")
	private Collection<Journal> journals;

	@ManyToMany
	@JoinTable(
		      name="JOURNAL_AUTHOR",
		      joinColumns={@JoinColumn(name="JOURNAL_ID", referencedColumnName="issn_number")},
		      inverseJoinColumns={@JoinColumn(name="AUTHOR_ID", referencedColumnName="ID")})
	private Collection<Author> authors;

	public String getIssnNumber() {
		return issnNumber;
	}

	public void setIssnNumber(String issnNumber) {
		this.issnNumber = issnNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public Collection<Journal> getJournals() {
		return journals;
	}

	public void setJournals(Collection<Journal> journals) {
		this.journals = journals;
	}

	public Collection<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Collection<Author> authors) {
		this.authors = authors;
	}
	
}
