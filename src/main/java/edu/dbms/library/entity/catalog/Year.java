package edu.dbms.library.entity.catalog;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.dbms.library.entity.AbsEntity;
import edu.dbms.library.entity.Patron;

@Entity
@Table(name="year")
public class Year extends AbsEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String name;

	@OneToMany(mappedBy="year")
	private Collection<DegreeYear> degreeYears;
	
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
}
