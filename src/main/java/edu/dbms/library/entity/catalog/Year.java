package edu.dbms.library.entity.catalog;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import edu.dbms.library.entity.AbsEntity;

@Entity
@Table(name="degree_year")
public class Year extends AbsEntity {
	
	@Id
	private long id;
	
	private String name;

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
}
