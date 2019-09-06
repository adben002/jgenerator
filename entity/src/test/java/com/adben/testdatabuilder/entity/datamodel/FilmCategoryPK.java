package com.adben.testdatabuilder.entity.datamodel;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 */
@Embeddable
public class FilmCategoryPK implements Serializable {

  @Column(name = "film_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
  private Short filmId;

  @Column(name = "category_id", nullable = false, columnDefinition = "TINYINT UNSIGNED")
  private Byte categoryId;

  public Short getFilmId() {
    return this.filmId;
  }

  public void setFilmId(final Short filmId) {
    this.filmId = filmId;
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
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final FilmCategoryPK that = (FilmCategoryPK) o;

    if (this.filmId != null ? !this.filmId.equals(that.filmId) : that.filmId != null) {
      return false;
    }
    return this.categoryId != null ? this.categoryId.equals(that.categoryId)
        : that.categoryId == null;
  }

  @Override
  public int hashCode() {
    int result = this.filmId != null ? this.filmId.hashCode() : 0;
    result = 31 * result + (this.categoryId != null ? this.categoryId.hashCode() : 0);
    return result;
  }
}
