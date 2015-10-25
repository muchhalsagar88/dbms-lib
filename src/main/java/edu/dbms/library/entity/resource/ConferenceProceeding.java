package edu.dbms.library.entity.resource;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import edu.dbms.library.entity.Author;

@Entity
@Table(name="conf_proceeding")
@PrimaryKeyJoinColumn(name="conf_proc_id", referencedColumnName="asset_id")
@DiscriminatorValue("2")
public class ConferenceProceeding extends Publication {

	private String confNumber;
	
	private String confName;
	
	public ConferenceProceeding() {
		super();
	}
	
	public String getConfNumber() {
		return confNumber;
	}

	public void setConfNumber(String confNumber) {
		this.confNumber = confNumber;
	}

	public String getConfName() {
		return confName;
	}

	public void setConfName(String confName) {
		this.confName = confName;
	}

	public Object[] toObjectArray() {
		List<Object> objects = new LinkedList<Object>();
		
		objects.add(this.confNumber);
		objects.add(this.getTitle());
		objects.add(this.getConfName());
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
