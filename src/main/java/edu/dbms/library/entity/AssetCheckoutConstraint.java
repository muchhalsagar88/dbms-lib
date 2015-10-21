package edu.dbms.library.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="asset_checkout_constraint")
public class AssetCheckoutConstraint {
	
	@OneToOne
	private AssetCheckout assetCheckout;
	
	@EmbeddedId
	private AssetCheckoutConstraintKey assetCheckoutConstraintKey;

	@MapsId("patronId")
    @JoinColumn(name ="patron_id", referencedColumnName = "patron_id")
	@ManyToOne
	private Patron patron;
	
	public AssetCheckout getAssetCheckout() {
		return assetCheckout;
	}

	public void setAssetCheckout(AssetCheckout assetCheckout) {
		this.assetCheckout = assetCheckout;
	}
}
