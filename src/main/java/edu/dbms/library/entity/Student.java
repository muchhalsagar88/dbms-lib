package edu.dbms.library.entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.dbms.library.entity.catalog.DegreeYear;

@Entity
@Table(name="student")
@PrimaryKeyJoinColumn(name="student_id", referencedColumnName="patron_id")
@DiscriminatorValue("S")
public class Student extends Patron {

	@Column(name="phone_no", nullable=false)
	private String phoneNumber;
	
	@Column(name="alt_phone_no")
	private String alternatePhoneNumber;
	
	@Embedded
	private Address homeAddress;
	
	@ManyToOne
	private DegreeYear degreeYear;
	
	public Collection<Course> getCourses() {
		return courses;
	}

	public void setCourses(Collection<Course> courses) {
		this.courses = courses;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="dob")
	private Date dateOfBirth;
	
	private Character sex;

	@JoinTable(name="enroll",
			joinColumns = {
				@JoinColumn(name="student_id", referencedColumnName="student_id")
			},
			inverseJoinColumns = {
					@JoinColumn(name="course_id", referencedColumnName="id")
			}
	)
	@ManyToMany
	private Collection<Course> courses;

	public Student() {}
	
	public Student(String phoneNumber, String alternatePhoneNumber, Address homeAddress, Date dateOfBirth,
			Character sex) {
		super();
		this.phoneNumber = phoneNumber;
		this.alternatePhoneNumber = alternatePhoneNumber;
		this.homeAddress = homeAddress;
		this.dateOfBirth = dateOfBirth;
		this.sex = sex;
	}

	public boolean isPhoneNumberNull() {
		return phoneNumber == null;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isAlternatePhoneNumberNull() {
		return alternatePhoneNumber == null;
	}
	
	public String getAlternatePhoneNumber() {
		return alternatePhoneNumber;
	}

	public void setAlternatePhoneNumber(String alternatePhoneNumber) {
		this.alternatePhoneNumber = alternatePhoneNumber;
	}

	public DegreeYear getDegreeYear() {
		return degreeYear;
	}

	public void setDegreeYear(DegreeYear degreeYear) {
		this.degreeYear = degreeYear;
	}

	public boolean isHomeAddressNull() {
		return homeAddress == null;
	}
	
	public Address getHomeAddress() {
		return homeAddress;
	}

	public String getHomeAddressString() {
		String homeAddrString = "";
		if(homeAddress.getAddressLineOne() != null)
			homeAddrString = homeAddrString.concat(homeAddress.getAddressLineOne() + "\n");
		if(homeAddress.getAddressLineTwo() != null)
			homeAddrString = homeAddrString.concat(homeAddress.getAddressLineTwo() + "\n");
		if(homeAddress.getCityName() != null)
			homeAddrString = homeAddrString.concat(homeAddress.getCityName() + "\n");
		if(homeAddress.getPinCode() != 0)
			homeAddrString = homeAddrString.concat(Integer.toString(homeAddress.getPinCode()));
		
		return homeAddrString;
	}
	
	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}

	public boolean isDateOfBirthNull() {
		return dateOfBirth == null;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public boolean isSexNull() {
		return sex == null;
	}
	
	public Character getSex() {
		return sex;
	}

	public void setSex(Character sex) {
		this.sex = sex;
	}
}
