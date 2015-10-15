package edu.dbms.library.entity.resource;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="camera")
@DiscriminatorValue("C")
public class Camera extends Resource {
	
	private int cameraId;
	
	private String maker;
	
	private String model;
	
	private String lensDetail;
	
	private int memoryAvailable;

	public Camera() {
		super();
	}
	
	public int getCameraId() {
		return cameraId;
	}

	public void setCameraId(int cameraId) {
		this.cameraId = cameraId;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getLensDetail() {
		return lensDetail;
	}

	public void setLensDetail(String lensDetail) {
		this.lensDetail = lensDetail;
	}

	public int getMemoryAvailable() {
		return memoryAvailable;
	}

	public void setMemoryAvailable(int memoryAvailable) {
		this.memoryAvailable = memoryAvailable;
	}
	
}
