package edu.dbms.library.entity.resource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="room")
@PrimaryKeyJoinColumn(name="room_id", referencedColumnName="id")
public class Room extends Asset {
	
	private int roomNo;
	
	private int floorLevel;
	
	private int capacity;

	@Column(name="room_type")
	private char roomType;
	
	public Room() {
		super();
	}
	
	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}

	public int getFloorLevel() {
		return floorLevel;
	}

	public void setFloorLevel(int floorLevel) {
		this.floorLevel = floorLevel;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
}
