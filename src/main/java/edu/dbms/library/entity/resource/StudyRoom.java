package edu.dbms.library.entity.resource;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="study_room")
@DiscriminatorValue("5")
@PrimaryKeyJoinColumn(name="study_room_id", referencedColumnName="id")
public class StudyRoom extends Room {

}
