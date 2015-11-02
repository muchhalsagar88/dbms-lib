package edu.dbms.library.entity.resource;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.dbms.library.entity.AbsEntity;

@Entity
@Table(name="camera_detail")
public class CameraDetail extends AbsEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="camera_detail_id")
	private String id;
	
	private String maker;
	
	private String model;
	
	private String lensDetail;
	
	private int memoryAvailable;
	
	@OneToMany(mappedBy="detail")
	private Collection<Camera> cameras;
	
	public String getId() {
		return id;
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
