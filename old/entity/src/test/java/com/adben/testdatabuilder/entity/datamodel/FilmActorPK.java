package com.adben.testdatabuilder.entity.datamodel;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 */
@Embeddable
public class FilmActorPK implements Serializable {

  @Column(name = "film_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
  private Short filmId;
  @Column(name = "actor_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
  private Short actorId;

  @Column(name = "category_id", nullable = false, columnDefinition = "TINYINT UNSIGNED")
  private Byte categoryId;

  public Short getFilmId() {
    return this.filmId;
  }

  public void setFilmId(final Short filmId) {
    this.filmId = filmId;
  }

  public Short getActorId() {
    return this.actorId;
  }

  public void setActorId(final Short actorId) {
    this.actorId = actorId;
  }

  public Byte getCategoryId() {
    return this.categoryId;
  }

  public void setCategoryId(final Byte categoryId) {
    this.categoryId = categoryId;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    final FilmActorPK that = (FilmActorPK) o;
    return Objects.equals(this.filmId, that.filmId) &&
        Objects.equals(this.actorId, that.actorId) &&
        Objects.equals(this.categoryId, that.categoryId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.filmId, this.actorId, this.categoryId);
  }

}
