package edu.dbms.library.entity.resource;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="journal")
@DiscriminatorValue("J")
public class Journal extends Publication {
	
}
