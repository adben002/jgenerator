package com.adben.testdatabuilder.entity.datamodel;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "film_actor", indexes = @Index(columnList = "film_id", name = "idx_fk_film_id"))
public class FilmActor implements Serializable {

  @EmbeddedId
  private FilmActorPK id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "actor_id", columnDefinition = "SMALLINT UNSIGNED", nullable = false, insertable = false, updatable = false)
  private Actor actor;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "film_id", columnDefinition = "SMALLINT UNSIGNED", nullable = false, insertable = false, updatable = false)
  private Film film;

  @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
  private Timestamp lastUpdate;

  public FilmActor() {
    this.lastUpdate = new Timestamp(System.currentTimeMillis());
  }

  public FilmActorPK getId() {
    return this.id;
  }

  public void setId(final FilmActorPK id) {
    this.id = id;
  }

  public Actor getActor() {
    return this.actor;
  }

  public void setActor(final Actor actor) {
    this.actor = actor;
  }

  public Film getFilm() {
    return this.film;
  }

  public void setFilm(final Film film) {
    this.film = film;
  }

  public Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(final Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

}
