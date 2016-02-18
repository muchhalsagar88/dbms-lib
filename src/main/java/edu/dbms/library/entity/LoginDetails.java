package edu.dbms.library.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="login_details")
public class LoginDetails extends AbsEntity {

	@Id
	private String username;

	private String password;

	@OneToOne
	@JoinColumn(name="patron_id", nullable=false)
	private Patron patron;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Patron getPatron() {
		return patron;
	}

	public void setPatron(Patron patron) {
		this.patron = patron;
	}

	public LoginDetails(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public LoginDetails(){}

	@Override
	public String toString() {
		return "LoginDetails [username=" + username + ", password=" + password + "]";
	}

}
