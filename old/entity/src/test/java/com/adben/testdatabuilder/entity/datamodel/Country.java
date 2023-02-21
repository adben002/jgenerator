package com.adben.testdatabuilder.entity.datamodel;

import com.google.common.collect.Lists;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "country")
public class Country {

  @Id
  @GeneratedValue
  @Column(name = "country_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
  private Short countryId;

  @Column(name = "country", nullable = false, columnDefinition = "VARCHAR(50)", length = 50)
  private String country;

  @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
  private Timestamp lastUpdate;

  @OneToMany(mappedBy = "country")
  private List<City> cities = Lists.newArrayList();

  public Country() {
    this.lastUpdate = new Timestamp(System.currentTimeMillis());
  }

  public Short getCountryId() {
    return this.countryId;
  }

  public void setCountryId(final Short countryId) {
    this.countryId = countryId;
  }

  public String getCountry() {
    return this.country;
  }

  public void setCountry(final String country) {
    this.country = country;
  }

  public Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(final Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public List<City> getCities() {
    return this.cities;
  }

  public void setCities(final List<City> cities) {
    this.cities = cities;
  }

}
