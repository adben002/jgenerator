package com.adben.testdatabuilder.entity.datamodel;

import com.google.common.collect.Lists;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "film", indexes = @Index(columnList = "title", name = "idx_title"))
public class Film implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "film_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
  private Short filmId;

  @Column(name = "title", nullable = false, columnDefinition = "VARCHAR(255)")
  private String title;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "release_year", columnDefinition = "SMALLINT UNSIGNED")
  private Short releaseYear;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "language_id", columnDefinition = "TINYINT UNSIGNED", nullable = false)
  private Language language;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "original_language_id", columnDefinition = "TINYINT UNSIGNED")
  private Language originalLanguage;

  @Column(name = "rental_duration", columnDefinition = "TINYINT UNSIGNED", nullable = false)
  private Byte rentalDuration = 3;

  @Column(name = "rental_rate", columnDefinition = "DECIMAL", precision = 4, scale = 2, nullable = false)
  private BigDecimal rentalRate = new BigDecimal("4.99");

  @Column(name = "length", columnDefinition = "SMALLINT UNSIGNED")
  private Short length;

  @Column(name = "replacement_cost", columnDefinition = "DECIMAL", precision = 5, scale = 2, nullable = false)
  private BigDecimal replacementCost = new BigDecimal("19.99");

  @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
  private Timestamp lastUpdate;

  @OneToMany(mappedBy = "film")
  private List<FilmActor> filmActors = Lists.newArrayList();

  public Film() {
    this.lastUpdate = new Timestamp(System.currentTimeMillis());
  }

  public Short getFilmId() {
    return this.filmId;
  }

  public void setFilmId(final Short filmId) {
    this.filmId = filmId;
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

  public Language getLanguage() {
    return this.language;
  }

  public void setLanguage(final Language language) {
    this.language = language;
  }

  public Language getOriginalLanguage() {
    return this.originalLanguage;
  }

  public void setOriginalLanguage(final Language originalLanguage) {
    this.originalLanguage = originalLanguage;
  }

  public Byte getRentalDuration() {
    return this.rentalDuration;
  }

  public void setRentalDuration(final Byte rentalDuration) {
    this.rentalDuration = rentalDuration;
  }

  public BigDecimal getRentalRate() {
    return this.rentalRate;
  }

  public void setRentalRate(final BigDecimal rentalRate) {
    this.rentalRate = rentalRate;
  }

  public Short getLength() {
    return this.length;
  }

  public void setLength(final Short length) {
    this.length = length;
  }

  public BigDecimal getReplacementCost() {
    return this.replacementCost;
  }

  public void setReplacementCost(final BigDecimal replacementCost) {
    this.replacementCost = replacementCost;
  }

  public Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(final Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public Short getReleaseYear() {
    return this.releaseYear;
  }

  public void setReleaseYear(final Short releaseYear) {
    this.releaseYear = releaseYear;
  }

  public List<FilmActor> getFilmActors() {
    return this.filmActors;
  }

  public void setFilmActors(final List<FilmActor> filmActors) {
    this.filmActors = filmActors;
  }

}
