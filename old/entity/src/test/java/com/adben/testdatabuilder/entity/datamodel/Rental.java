package com.adben.testdatabuilder.entity.datamodel;

import java.io.Serializable;
import java.sql.Date;
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
@Table(name = "rental")
public class Rental implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "rental_id", nullable = false, columnDefinition = "INT")
  private Integer rentalId;

  @Column(name = "rental_date", columnDefinition = "DATETIME", nullable = false)
  private Date rentalDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "inventory_id", columnDefinition = "MEDIUMINT UNSIGNED", nullable = false)
  private Inventory inventory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", columnDefinition = "SMALLINT UNSIGNED", nullable = false)
  private Customer customer;

  @Column(name = "return_date", columnDefinition = "DATETIME")
  private Date returnDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "staff_id", columnDefinition = "TINYINT UNSIGNED", nullable = false)
  private Staff staff;

  @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
  private Timestamp lastUpdate;

  public Rental() {
    this.lastUpdate = new Timestamp(System.currentTimeMillis());
  }

  public Integer getRentalId() {
    return this.rentalId;
  }

  public void setRentalId(final Integer rentalId) {
    this.rentalId = rentalId;
  }

  public Date getRentalDate() {
    return this.rentalDate;
  }

  public void setRentalDate(final Date rentalDate) {
    this.rentalDate = rentalDate;
  }

  public Inventory getInventory() {
    return this.inventory;
  }

  public void setInventory(final Inventory inventory) {
    this.inventory = inventory;
  }

  public Customer getCustomer() {
    return this.customer;
  }

  public void setCustomer(final Customer customer) {
    this.customer = customer;
  }

  public Date getReturnDate() {
    return this.returnDate;
  }

  public void setReturnDate(final Date returnDate) {
    this.returnDate = returnDate;
  }

  public Staff getStaff() {
    return this.staff;
  }

  public void setStaff(final Staff staff) {
    this.staff = staff;
  }

  public Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(final Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
