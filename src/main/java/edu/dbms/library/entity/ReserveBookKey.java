package edu.dbms.library.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import edu.dbms.library.entity.resource.Book;

@Embeddable
public class ReserveBookKey {
	
	@ManyToOne
	@Column(name="book_id", nullable = false)
	private Book book;
	
	@ManyToOne
	@Column(name="course_id", nullable = false)
	private Course course;
	
	@ManyToOne
	@Column(name="patron_id", nullable = false)
	private Patron patron;
	
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Patron getPatron() {
		return patron;
	}

	public void setPatron(Patron patron) {
		this.patron = patron;
	}
}
