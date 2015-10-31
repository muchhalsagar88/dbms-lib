package edu.dbms.library.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="patron")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="patron_type", discriminatorType=DiscriminatorType.CHAR, columnDefinition="CHAR(1)")
public class Patron extends AbsEntity {

	@Id
	@Column(name="patron_id")
	private String id;

	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	@Column(name="email_address")
	private String emailAddress;

	private String nationality;

	@Column(updatable= true, columnDefinition="CHAR(1) DEFAULT 'N'")
	private char hold;

	@ManyToOne
	private Department department;

	@OneToMany(mappedBy="patron")
	private Collection<AssetCheckout> assetCheckouts;

	@OneToOne(mappedBy="patron")
	private LoginDetails loginDetails;

	public Collection<AssetCheckout> getAssetCheckouts() {
		return assetCheckouts;
	}

	public void setAssetCheckouts(Collection<AssetCheckout> assetCheckouts) {
		this.assetCheckouts = assetCheckouts;
	}

	public void setAssetCheckout(AssetCheckout assetCheckout) {
		if(this.assetCheckouts == null)
			this.assetCheckouts = new ArrayList<AssetCheckout>();
		this.assetCheckouts.add(assetCheckout);
	}

	public Patron() {
		this.hold = 'N';
		this.id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public boolean isEmailAddresNull() {
		return emailAddress == null;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public boolean getHold() {
		return hold == 'Y';
	}

	public void setHold(boolean toSet) {
		if(toSet)
			this.hold = 'Y';
		else
			this.hold = 'N';
	}

	@Override
	public String toString() {
		return "Patron [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", emailAddress="
				+ emailAddress + ", nationality=" + nationality + ", hold=" + hold
				+ ", loginDetails=" + loginDetails + "]";
	}

}
