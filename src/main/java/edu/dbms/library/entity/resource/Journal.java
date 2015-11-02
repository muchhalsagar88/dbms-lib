package edu.dbms.library.entity.resource;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="journal")
@PrimaryKeyJoinColumn(name="journal_id", referencedColumnName="asset_id")
@DiscriminatorValue("2")
public class Journal extends Publication {

	@ManyToOne
	@JoinColumn(name="issn_number")
	private JournalDetail details;
	
	public Journal() {
		super();
	}

	public JournalDetail getDetails() {
		return details;
	}

	public void setDetails(JournalDetail details) {
		this.details = details;
	}

	
	/*public Object[] toObjectArray() {
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
	}*/
}
