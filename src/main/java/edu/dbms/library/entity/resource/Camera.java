package edu.dbms.library.entity.resource;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import edu.dbms.library.entity.Library;

@Entity
@Table(name="camera")
@DiscriminatorValue("7")
@PrimaryKeyJoinColumn(name="camera_id", referencedColumnName="asset_id")
public class Camera extends Asset {
	
	private String maker;
	
	private String model;
	
	private String lensDetail;
	
	private int memoryAvailable;

	public Camera() {
		super();
	}
	
	public Camera(Library containingLibrary, String maker, String model, 
			String lensDetail, int memoryAvailable) {
		super(containingLibrary);
		this.maker = maker;
		this.model = model;
		this.lensDetail = lensDetail;
		this.memoryAvailable = memoryAvailable;
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
	
	public Object[] toObjectArray() {
		List<Object> objects = new LinkedList<Object>();
		objects.add(this.maker);
		objects.add(this.model);
		objects.add(this.lensDetail);
		objects.add(this.memoryAvailable);
		
		return objects.toArray();
	}
}
