package edu.dbms.library.entity.catalog;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import edu.dbms.library.entity.AbsEntity;

@Entity
@Table(name="asset_patron_constraint")
public class AssetPatronConstraint extends AbsEntity {
	
	@EmbeddedId
	private AssetPatronConstaintPK key;
	
	// duration will be in hours
	private int duration;
	
	// this will be 1 for an hourly fine
	// and 24 for a daily fine
	@Column(name="fine_duration")
	private int fineDuration;
	
	private float fine;
	
	public AssetPatronConstaintPK getKey() {
		return key;
	}

	public void setKey(AssetPatronConstaintPK key) {
		this.key = key;
	}

	public int getFineDuration() {
		return fineDuration;
	}

	public void setFineDuration(int fineDuration) {
		this.fineDuration = fineDuration;
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
		this.key = new AssetPatronConstaintPK(assetType, patronType);
		this.duration = duration;
		this.fine = fine;
	}
	
	public AssetPatronConstraint() {
		// TODO Auto-generated constructor stub
	}
}
