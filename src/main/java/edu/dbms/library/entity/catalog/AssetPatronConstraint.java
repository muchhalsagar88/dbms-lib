package edu.dbms.library.entity.catalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import edu.dbms.library.entity.AbsEntity;

@Entity
@Table(name="asset_patron_constraint")
public class AssetPatronConstraint extends AbsEntity {
	
	private int assetType;
	
	@Column(name="patron_type")
	private char patronType;
	
	// duration will be in hours
	private int duration;
	
	// this will be 1 for an hourly fine
	// and 24 for a daily fine
	@Column(name="fine_duration")
	private int fineDuration;
	
	private float fine;
	
	public int getFineDuration() {
		return fineDuration;
	}

	public void setFineDuration(int fineDuration) {
		this.fineDuration = fineDuration;
	}

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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public float getFine() {
		return fine;
	}

	public void setFine(float fine) {
		this.fine = fine;
	}

	public AssetPatronConstraint(int assetType, char patronType, int duration, int fineDuration, float fine) {
		super();
		this.assetType = assetType;
		this.patronType = patronType;
		this.duration = duration;
		this.fine = fine;
	}
	
}
