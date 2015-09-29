package edu.dbms.library.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="library")
public class Library {

	@Id 
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long libraryId;
	
	private String libraryName;
	
	@OneToOne(optional=false, cascade= CascadeType.PERSIST)
	@JoinColumn(name="address_id")
	private Address libraryAddress;

	@Column(name="library_id")
	public long getLibraryId() {
		return libraryId;
	}

	public void setLibraryId(long libraryId) {
		this.libraryId = libraryId;
	}

	@Column(name="library_name")
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
