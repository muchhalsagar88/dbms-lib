package edu.dbms.library.entity.catalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import edu.dbms.library.entity.AbsEntity;

@Entity
@Table(name="asset_type")
public class AssetType extends AbsEntity {
	
	@Id
	private int assetTypeId;
	
	private String category;
	
	@Column(name="sub_category")
	private String subCategory;

	public int getAssetTypeId() {
		return assetTypeId;
	}

	public void setAssetTypeId(int assetTypeId) {
		this.assetTypeId = assetTypeId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public AssetType(int assetTypeId, String category, String subCategory) {
		super();
		this.assetTypeId = assetTypeId;
		this.category = category;
		this.subCategory = subCategory;
	}
	
	public AssetType() {}
}
