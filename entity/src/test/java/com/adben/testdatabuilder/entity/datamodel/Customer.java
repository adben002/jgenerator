package com.adben.testdatabuilder.entity.datamodel;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "customer", indexes = {
    @Index(columnList = "store_id", name = "idx_fk_store_id"),
    @Index(columnList = "address_id", name = "idx_fk_address_id"),
    @Index(columnList = "last_name", name = "idx_last_name")
})
public class Customer implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "customer_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
  private Short customerId;

  @Column(name = "store_id", nullable = false, columnDefinition = "TINYINT UNSIGNED")
  private Short storeId;

  @Column(name = "first_name", nullable = false, columnDefinition = "VARCHAR(45)", length = 45)
  private String firstName;

  @Column(name = "last_name", nullable = false, columnDefinition = "VARCHAR(45)", length = 45)
  private String lastName;

  @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(50)", length = 50)
  private String email;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "address_id", columnDefinition = "SMALLINT UNSIGNED", nullable = false)
  private Address address;

  @Column(name = "active", nullable = false, columnDefinition = "BOOLEAN")
  private Boolean active = Boolean.TRUE;

  @Column(name = "create_date", nullable = false, columnDefinition = "DATETIME")
  private Date createDate;

  @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
  private Timestamp lastUpdate;

  public Customer() {
    this.lastUpdate = new Timestamp(System.currentTimeMillis());
  }

  public Short getCustomerId() {
    return this.customerId;
  }

  public void setCustomerId(final Short customerId) {
    this.customerId = customerId;
  }

  public Short getStoreId() {
    return this.storeId;
  }

  public void setStoreId(final Short storeId) {
    this.storeId = storeId;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public Address getAddress() {
    return this.address;
  }

  public void setAddress(final Address address) {
    this.address = address;
  }

  public Boolean getActive() {
    return this.active;
  }

  public void setActive(final Boolean active) {
    this.active = active;
  }

  public Date getCreateDate() {
    return this.createDate;
  }

  public void setCreateDate(final Date createDate) {
    this.createDate = createDate;
  }

  public Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(final Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

}
