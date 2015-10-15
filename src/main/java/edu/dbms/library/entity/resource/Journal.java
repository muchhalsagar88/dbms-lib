package edu.dbms.library.entity.resource;

import java.util.Collection;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import edu.dbms.library.entity.Author;

@Entity
@Table(name="journal")
@DiscriminatorValue("J")
public class Journal extends Publication {

	@ManyToMany(mappedBy="journals")
	private Collection<Author> authors;
}
