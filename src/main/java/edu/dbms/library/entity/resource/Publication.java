package edu.dbms.library.entity.resource;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import edu.dbms.library.entity.Library;

@Entity
@Table(name="publication")
@DiscriminatorValue("P")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="pub_type", discriminatorType=DiscriminatorType.CHAR)
public class Publication extends Resource {
	
	private String publicationFormat;

	private String title;
	
	private String edition;
	
	private int publicationYear;
	
	public Publication() {
		super();
		publicationFormat = PublicationFormat.HARDCOPY.name();
	}
	
	public Publication(PublicationFormat format, String title, String edition, int year) {
		super();
		this.publicationFormat = format.name();
		this.title = title;
		this.edition = edition;
		this.publicationYear = year;
	}
	
	public Publication(Library containingLibrary, PublicationFormat format, String title, String edition, int year) {
		super(containingLibrary);
		this.publicationFormat = format.name();
		this.title = title;
		this.edition = edition;
		this.publicationYear = year;
	}
	
	public PublicationFormat getPublicationFormat() {
		return PublicationFormat.valueOf(publicationFormat);
	}

	public void setPublicationFormat(PublicationFormat publicationFormat) {
		this.publicationFormat = publicationFormat.name();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public void setPublicationFormat(String publicationFormat) {
		this.publicationFormat = publicationFormat;
	}
	
}
