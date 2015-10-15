package edu.dbms.library.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="publisher")
public class Publisher extends AbsEntity {

	@Id
	private int id;
	
	private String name;
	
	public Publisher(){}
	
	public Publisher(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
