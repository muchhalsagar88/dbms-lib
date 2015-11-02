package edu.dbms.library.entity.resource;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="room")
@DiscriminatorValue("11")
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="room_id", referencedColumnName="asset_id")
public class Room extends Asset {
	
	private String roomNo;
	
	private int floorLevel;
	
	private int capacity;

	public Room() {
		super();
	}
	
	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
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
