package edu.dbms.library.db.manager;

import java.util.ArrayList;
import java.util.List;

import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.catalog.AssetType;

public class AssetTypeManager extends DBManager {

	public static void persistCatalogData(){
		
		if(DBUtils.findEntity(AssetType.class, 1, Integer.class) != null)
			return;
		
		List<AssetType> types = new ArrayList<AssetType>();
		
		AssetType type = new AssetType(1, "Publication", "Book");
		types.add(type);
		
		type = new AssetType(2, "Publication", "Journal");
		types.add(type);
		
		type = new AssetType(3, "Publication", "ConferenceProceeding");
		types.add(type);
		
		type = new AssetType(4, "Publication", "Reserved Book");
		types.add(type);
		
		type = new AssetType(5, "Room", "Study Room");
		types.add(type);
		
		type = new AssetType(6, "Room", "Conference Room");
		types.add(type);
		
		type = new AssetType(7, "Device", "Camera");
		types.add(type);
		
		try {
			DBUtils.persist(types);
		} catch(Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public static void main(String []args) {
		
		AssetTypeManager.persistCatalogData();
	}
}
