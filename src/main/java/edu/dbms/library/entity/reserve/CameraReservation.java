package edu.dbms.library.entity.reserve;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.dbms.library.entity.AbsEntity;
import edu.dbms.library.entity.AssetCheckout;
import edu.dbms.library.entity.Patron;
import edu.dbms.library.entity.resource.Camera;
import edu.dbms.library.listener.CameraReservationListener;

@Entity
@EntityListeners({CameraReservationListener.class})
@Table(name="camera_reservation")
public class CameraReservation extends AbsEntity {

	@EmbeddedId
	private CameraReservationPK cameraReservationKey;
	
	@MapsId("patronId")
    @JoinColumn(name ="patron_id", referencedColumnName = "patron_id")
	private Patron patron;
	
	@MapsId("cameraId")
    @JoinColumn(name ="camera_id", referencedColumnName = "camera_id")
	private Camera camera;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reserve_date")
	private Date reserveDate;
	
	@Column(name="reservation_status", columnDefinition="VARCHAR2(20) DEFAULT 'ACTIVE'")
	private String status;
	
	@OneToOne
	@JoinColumn(name="checkout_id", referencedColumnName="id")
	private AssetCheckout assetCheckout;

	public CameraReservationPK getCameraReservationKey() {
		return cameraReservationKey;
	}

	public void setCameraReservationKey(CameraReservationPK cameraReservationKey) {
		this.cameraReservationKey = cameraReservationKey;
	}

	public Patron getPatron() {
		return patron;
	}

	public void setPatron(Patron patron) {
		this.patron = patron;
	}

	public Date getReserveDate() {
		return reserveDate;
	}

	public void setReserveDate(Date reserveDate) {
		this.reserveDate = reserveDate;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AssetCheckout getAssetCheckout() {
		return assetCheckout;
	}

	public void setAssetCheckout(AssetCheckout assetCheckout) {
		this.assetCheckout = assetCheckout;
	}

	public CameraReservation(String cameraId, String patronId, Patron patron, Date reserveDate,
			Date issueDate) {
		this();
		this.getCameraReservationKey().setCameraId(cameraId);
		this.getCameraReservationKey().setIssueDate(issueDate);
		this.patron = patron;
		this.reserveDate = reserveDate;
	}
	
	public CameraReservation() {
		this.status = "ACTIVE";
		this.cameraReservationKey = new CameraReservationPK();
	}

	@Override
	public String toString() {
		return "CameraReservation [cameraReservationKey=" + cameraReservationKey + ", reserveDate=" + reserveDate
				+ ", status=" + status + "]";
	}
	
	
}
