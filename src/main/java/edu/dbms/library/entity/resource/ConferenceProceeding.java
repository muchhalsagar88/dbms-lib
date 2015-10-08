package edu.dbms.library.entity.resource;

import java.util.Collection;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import edu.dbms.library.entity.Author;

@Entity
@Table(name="conf_proceeding")
@DiscriminatorValue("C")
public class ConferenceProceeding extends Publication {

	private int confNumber;
	
	private String confName;
	
	@ManyToMany(mappedBy="confPapers")
	private Collection<Author> authors;

	public ConferenceProceeding() {
		super();
	}
	
	public int getConfNumber() {
		return confNumber;
	}

	public void setConfNumber(int confNumber) {
		this.confNumber = confNumber;
	}

	public String getConfName() {
		return confName;
	}

	public void setConfName(String confName) {
		this.confName = confName;
	}

	public Collection<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Collection<Author> authors) {
		this.authors = authors;
	}
	
}
