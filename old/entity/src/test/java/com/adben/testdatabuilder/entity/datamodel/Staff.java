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
@Table(name = "staff")
public class Staff implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "staff_id", nullable = false, columnDefinition = "TINYINT UNSIGNED")
  private Byte staffId;

  @Column(name = "first_name", nullable = false, columnDefinition = "VARCHAR(45)", length = 45)
  private String firstName;

  @Column(name = "last_name", nullable = false, columnDefinition = "VARCHAR(45)", length = 45)
  private String lastName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "address_id", columnDefinition = "SMALLINT UNSIGNED", nullable = false)
  private Address address;

  @Column(name = "picture", columnDefinition = "BLOB")
  private byte[] picture;

  @Column(name = "email", columnDefinition = "VARCHAR(50)", length = 50)
  private String email;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id", columnDefinition = "TINYINT UNSIGNED", nullable = false)
  private Store store;

  @Column(name = "active", columnDefinition = "BOOLEAN")
  private Boolean active = Boolean.TRUE;

  @Column(name = "username", nullable = false, columnDefinition = "VARCHAR(16)", length = 16)
  private String username;

  @Column(name = "password", columnDefinition = "VARCHAR(40) BINARY", length = 40)
  private String password;

  @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
  private Timestamp lastUpdate;

  public Staff() {
    this.lastUpdate = new Timestamp(System.currentTimeMillis());
  }

  public Byte getStaffId() {
    return this.staffId;
  }

  public void setStaffId(final Byte staffId) {
    this.staffId = staffId;
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

  public Address getAddress() {
    return this.address;
  }

  public void setAddress(final Address address) {
    this.address = address;
  }

  public byte[] getPicture() {
    return this.picture;
  }

  public void setPicture(final byte[] picture) {
    this.picture = picture;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public Store getStore() {
    return this.store;
  }

  public void setStore(final Store store) {
    this.store = store;
  }

  public Boolean getActive() {
    return this.active;
  }

  public void setActive(final Boolean active) {
    this.active = active;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(final Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

}
