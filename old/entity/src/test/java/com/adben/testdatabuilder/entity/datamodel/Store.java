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
@Table(name = "store")
public class Store implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "store_id", nullable = false, columnDefinition = "TINYINT UNSIGNED")
  private Integer storeId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "manager_staff_id", columnDefinition = "TINYINT UNSIGNED", nullable = false)
  private Staff manager;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "address_id", columnDefinition = "SMALLINT UNSIGNED", nullable = false)
  private Address address;

  @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
  private Timestamp lastUpdate;

  public Store() {
    this.lastUpdate = new Timestamp(System.currentTimeMillis());
  }

  public Integer getStoreId() {
    return this.storeId;
  }

  public void setStoreId(final Integer storeId) {
    this.storeId = storeId;
  }

  public Staff getManager() {
    return this.manager;
  }

  public void setManager(final Staff manager) {
    this.manager = manager;
  }

  public Address getAddress() {
    return this.address;
  }

  public void setAddress(final Address address) {
    this.address = address;
  }

  public Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(final Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

}
