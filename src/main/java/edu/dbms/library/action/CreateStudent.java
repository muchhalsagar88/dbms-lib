package edu.dbms.library.action;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.dbms.library.entity.Student;

public class CreateStudent {

	public static void main(String[] args) {
		
		 EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "main" );
	      
	      EntityManager entitymanager = emfactory.createEntityManager( );
	      entitymanager.getTransaction( ).begin( );

	      Student employee = new Student( ); 
	      employee.setEid( 1201 );
	      employee.setEname( "Gopal" );
	      employee.setSalary( 40000 );
	      employee.setDeg( "Technical Manager" );
	      
	      entitymanager.persist( employee );
	      entitymanager.getTransaction( ).commit( );

	      entitymanager.close( );
	      emfactory.close( );

	}

}
