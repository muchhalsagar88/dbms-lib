package edu.dbms.library.entity.catalog;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AssetPatronConstaintPK {
	
	@Column(name="asset_type_id")
	private int assetType;
	
	@Column(name="patron_type")
	private char patronType;

	public int getAssetType() {
		return assetType;
	}

	public void setAssetType(int assetType) {
		this.assetType = assetType;
	}

	public char getPatronType() {
		return patronType;
	}

	public void setPatronType(char patronType) {
		this.patronType = patronType;
	}

	public AssetPatronConstaintPK(int assetType, char patronType) {
		super();
		this.assetType = assetType;
		this.patronType = patronType;
	}
	
	public AssetPatronConstaintPK() {
		// TODO Auto-generated constructor stub
	}
}
