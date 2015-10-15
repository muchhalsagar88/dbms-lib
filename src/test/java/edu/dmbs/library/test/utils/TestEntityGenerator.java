package edu.dmbs.library.test.utils;

import edu.dbms.library.entity.Address;
import edu.dbms.library.entity.Author;
import edu.dbms.library.entity.Library;

public class TestEntityGenerator {

	public static Library generateLibrary(String libraryName) {

		Library lib = new Library();
		lib.setLibraryName(libraryName);
		lib.setLibraryAddress(new Address("101 Partner's Drive", null, "Raleigh", 27606));

		return lib;
	}
	
	public static Author generateAuthor(String name) {
		
		Author author = new Author();
		author.setName(name);
		
		return author;
	}
}
