package edu.dbms.library.entity.reserve;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PubWaitlistPK {

	@Column(name="patron_id", nullable=false)
	private String patronId;

	@Column(name="pub_secondary_id", nullable=false)
	private String pubSecondaryId;

	public String getPatronId() {
		return patronId;
	}

	public void setPatronId(String patronId) {
		this.patronId = patronId;
	}

	public String getPubSecondaryId() {
		return pubSecondaryId;
	}

	public void setPubSecondaryId(String pubSecondaryId) {
		this.pubSecondaryId = pubSecondaryId;
	}

	public PubWaitlistPK(String patronId, String pubSecondaryId) {
		this.patronId = patronId;
		this.pubSecondaryId = pubSecondaryId;
	}

	public PubWaitlistPK() {
		// TODO Auto-generated constructor stub
	}
}
