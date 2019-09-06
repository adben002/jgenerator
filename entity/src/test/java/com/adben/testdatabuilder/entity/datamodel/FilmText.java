package com.adben.testdatabuilder.entity.datamodel;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "film_text")
public class FilmText implements Serializable {

  @Id
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "film_id", columnDefinition = "SMALLINT UNSIGNED", nullable = false)
  private Film film;

  @Column(name = "title", nullable = false, columnDefinition = "VARCHAR(255)")
  private String title;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  public Film getFilm() {
    return this.film;
  }

  public void setFilm(final Film film) {
    this.film = film;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

}
