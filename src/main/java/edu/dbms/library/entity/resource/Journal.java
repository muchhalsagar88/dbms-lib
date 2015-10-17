package edu.dbms.library.entity.resource;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import edu.dbms.library.entity.Library;

@Entity
@Table(name="journal")
@DiscriminatorValue("J")
public class Journal extends Publication {

	private String issnNumber;

	public Journal() {
		super();
	}

	public Journal(Library containingLibrary, PublicationFormat format, String title, 
			String edition, int year, String issnNumber) {
		super(containingLibrary, format, title, edition, year);
		this.issnNumber = issnNumber;
	}

	public Journal(String issnNumber) {
		super();
		this.issnNumber = issnNumber;
	}

	public String getIssnNumber() {
		return issnNumber;
	}

	public void setIssnNumber(String issnNUmber) {
		this.issnNumber = issnNUmber;
	}
}
