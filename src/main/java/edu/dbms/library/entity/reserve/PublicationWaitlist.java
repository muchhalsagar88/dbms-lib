package edu.dbms.library.entity.reserve;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.dbms.library.entity.AbsEntity;
import edu.dbms.library.entity.Patron;
import edu.dbms.library.entity.resource.Publication;

@Entity
@Table(name="publication_waitlist")
@IdClass(PubWaitlistPK.class)
public class PublicationWaitlist extends AbsEntity {

	
	
	@Id
	private String patronId;
	
	@Id 
	private String pubSecondaryId;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="request_date")
	private Date requestDate;

	@Column(name="is_student")
	private int isStudent;


	public String getPatronId() {
		return patronId;
	}

	public void setPatronId(String patronId) {
		this.patronId = patronId;
	}

	public String getPubSecondaryId() {
		return pubSecondaryId;
	}

	public void setPubSecondaryId(String pubSecondaryId) {
		this.pubSecondaryId = pubSecondaryId;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public int getIsStudent() {
		return isStudent;
	}

	public void setIsStudent(int isStudent) {
		this.isStudent = isStudent;
	}

	public PublicationWaitlist(String patronId, String pubSecondaryId, Date requestDate, int isStudent) {
		super();
		this.patronId = patronId;
		this.pubSecondaryId = pubSecondaryId;
		this.requestDate = requestDate;
		this.isStudent = isStudent;
	}

	public PublicationWaitlist() {
		super();
	}
	
	
			
	
}
