package edu.dbms.library.entity.resource;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="conference_room")
@DiscriminatorValue("6")
@PrimaryKeyJoinColumn(name="conf_room_id", referencedColumnName="asset_id")
public class ConferenceRoom extends Room {

}
