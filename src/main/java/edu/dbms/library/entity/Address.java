package edu.dbms.library.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
	
	private String addressLineOne;
	
	private String addressLineTwo;
	
	private String cityName;
	
	private int pinCode;

	public String getAddressLineOne() {
		return addressLineOne;
	}

	public void setAddressLineOne(String addressLineOne) {
		this.addressLineOne = addressLineOne;
	}
	
	public String getAddressLineTwo() {
		return addressLineTwo;
	}

	public void setAddressLineTwo(String addressLineTwo) {
		this.addressLineTwo = addressLineTwo;
	}
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getPinCode() {
		return pinCode;
	}

	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}
	
	public Address(String addressLineOne, String addressLineTwo, String cityName, int pinCode) {
		this.addressLineOne = addressLineOne;
		this.addressLineTwo = addressLineTwo;
		this.cityName = cityName;
		this.pinCode = pinCode;
	}
	
	public Address(){}
}
