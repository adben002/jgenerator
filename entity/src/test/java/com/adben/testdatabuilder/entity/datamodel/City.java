package com.adben.testdatabuilder.entity.datamodel;

import com.google.common.collect.Lists;
import java.io.Serializable;
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
@Table(name = "city", indexes = @Index(columnList = "country_id", name = "idx_fk_country_id"))
public class City implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "city_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
  private Short cityId;

  @Column(name = "city", nullable = false, columnDefinition = "VARCHAR(50)", length = 50)
  private String city;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "country_id", columnDefinition = "SMALLINT UNSIGNED", nullable = false)
  private Country country;

  @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
  private Timestamp lastUpdate;

  @OneToMany(mappedBy = "city")
  private List<Address> addresses = Lists.newArrayList();

  public City() {
    this.lastUpdate = new Timestamp(System.currentTimeMillis());
  }

  public Short getCityId() {
    return this.cityId;
  }

  public void setCityId(final Short cityId) {
    this.cityId = cityId;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(final String city) {
    this.city = city;
  }

  public Country getCountry() {
    return this.country;
  }

  public void setCountry(final Country country) {
    this.country = country;
  }

  public Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(final Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public List<Address> getAddresses() {
    return this.addresses;
  }

  public void setAddresses(final List<Address> addresses) {
    this.addresses = addresses;
  }
}
