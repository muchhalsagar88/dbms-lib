package edu.dbms.library.entity.resource;

import java.util.UUID;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.dbms.library.entity.AbsEntity;
import edu.dbms.library.entity.Library;

@Entity
@Table(name="parent_resource")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="asset_type", discriminatorType=DiscriminatorType.CHAR)
public class Resource extends AbsEntity {
	
	@Id
	private String id;
	
	@ManyToOne
	@JoinColumn(name="library_id", nullable=false)
	private Library library;

	public Resource() {
		this.id = UUID.randomUUID().toString();
	}
	
	public Resource(Library library) {
		this();
		this.library = library;
	}

	public String getId() {
		return id;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}
	
	
}
