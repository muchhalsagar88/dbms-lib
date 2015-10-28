package edu.dbms.library.entity.catalog;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.dbms.library.entity.AbsEntity;
import edu.dbms.library.entity.Patron;

@Entity
@Table(name="degree_program")
public class DegreeProgram extends AbsEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	@ManyToOne
	private Classification classification;

	@OneToMany(mappedBy="degreeProgram")
	private Collection<DegreeYear> degreeYears;
	
	public Classification getClassification() {
		return classification;
	}

	public void setClassification(Classification classification) {
		this.classification = classification;
	}

	public Collection<DegreeYear> getDegreeYears() {
		return degreeYears;
	}

	public void setDegreeYears(Collection<DegreeYear> degreeYears) {
		this.degreeYears = degreeYears;
	}

	public void setDegreeYear(DegreeYear degreeYear) {
		if(this.degreeYears == null)
			this.degreeYears = new ArrayList<DegreeYear>();
		this.degreeYears.add(degreeYear);
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
