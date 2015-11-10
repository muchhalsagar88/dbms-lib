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
@Table(name="conference_proceeding_detail")
public class ConferenceProceedingDetail extends AbsEntity {
	
	@Id
	@Column(name="conf_num")
	private String confNumber;
	
	private String conferenceName;
	
	private String title;
	
	@OneToMany(mappedBy="details")
	private Collection<ConferenceProceeding> confProceedings;

	@ManyToMany
	@JoinTable(
		      name="CONF_PROC_AUTHOR",
		      joinColumns={@JoinColumn(name="CONF_NUM", referencedColumnName="conf_num")},
		      inverseJoinColumns={@JoinColumn(name="AUTHOR_ID", referencedColumnName="ID")})
	private Collection<Author> authors;
	
	public String getConfNumber() {
		return confNumber;
	}

	public void setConfNumber(String confNumber) {
		this.confNumber = confNumber;
	}

	public String getConferenceName() {
		return conferenceName;
	}

	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Collection<ConferenceProceeding> getConfProceedings() {
		return confProceedings;
	}

	public void setConfProceedings(Collection<ConferenceProceeding> confProceedings) {
		this.confProceedings = confProceedings;
	}

	public Collection<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Collection<Author> authors) {
		this.authors = authors;
	}

}
