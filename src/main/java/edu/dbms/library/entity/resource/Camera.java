package edu.dbms.library.entity.resource;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import edu.dbms.library.entity.Library;

@Entity
@Table(name="camera")
@DiscriminatorValue("7")
@PrimaryKeyJoinColumn(name="camera_id", referencedColumnName="asset_id")
public class Camera extends Asset {
	
	@ManyToOne
	@JoinColumn(name="camera_detail_id")
	private CameraDetail detail;
	
	public Camera() {
		super();
	}
	
	public Camera(Library containingLibrary, CameraDetail detail) {
		super(containingLibrary);
		this.detail = detail;
	}
	
	/*public Camera(Library containingLibrary, String maker, String model, 
			String lensDetail, int memoryAvailable) {
		super(containingLibrary);
		this.maker = maker;
		this.model = model;
		this.lensDetail = lensDetail;
		this.memoryAvailable = memoryAvailable;
	}

	*/
	
	public CameraDetail getDetail() {
		return detail;
	}
	public void setDetail(CameraDetail detail) {
		this.detail = detail;
	}

	public Object[] toObjectArray() {
		List<Object> objects = new LinkedList<Object>();
		objects.add(this.detail.getMaker());
		objects.add(this.detail.getModel());
		objects.add(this.detail.getLensDetail());
		objects.add(this.detail.getMemoryAvailable());
		
		return objects.toArray();
	}
}
