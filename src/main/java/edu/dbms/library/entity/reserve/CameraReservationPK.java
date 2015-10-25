package edu.dbms.library.entity.reserve;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CameraReservationPK {

	@Column(name="camera_id", nullable = false)
	private String cameraId;
	
	@Column(name="patron_id", nullable = false)
	private String patronId;

	public String getCameraId() {
		return cameraId;
	}

	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}

	public String getPatronId() {
		return patronId;
	}

	public void setPatronId(String patronId) {
		this.patronId = patronId;
	}

	public CameraReservationPK(String cameraId, String patronId) {
		this.cameraId = cameraId;
		this.patronId = patronId;
	}
	
	public CameraReservationPK() {}
}
