package com.adben.testdatabuilder.entity.datamodel;

import com.google.common.collect.Lists;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "language")
public class Language implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "language_id", nullable = false, columnDefinition = "TINYINT UNSIGNED")
  private Short languageId;

  @Column(name = "name", nullable = false, columnDefinition = "CHAR(20)", length = 20)
  private String name;

  @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
  private Timestamp lastUpdate;

  @OneToMany(mappedBy = "language")
  private List<Film> films = Lists.newArrayList();

  @OneToMany(mappedBy = "originalLanguage")
  private List<Film> filmsOriginal = Lists.newArrayList();

  public Language() {
    this.lastUpdate = new Timestamp(System.currentTimeMillis());
  }

  public Short getLanguageId() {
    return this.languageId;
  }

  public void setLanguageId(final Short languageId) {
    this.languageId = languageId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(final Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public List<Film> getFilms() {
    return this.films;
  }

  public void setFilms(final List<Film> films) {
    this.films = films;
  }

  public List<Film> getFilmsOriginal() {
    return this.filmsOriginal;
  }

  public void setFilmsOriginal(final List<Film> filmsOriginal) {
    this.filmsOriginal = filmsOriginal;
  }

}
