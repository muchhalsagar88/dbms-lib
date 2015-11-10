package edu.dbms.library.entity.resource;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="camera")
@DiscriminatorValue("7")
@PrimaryKeyJoinColumn(name="camera_id", referencedColumnName="asset_id")
public class Camera extends Asset {
	
	public Camera() {
		super();
	}
	
	/*public Camera(Library containingLibrary, String maker, String model, 
			String lensDetail, int memoryAvailable) {
		super(containingLibrary);
		this.maker = maker;
		this.model = model;
		this.lensDetail = lensDetail;
		this.memoryAvailable = memoryAvailable;
	}

	
	
	public Object[] toObjectArray() {
		List<Object> objects = new LinkedList<Object>();
		objects.add(this.maker);
		objects.add(this.model);
		objects.add(this.lensDetail);
		objects.add(this.memoryAvailable);
		
		return objects.toArray();
	}*/
}
