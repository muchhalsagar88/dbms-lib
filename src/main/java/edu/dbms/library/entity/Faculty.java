package edu.dbms.library.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="faculty")
@DiscriminatorValue("F")
public class Faculty extends Patron {
	
	@ManyToOne
	private FacultyCategory category;

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
