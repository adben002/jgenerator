package com.adben.testdatabuilder.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "test_entity")
public class TestEntity implements Serializable {

  @Enumerated(EnumType.STRING)
  private TestEntityEnum baseType;

  @Id
  @Column(name = "b", nullable = false)
  private Byte b;

  @Column(name = "c", nullable = false)
  private Short c;

  @Column(name = "d", nullable = false)
  private Integer d;

  @Column(name = "e", nullable = false)
  private Long e;

  @Column(name = "f", nullable = false)
  private Float f;

  @Column(name = "g", nullable = false)
  private Double g;

  @Column(name = "h", nullable = false)
  private Boolean h;

  @Column(name = "i", nullable = false)
  private Character i;

  @Column(name = "j", nullable = false, length = 50, columnDefinition = "CHAR(50)")
  private String j;

  public TestEntity() {
  }

  public TestEntityEnum getBaseType() {
    return this.baseType;
  }

  public void setBaseType(final TestEntityEnum baseType) {
    this.baseType = baseType;
  }

  public Byte getB() {
    return this.b;
  }

  public void setB(final Byte b) {
    this.b = b;
  }

  public Short getC() {
    return this.c;
  }

  public void setC(final Short c) {
    this.c = c;
  }

  public Integer getD() {
    return this.d;
  }

  public void setD(final Integer d) {
    this.d = d;
  }

  public Long getE() {
    return this.e;
  }

  public void setE(final Long e) {
    this.e = e;
  }

  public Float getF() {
    return this.f;
  }

  public void setF(final Float f) {
    this.f = f;
  }

  public Double getG() {
    return this.g;
  }

  public void setG(final Double g) {
    this.g = g;
  }

  public Boolean getH() {
    return this.h;
  }

  public void setH(final Boolean h) {
    this.h = h;
  }

  public Character getI() {
    return this.i;
  }

  public void setI(final Character i) {
    this.i = i;
  }

  public String getJ() {
    return this.j;
  }

  public void setJ(final String j) {
    this.j = j;
  }

  @Override
  public String toString() {
    return "TestEntity{" +
        "baseType=" + this.baseType +
        ", b=" + this.b +
        ", c=" + this.c +
        ", d=" + this.d +
        ", e=" + this.e +
        ", f=" + this.f +
        ", g=" + this.g +
        ", h=" + this.h +
        ", i=" + this.i +
        ", j='" + this.j + '\'' +
        '}';
  }

}
