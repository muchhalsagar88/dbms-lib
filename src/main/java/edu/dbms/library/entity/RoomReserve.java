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
public class RoomReserve implements Serializable {

	
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
	@JoinColumn(name="checkout_id", referencedColumnName="chckout_id")
	private AssetCheckout checkOut;

}
