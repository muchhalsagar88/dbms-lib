package edu.dbms.library.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.dbms.library.entity.resource.Room;

/**
 * Entity implementation class for Entity: RoomReserve
 *
 */
@Entity
@Table(name="room_reservation")
public class RoomReserve extends AbsEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public RoomReserve() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long reservation_id;
	
	@ManyToOne
	private Room room;

	@ManyToOne
	@JoinColumn(name="patron_id",  referencedColumnName="patron_id")
	private Patron patron;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_time")
	private Date startTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_time")
	private Date endTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reserve_time")
	private Date rTime;
	
	@OneToOne
	@JoinColumn(name="checkout_id", referencedColumnName="id")
	private AssetCheckout checkOut;

	public long getReservation_id() {
		return reservation_id;
	}

	public void setReservation_id(long reservation_id) {
		this.reservation_id = reservation_id;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Patron getPatron() {
		return patron;
	}

	public void setPatron(Patron patron) {
		this.patron = patron;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getrTime() {
		return rTime;
	}

	public void setrTime(Date rTime) {
		this.rTime = rTime;
	}

	public AssetCheckout getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(AssetCheckout checkOut) {
		this.checkOut = checkOut;
	}

}
