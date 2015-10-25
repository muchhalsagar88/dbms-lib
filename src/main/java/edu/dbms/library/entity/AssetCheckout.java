package edu.dbms.library.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.dbms.library.entity.resource.Asset;

@Entity
@Table(name="asset_checkout")
public class AssetCheckout {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private Asset asset;

	@ManyToOne
	@JoinColumn(name="patron_id",  referencedColumnName="patron_id")
	private Patron patron;

	@Column(name="asset_secondary_id")
	private String assetSecondaryId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="issue_date")
	private Date issueDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="due_date")
	private Date dueDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="return_date")
	private Date returnDate;	

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public String getAssetSecondaryId() {
		return assetSecondaryId;
	}

	public void setAssetSecondaryId(String assetSecondaryId) {
		this.assetSecondaryId = assetSecondaryId;
	}

	public Patron getPatron() {
		return patron;
	}

	public void setPatron(Patron patron) {
		this.patron = patron;
	}

	public long getId() {
		return id;
	}
}
