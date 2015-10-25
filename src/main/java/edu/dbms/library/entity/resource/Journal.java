package edu.dbms.library.entity.resource;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import edu.dbms.library.entity.Author;
import edu.dbms.library.entity.Library;

@Entity
@Table(name="journal")
@PrimaryKeyJoinColumn(name="journal_id", referencedColumnName="asset_id")
@DiscriminatorValue("3")
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
	
	public Object[] toObjectArray() {
		List<Object> objects = new LinkedList<Object>();
		objects.add(this.issnNumber);
		objects.add(this.getTitle());
		Collection<Author> authList = getAuthors();
		
		StringBuffer authrs = new StringBuffer(""); 
		for(Author a: authList){
			authrs.append(a.getName()+",");
		}
		objects.add(authrs.toString());
		objects.add(this.getPublicationYear());
		
		return objects.toArray();
	}
}
