package edu.dbms.library.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="fine_paid")
public class FinePaid extends AbsEntity {

	@Id
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="paid_date")
	private Date paidDate;

	private float amount;

	@ManyToOne
	private Patron patron;

	@OneToOne
	private AssetCheckout assetCheckout;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Patron getPatron() {
		return patron;
	}

	public void setPatron(Patron patron) {
		this.patron = patron;
	}

	public FinePaid(Date paidDate, float amount, Patron patron, AssetCheckout assetCheckout) {
		this();
		this.paidDate = paidDate;
		this.amount = amount;
		this.patron = patron;
		this.assetCheckout = assetCheckout;
	}

	 public FinePaid() {
		 this.id = UUID.randomUUID().toString();
	 }
}
