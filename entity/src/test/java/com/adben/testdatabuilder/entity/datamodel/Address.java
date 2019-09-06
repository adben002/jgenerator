package com.adben.testdatabuilder.entity.datamodel;

import java.io.Serializable;
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
@Table(name = "address", indexes = @Index(columnList = "city_id", name = "idx_fk_city_id"))
public class Address implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "address_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
  private Short addressId;

  @Column(name = "address", nullable = false, columnDefinition = "VARCHAR(50)", length = 50)
  private String address;

  @Column(name = "address2", columnDefinition = "VARCHAR(50)", length = 50)
  private String address2;

  @Column(name = "district", nullable = false, columnDefinition = "VARCHAR(20)", length = 20)
  private String district;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "city_id", columnDefinition = "SMALLINT UNSIGNED", nullable = false)
  private City city;

  @Column(name = "postal_code", columnDefinition = "VARCHAR(10)", length = 10)
  private String postalCode;

  @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
  private Timestamp lastUpdate;

  public Address() {
    this.lastUpdate = new Timestamp(System.currentTimeMillis());
  }

  public Short getAddressId() {
    return this.addressId;
  }

  public void setAddressId(final Short addressId) {
    this.addressId = addressId;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(final String address) {
    this.address = address;
  }

  public String getAddress2() {
    return this.address2;
  }

  public void setAddress2(final String address2) {
    this.address2 = address2;
  }

  public String getDistrict() {
    return this.district;
  }

  public void setDistrict(final String district) {
    this.district = district;
  }

  public City getCity() {
    return this.city;
  }

  public void setCity(final City city) {
    this.city = city;
  }

  public String getPostalCode() {
    return this.postalCode;
  }

  public void setPostalCode(final String postalCode) {
    this.postalCode = postalCode;
  }

  public Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(final Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

}
