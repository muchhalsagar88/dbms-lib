package edu.dbms.library.entity.reserve;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class CameraReservationPK {

	@Column(name="camera_id", nullable = false)
	private String cameraId;
	
	@Column(name="patron_id", nullable = false)
	private String patronId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="issue_date")
	// The Friday when the patron wants the camera
	private Date issueDate;

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

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public CameraReservationPK(String cameraId, String patronId, Date issueDate) {
		this.cameraId = cameraId;
		this.patronId = patronId;
		this.issueDate = issueDate;
	}
	
	public CameraReservationPK() {}

	@Override
	public String toString() {
		return "CameraReservationPK [cameraId=" + cameraId + ", patronId=" + patronId + ", issueDate=" + issueDate
				+ "]";
	}

}
