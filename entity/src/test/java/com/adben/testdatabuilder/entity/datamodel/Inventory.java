package com.adben.testdatabuilder.entity.datamodel;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "inventory")
public class Inventory implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "inventory_id", nullable = false, columnDefinition = "MEDIUMINT UNSIGNED")
  private Integer inventoryId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "film_id", columnDefinition = "SMALLINT UNSIGNED", nullable = false)
  private Film film;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id", columnDefinition = "TINYINT UNSIGNED", nullable = false)
  private Store store;

  @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
  private Timestamp lastUpdate;

  public Inventory() {
    this.lastUpdate = new Timestamp(System.currentTimeMillis());
  }

  public Integer getInventoryId() {
    return this.inventoryId;
  }

  public void setInventoryId(final Integer inventoryId) {
    this.inventoryId = inventoryId;
  }

  public Film getFilm() {
    return this.film;
  }

  public void setFilm(final Film film) {
    this.film = film;
  }

  public Store getStore() {
    return this.store;
  }

  public void setStore(final Store store) {
    this.store = store;
  }

  public Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(final Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
