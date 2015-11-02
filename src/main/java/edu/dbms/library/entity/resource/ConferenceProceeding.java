package edu.dbms.library.entity.resource;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="conf_proceeding")
@PrimaryKeyJoinColumn(name="conf_proc_id", referencedColumnName="asset_id")
@DiscriminatorValue("3")
public class ConferenceProceeding extends Publication {

	@ManyToOne
	@JoinColumn(name="conf_num")
	private ConferenceProceedingDetail details;
		
	public ConferenceProceeding() {
		super();
	}

	public ConferenceProceedingDetail getDetails() {
		return details;
	}

	public void setDetails(ConferenceProceedingDetail details) {
		this.details = details;
	}
	
	

	/*public Object[] toObjectArray() {
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
	}*/
	
}
