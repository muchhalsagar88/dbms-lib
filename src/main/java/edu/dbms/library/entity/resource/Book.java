package edu.dbms.library.entity.resource;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="book")
@PrimaryKeyJoinColumn(name="book_id", referencedColumnName="asset_id")
@DiscriminatorValue("1")
public class Book extends Publication {

	@ManyToOne
	@Column(name="isbn_number")
	private BookDetail detail;
	
	public Book() {
		super();
	}

	/*public Object[] toObjectArray() {
		List<Object> objects = new LinkedList<Object>();
		objects.add(this.isbnNumber);
		objects.add(this.getTitle());
		objects.add(this.getEdition());
		Collection<Author> authList = getAuthors();
		
		StringBuffer authrs = new StringBuffer(""); 
		for(Author a: authList){
			authrs.append(a.getName()+",");
		}
		objects.add(authrs.toString());
		objects.add(this.getPublicationYear());
		objects.add(this.getPublicationFormat());
		objects.add(publisher.getName());
		return objects.toArray();
	}*/
	
	
}
