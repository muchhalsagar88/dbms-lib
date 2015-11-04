package edu.dbms.library.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="notification")
public class Notification extends AbsEntity {

	@Id
	private String id;

	private String notification;

	private int isRead;

	@ManyToOne
	private Patron patron;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public Patron getPatron() {
		return patron;
	}

	public void setPatron(Patron patron) {
		this.patron = patron;
	}

	public Notification(){
		this.id = UUID.randomUUID().toString();
	}

	public Notification(String notification, int isRead, Patron patron) {
		this();
		this.notification = notification;
		this.isRead = isRead;
		this.patron = patron;
	}

	public Object[] toObjectArray() {
		List<Object> objects = new LinkedList<Object>();
		objects.add(this.getNotification());

		return objects.toArray();
	}
}
