package edu.dbms.library.entity.catalog;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import edu.dbms.library.entity.AbsEntity;
import edu.dbms.library.entity.Student;

@Table(name="degree_year", uniqueConstraints=@UniqueConstraint(columnNames={"degree_program_id", "year_id"}))
@Entity
public class DegreeYear extends AbsEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="degree_year_id")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="degree_program_id")
	private DegreeProgram degreeProgram;
	
	@ManyToOne
	@JoinColumn(name="year_id")
	private Year year;

	public Collection<Student> getStudents() {
		return students;
	}

	public void setStudents(Collection<Student> students) {
		this.students = students;
	}

	@OneToMany(mappedBy="degreeYear")
	private Collection<Student> students;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DegreeProgram getDegreeProgram() {
		return degreeProgram;
	}

	public void setDegreeProgram(DegreeProgram degreeProgram) {
		this.degreeProgram = degreeProgram;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}
}
