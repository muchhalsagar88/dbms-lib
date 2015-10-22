package edu.dbms.library.entity.resource;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="conf_proceeding")
@PrimaryKeyJoinColumn(name="conf_proc_id", referencedColumnName="id")
@DiscriminatorValue("2")
public class ConferenceProceeding extends Publication {

	private String confNumber;
	
	private String confName;
	
	public ConferenceProceeding() {
		super();
	}
	
	public String getConfNumber() {
		return confNumber;
	}

	public void setConfNumber(String confNumber) {
		this.confNumber = confNumber;
	}

	public String getConfName() {
		return confName;
	}

	public void setConfName(String confName) {
		this.confName = confName;
	}

}
