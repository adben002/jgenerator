package com.adben.testdatabuilder.entity.datamodel;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "film_category")
public class FilmCategory implements Serializable {

  @EmbeddedId
  private FilmCategoryPK id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "film_id", columnDefinition = "SMALLINT UNSIGNED", nullable = false, insertable = false, updatable = false)
  private Film film;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", columnDefinition = "TINYINT UNSIGNED", nullable = false, insertable = false, updatable = false)
  private Category category;

  @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
  private Timestamp lastUpdate;

  public FilmCategory() {
    this.lastUpdate = new Timestamp(System.currentTimeMillis());
  }

  public FilmCategoryPK getId() {
    return this.id;
  }

  public void setId(final FilmCategoryPK id) {
    this.id = id;
  }

  public Film getFilm() {
    return this.film;
  }

  public void setFilm(final Film film) {
    this.film = film;
  }

  public Category getCategory() {
    return this.category;
  }

  public void setCategory(final Category category) {
    this.category = category;
  }

  public Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(final Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

}
