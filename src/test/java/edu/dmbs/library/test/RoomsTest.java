package edu.dmbs.library.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.Address;
import edu.dbms.library.entity.Library;
import edu.dbms.library.entity.resource.ConferenceRoom;
import edu.dbms.library.entity.resource.Room;
import edu.dbms.library.entity.resource.StudyRoom;
import junit.framework.Assert;

public class RoomsTest extends BaseTest {

	public static final long DEFAULT_FC_COUNT = 5;
	
	private List<Room> _generateTestData() {
		
		
		Library lib = new Library(); 
		lib.setLibraryName("James Hunt Library");
		lib.setLibraryAddress(new Address("101 Partner's Drive", null, "Raleigh", 27606));
		
		DBUtils.persist(lib);
		
		
		List<Room> roomList = new ArrayList<Room>();
		
		Room r1 = new StudyRoom();
		r1.setCapacity(40);
		r1.setFloorLevel(2);
		r1.setLibrary(lib);
		r1.setRoomNo(201);
		
		Room r2 = new StudyRoom();
		r2.setCapacity(150);
		r2.setFloorLevel(1);
		r2.setLibrary(lib);
		r2.setRoomNo(115);
		
		Room r3 = new StudyRoom();
		r3.setCapacity(30);
		r3.setFloorLevel(2);
		r3.setLibrary(lib);
		r3.setRoomNo(205);
		
		Room r4 = new ConferenceRoom();
		r4.setCapacity(250);
		r4.setFloorLevel(1);
		r4.setLibrary(lib);
		r4.setRoomNo(116);
		
		
		Room r5 = new ConferenceRoom();
		r5.setCapacity(30);
		r5.setFloorLevel(3);
		r5.setLibrary(lib);
		r5.setRoomNo(301);
		
		roomList.add(r1); roomList.add(r2); roomList.add(r3);
		roomList.add(r4); roomList.add(r5);
			
		return roomList;
	}
	
	@Before
	public void generateTestData() {
		
		List<Room> roomList = _generateTestData();
		for(Room room: roomList)
			DBUtils.persist(room);
	}
	
	@Test
	public void testDataGeneration() {
		
		Assert.assertEquals("Number of faculty categories persisted is different", 
				DEFAULT_FC_COUNT, getCount(Room.class));
	}
	
	@After
	public void clearTestData() {
		
		removeAllEntities(Room.class);
		removeAllEntities(Library.class);
		
        System.out.println("@After: executedAfterEach");
	}
	
	
}
