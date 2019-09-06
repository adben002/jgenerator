package com.adben.testdatabuilder.entity.datamodel;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "payment")
public class Payment implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "payment_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
  private Short paymentId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", columnDefinition = "SMALLINT UNSIGNED", nullable = false)
  private Customer customer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "staff_id", columnDefinition = "TINYINT UNSIGNED", nullable = false)
  private Staff staff;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rental_id", columnDefinition = "INT")
  private Rental rental;

  @Column(name = "amount", columnDefinition = "DECIMAL", precision = 5, scale = 2, nullable = false)
  private BigDecimal amount;

  @Column(name = "payment_date", columnDefinition = "DATETIME", nullable = false)
  private Date paymentDate;

  @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
  private Timestamp lastUpdate;

  public Payment() {
    this.lastUpdate = new Timestamp(System.currentTimeMillis());
  }

  public Short getPaymentId() {
    return this.paymentId;
  }

  public void setPaymentId(final Short paymentId) {
    this.paymentId = paymentId;
  }

  public Customer getCustomer() {
    return this.customer;
  }

  public void setCustomer(final Customer customer) {
    this.customer = customer;
  }

  public Staff getStaff() {
    return this.staff;
  }

  public void setStaff(final Staff staff) {
    this.staff = staff;
  }

  public Rental getRental() {
    return this.rental;
  }

  public void setRental(final Rental rental) {
    this.rental = rental;
  }

  public BigDecimal getAmount() {
    return this.amount;
  }

  public void setAmount(final BigDecimal amount) {
    this.amount = amount;
  }

  public Date getPaymentDate() {
    return this.paymentDate;
  }

  public void setPaymentDate(final Date paymentDate) {
    this.paymentDate = paymentDate;
  }

  public Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(final Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

}
