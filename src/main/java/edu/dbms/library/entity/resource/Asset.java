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
import edu.dbms.library.entity.AssetCheckout;
import edu.dbms.library.entity.Library;

@Entity
@Table(name="asset")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="asset_type", discriminatorType=DiscriminatorType.INTEGER)
public class Asset extends AbsEntity {
	
	@Id
	private String id;

	@ManyToOne
	@JoinColumn(name="library_id", nullable=false)
	private Library library;

	@OneToMany(mappedBy="asset")
	private Collection<AssetCheckout> assetCheckouts;

	public Collection<AssetCheckout> getAssetCheckouts() {
		return assetCheckouts;
	}

	public void setAssetCheckouts(Collection<AssetCheckout> assetCheckouts) {
		this.assetCheckouts = assetCheckouts;
	}

	public void setAssetCheckout(AssetCheckout assetCheckout) {
		if(this.assetCheckouts == null)
			this.assetCheckouts = new ArrayList<AssetCheckout>();
		this.assetCheckouts.add(assetCheckout);
	}

	public Asset() {
		this.id = UUID.randomUUID().toString();
	}

	public Asset(Library library) {
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
