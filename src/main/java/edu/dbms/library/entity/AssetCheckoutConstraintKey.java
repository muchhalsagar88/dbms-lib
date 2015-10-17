package edu.dbms.library.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class AssetCheckoutConstraintKey {

	@Column(name="asset_secondary_id", nullable = false)
	private String assetSecondaryId;
	
	@ManyToOne
	@Column(name="patron_id", nullable = false)
	private Patron patron;

	public String getAssetSecondaryId() {
		return assetSecondaryId;
	}

	public void setAssetSecondaryId(String assetSecondaryId) {
		this.assetSecondaryId = assetSecondaryId;
	}

	public Patron getPatron() {
		return patron;
	}

	public void setPatron(Patron patron) {
		this.patron = patron;
	}
}
