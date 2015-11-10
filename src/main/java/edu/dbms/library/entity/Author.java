package edu.dbms.library.entity;

import java.util.Collection;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import edu.dbms.library.entity.resource.BookDetail;
import edu.dbms.library.entity.resource.ConferenceProceedingDetail;
import edu.dbms.library.entity.resource.JournalDetail;

@Entity
@Table(name="author")
public class Author extends AbsEntity {

	@Id
	private String id;
	
	private String name;

	@ManyToMany(mappedBy="authors")
	private Collection<BookDetail> books;
	
	@ManyToMany(mappedBy="authors")
	private Collection<JournalDetail> journals;
	
	@ManyToMany(mappedBy="authors")
	private Collection<ConferenceProceedingDetail> confPapers;
	
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
