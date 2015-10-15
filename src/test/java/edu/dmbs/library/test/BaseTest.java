package edu.dmbs.library.test;

import java.util.List;

import edu.dbms.library.db.DBUtils;

public class BaseTest {

	@SuppressWarnings("unchecked")
	public <T> int getCount(Class<T> c) {
		
		String fetchAllQuery = String.format("SELECT x FROM %s x", c.getName());
		List<T> list = (List<T>) DBUtils.fetchAllEntities(fetchAllQuery);
		
		return list.size();
	}
	
	public static  <T> int removeAllEntities(Class<T> c) {
		
		String query = "Delete from "+ c.getSimpleName();
		return DBUtils.removeAllEntities(query);
	}
}
