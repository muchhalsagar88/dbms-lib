package edu.dbms.library.entity.resource;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import edu.dbms.library.entity.AbsEntity;
import edu.dbms.library.entity.Author;

public class PublicationDetail extends AbsEntity {
	
	private Integer id;
	
}
