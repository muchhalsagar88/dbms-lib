package edu.dbms.library.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="course")
public class Course extends AbsEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="course_name")
	private String courseName;
	
	@ManyToMany(mappedBy="courses")
	private Collection<Faculty> faculties;
	
	@ManyToMany(mappedBy="courses")
	private Collection<Student> students;
	
	public Course() {
		super();
	}
	
	public long getCourseId() {
		return id;
	}

	public void setCourseId(long courseId) {
		this.id = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public Collection<Faculty> getFaculties() {
		return faculties;
	}

	public void setFaculties(Collection<Faculty> faculties) {
		this.faculties = faculties;
	}
	
	public void setFaculty(Faculty faculty) {
		if(this.faculties == null)
			this.faculties = new ArrayList<Faculty>();
		this.faculties.add(faculty);
	}
	
	public Collection<Student> getStudents() {
		return students;
	}

	public void setStudents(Collection<Student> students) {
		this.students = students;
	}
	
	public void setStudent(Student student) {
		if(this.students == null)
			this.students = new ArrayList<Student>();
		this.students.add(student);
	}
}
