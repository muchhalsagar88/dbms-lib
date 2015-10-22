package edu.dbms.library.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="faculty")
@PrimaryKeyJoinColumn(name="faculty_id", referencedColumnName="patron_id")
@DiscriminatorValue("P")
public class Faculty extends Patron {
	
	@ManyToOne
	private FacultyCategory category;

	@JoinTable(name="teach",
			joinColumns = {
				@JoinColumn(name="faculty_id", referencedColumnName="faculty_id")
			},
			inverseJoinColumns = {
					@JoinColumn(name="course_id", referencedColumnName="id")
			}
	)
	@ManyToMany
	private Collection<Course> courses;
	
	@OneToMany(mappedBy="faculty")
	private Collection<ReserveBook> reserveBooks;
	
	public Collection<Course> getCourses() {
		return courses;
	}

	public void setCourses(Collection<Course> courses) {
		this.courses = courses;
	}

	public void setCourse(Course course) {
		if(this.courses == null)
			this.courses = new ArrayList<Course>();
		this.courses.add(course);
	}
	
	public Faculty() {
		super();
	}
	
	public FacultyCategory getCategory() {
		return category;
	}

	public void setCategory(FacultyCategory category) {
		this.category = category;
	}
	
}
