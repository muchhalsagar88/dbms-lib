package edu.dbms.library.entity.resource;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="publication")
@DiscriminatorValue("10")
@PrimaryKeyJoinColumn(name="publication_id", referencedColumnName="asset_id")
@Inheritance(strategy=InheritanceType.JOINED)
public class Publication extends Asset {
	
	private String publicationFormat;
	
	public Publication() {
		super();
	}
	
	public PublicationFormat getPublicationFormat() {
		return PublicationFormat.valueOf(publicationFormat);
	}

	public void setPublicationFormat(PublicationFormat publicationFormat) {
		this.publicationFormat = publicationFormat.name();
	}
	
}
