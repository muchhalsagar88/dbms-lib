package edu.dbms.library.entity;

import java.util.Collection;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import edu.dbms.library.entity.resource.Book;
import edu.dbms.library.entity.resource.ConferenceProceeding;
import edu.dbms.library.entity.resource.Journal;

@Entity
@Table(name="author")
public class Author extends AbsEntity {

	@Id
	private String id;
	
	private String name;

	@ManyToMany
	private Collection<Book> books;
	
	@ManyToMany
	private Collection<Journal> journals;
	
	@ManyToMany
	private Collection<ConferenceProceeding> confPapers;
	
	public Author() {
		this.id = UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
