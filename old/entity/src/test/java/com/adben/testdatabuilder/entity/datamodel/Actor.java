package com.adben.testdatabuilder.entity.datamodel;

import com.google.common.collect.Lists;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "actor", indexes = @Index(columnList = "last_name", name = "idx_actor_last_name"))
public class Actor implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "actor_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
  private Short actorId;

  @Column(name = "first_name", nullable = false, columnDefinition = "VARCHAR(45)", length = 45)
  private String firstName;

  @Column(name = "last_name", nullable = false, columnDefinition = "VARCHAR(45)", length = 45)
  private String lastName;

  @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
  private Timestamp lastUpdate;

  @OneToMany(mappedBy = "actor")
  private List<FilmActor> filmActors = Lists.newArrayList();

  public Actor() {
    this.lastUpdate = new Timestamp(System.currentTimeMillis());
  }

  public Short getActorId() {
    return this.actorId;
  }

  public void setActorId(final Short actorId) {
    this.actorId = actorId;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(final Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public List<FilmActor> getFilmActors() {
    return this.filmActors;
  }

  public void setFilmActors(final List<FilmActor> filmActors) {
    this.filmActors = filmActors;
  }
}
