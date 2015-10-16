package edu.dbms.library.entity.catalog;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import edu.dbms.library.entity.AbsEntity;

@Entity
@Table(name="year")
public class Year extends AbsEntity {
	
	@Id
	private long id;
	
	private String name;

	@ManyToMany(mappedBy="years")
	private Collection<DegreeProgram> degreePrograms;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Year(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Year() {}

	public Collection<DegreeProgram> getDegreePrograms() {
		return degreePrograms;
	}

	public void setDegreePrograms(Collection<DegreeProgram> degreePrograms) {
		this.degreePrograms = degreePrograms;
	}

	public void setDegreeProgram(DegreeProgram degreeProgram) {
		if(this.degreePrograms == null)
			this.degreePrograms = new ArrayList<DegreeProgram>();
		this.degreePrograms.add(degreeProgram);
	}
}
