package edu.dbms.library.entity.reserve;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.dbms.library.entity.AbsEntity;
import edu.dbms.library.entity.Patron;

@Entity
@Table(name="publication_waitlist")
public class PublicationWaitlist extends AbsEntity {

	@EmbeddedId
	private PubWaitlistPK key;

	@MapsId("patronId")
    @JoinColumn(name ="patron_id", referencedColumnName = "patron_id")
	@ManyToOne
	private Patron patron;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="request_date")
	private Date requestDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_time")
	private Date startTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_time")
	private Date endTime;
	
	@Column(name="is_student")
	private int isStudent;


	public PubWaitlistPK getKey() {
		return key;
	}

	public void setKey(PubWaitlistPK key) {
		this.key = key;
	}

	public Patron getPatron() {
		return patron;
	}

	public void setPatron(Patron patron) {
		this.patron = patron;
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
		this.key = new PubWaitlistPK(patronId, pubSecondaryId);
		this.requestDate = requestDate;
		this.isStudent = isStudent;
	}

	public PublicationWaitlist() {
		super();
	}

}
