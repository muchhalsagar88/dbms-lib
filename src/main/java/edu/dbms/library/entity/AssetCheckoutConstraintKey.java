package edu.dbms.library.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AssetCheckoutConstraintKey {

	@Column(name="asset_secondary_id", nullable = false)
	private String assetSecondaryId;
	
	@Column(name="patron_id", nullable = false)
	private String patronId;

	public String getAssetSecondaryId() {
		return assetSecondaryId;
	}

	public void setAssetSecondaryId(String assetSecondaryId) {
		this.assetSecondaryId = assetSecondaryId;
	}

	public String getPatronId() {
		return patronId;
	}

	public void setPatronId(String patronId) {
		this.patronId = patronId;
	}

}
