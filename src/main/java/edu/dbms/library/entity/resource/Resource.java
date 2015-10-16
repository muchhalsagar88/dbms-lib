package edu.dbms.library.entity.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.dbms.library.entity.AbsEntity;
import edu.dbms.library.entity.Library;
import edu.dbms.library.entity.ResourceCheckout;

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

	@OneToMany(mappedBy="resource_id")
	private Collection<ResourceCheckout> resourceCheckouts;

	public Collection<ResourceCheckout> getResourceCheckouts() {
		return resourceCheckouts;
	}

	public void setResourceCheckouts(Collection<ResourceCheckout> resourceCheckouts) {
		this.resourceCheckouts = resourceCheckouts;
	}

	public void setResourceCheckout(ResourceCheckout resourceCheckout) {
		if(this.resourceCheckouts == null)
			this.resourceCheckouts = new ArrayList<ResourceCheckout>();
		this.resourceCheckouts.add(resourceCheckout);
	}

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
