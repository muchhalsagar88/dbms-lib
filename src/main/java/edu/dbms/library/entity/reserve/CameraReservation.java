package edu.dbms.library.entity.reserve;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.dbms.library.entity.AbsEntity;
import edu.dbms.library.entity.Patron;
import edu.dbms.library.entity.resource.Camera;

@Entity
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

	public CameraReservation(String cameraId, String patronId, Patron patron, Date reserveDate) {
		this.cameraReservationKey.setCameraId(cameraId);
		this.cameraReservationKey.setPatronId(patronId);
		this.patron = patron;
		this.reserveDate = reserveDate;
	}
	
	public CameraReservation() {
		// TODO Auto-generated constructor stub
	}
}
