package edu.dbms.library.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="address")
public class Address {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long addressId;
	
	private String addressLineOne;
	
	private String addressLineTwo;
	
	private String cityName;
	
	private int pinCode;

	@Column(name="add_line_one", nullable=false)
	public String getAddressLineOne() {
		return addressLineOne;
	}

	public void setAddressLineOne(String addressLineOne) {
		this.addressLineOne = addressLineOne;
	}
	
	@Column(name="add_line_two", nullable=true)
	public String getAddressLineTwo() {
		return addressLineTwo;
	}

	public void setAddressLineTwo(String addressLineTwo) {
		this.addressLineTwo = addressLineTwo;
	}
	
	@Column(name="city", nullable=false)
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name="pin_code", nullable=false)
	public int getPinCode() {
		return pinCode;
	}

	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}
	
	@Column(name="address_id")
	public long getAddressId() {
		return addressId;
	}

	public Address(String addressLineOne, String addressLineTwo, String cityName, int pinCode) {
		this.addressLineOne = addressLineOne;
		this.addressLineTwo = addressLineTwo;
		this.cityName = cityName;
		this.pinCode = pinCode;
	}
	
	public Address(){}
}
