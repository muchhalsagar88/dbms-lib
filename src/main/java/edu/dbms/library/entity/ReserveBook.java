package edu.dbms.library.entity;

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

@Entity
@Table(name="reserve_book")
public class ReserveBook {
	
	@EmbeddedId
	private ReserveBookKey reserveBookKey;
		
	@Temporal(TemporalType.DATE)
	@Column(name="from_date")
	private Date fromDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="toDate")
	private Date toDate;

	@MapsId("courseId")
    @JoinColumn(name ="course_id", referencedColumnName = "id")
    @ManyToOne
	private Course course;
	
	@MapsId("facultyId")
    @JoinColumn(name ="faculty_id", referencedColumnName = "patron_id")
    @ManyToOne
	private Faculty faculty;
	
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public ReserveBook(ReserveBookKey reserveBookKey, Date fromDate, Date toDate, Course course, Faculty faculty) {
		super();
		this.reserveBookKey = reserveBookKey;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.course = course;
		this.faculty = faculty;
	}
	
	public ReserveBook() {}
	
}
