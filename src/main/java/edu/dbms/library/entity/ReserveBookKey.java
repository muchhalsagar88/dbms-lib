package edu.dbms.library.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ReserveBookKey {
	
	// ADD DB Constraint over this
	@Column(name="book_isbn")
	private String isbnNumber;
	
	@Column(name="course_id")
	private long courseId;
	
	@Column(name="faculty_id")
	private String facultyId;

	public String getIsbnNumber() {
		return isbnNumber;
	}

	public void setIsbnNumber(String isbnNumber) {
		this.isbnNumber = isbnNumber;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(String facultyId) {
		this.facultyId = facultyId;
	}

	@Override
	public int hashCode() {
		
		int result;
		 
        result = (int) (this.courseId%Integer.MAX_VALUE);
        result = 31 * result + (this.isbnNumber != null ? this.isbnNumber.hashCode() : 0);
        result = 31 * result + (this.facultyId != null ? this.facultyId.hashCode() : 0);
        return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(obj==null || (obj.getClass() != getClass()))
			return false;
		
		ReserveBookKey thatKey = (ReserveBookKey)obj;
		
		if(this.isbnNumber != thatKey.getIsbnNumber())
			return false;
		if(this.courseId != thatKey.getCourseId())
			return false;
		if(this.facultyId != thatKey.getFacultyId())
			return false;
		
		return true;
	}
	
	public ReserveBookKey(){}

	public ReserveBookKey(String isbnNumber, long courseId, String facultyId) {
		super();
		this.isbnNumber = isbnNumber;
		this.courseId = courseId;
		this.facultyId = facultyId;
	}
	
}
