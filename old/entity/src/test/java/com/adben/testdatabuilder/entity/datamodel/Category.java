package com.adben.testdatabuilder.entity.datamodel;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "category")
public class Category implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "category_id", nullable = false, columnDefinition = "TINYINT UNSIGNED")
  private Byte categoryId;

  @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(25)", length = 25)
  private String name;

  @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
  private Timestamp lastUpdate;

  public Category() {
    this.lastUpdate = new Timestamp(System.currentTimeMillis());
  }

  public Byte getCategoryId() {
    return this.categoryId;
  }

  public void setCategoryId(final Byte categoryId) {
    this.categoryId = categoryId;
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

}
