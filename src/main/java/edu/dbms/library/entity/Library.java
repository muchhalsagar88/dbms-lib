package edu.dbms.library.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="library")
public class Library {

	@Id 
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="library_id")
	private long libraryId;
	
	@Column(name="library_name")
	private String libraryName;
	
	@Embedded
	@JoinColumn(name="address_id")
	private Address libraryAddress;

	public long getLibraryId() {
		return libraryId;
	}

	public void setLibraryId(long libraryId) {
		this.libraryId = libraryId;
	}

	public String getLibraryName() {
		return libraryName;
	}

	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}

	public Address getLibraryAddress() {
		return libraryAddress;
	}

	public void setLibraryAddress(Address libraryAddress) {
		this.libraryAddress = libraryAddress;
	}
	
}
