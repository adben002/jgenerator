package com.adben.testdatabuilder.core.model;

import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * Created by Adam.Bennett on 28/03/2017.
 */
public class TestBean {

    private final long CONST = 1L;

    private BaseEnum baseType;

    private byte b1;
    private Byte b2;

    private short c1;
    private Short c2;

    private int d1;
    private Integer d2;

    private long e1;
    private Long e2;

    private float f1;
    private Float f2;

    private double g1;
    private Double g2;

    private boolean h1;
    private Boolean h2;

    private char i1;
    private Character i2;

    private String j1;

    private BigDecimal k1;

    private TestSubBean subBean;

    private Date l1;
    private Timestamp m1;

    private List<Integer> n1;
    private Set<Integer> o1;

    private List<TestSubBean> subBeans;

    private List<List<List<List<List<List<List<List<List<Character>>>>>>>>> hugeList = Lists.newArrayList();

    public BaseEnum getBaseType() {
        return this.baseType;
    }

    public void setBaseType(final BaseEnum baseType) {
        this.baseType = baseType;
    }

    public byte getB1() {
        return this.b1;
    }

    public void setB1(final byte b1) {
        this.b1 = b1;
    }

    public Byte getB2() {
        return this.b2;
    }

    public void setB2(final Byte b2) {
        this.b2 = b2;
    }

    public short getC1() {
        return this.c1;
    }

    public void setC1(final short c1) {
        this.c1 = c1;
    }

    public Short getC2() {
        return this.c2;
    }

    public void setC2(final Short c2) {
        this.c2 = c2;
    }

    public int getD1() {
        return this.d1;
    }

    public void setD1(final int d1) {
        this.d1 = d1;
    }

    public Integer getD2() {
        return this.d2;
    }

    public void setD2(final Integer d2) {
        this.d2 = d2;
    }

    public long getE1() {
        return this.e1;
    }

    public void setE1(final long e1) {
        this.e1 = e1;
    }

    public Long getE2() {
        return this.e2;
    }

    public void setE2(final Long e2) {
        this.e2 = e2;
    }

    public float getF1() {
        return this.f1;
    }

    public void setF1(final float f1) {
        this.f1 = f1;
    }

    public Float getF2() {
        return this.f2;
    }

    public void setF2(final Float f2) {
        this.f2 = f2;
    }

    public double getG1() {
        return this.g1;
    }

    public void setG1(final double g1) {
        this.g1 = g1;
    }

    public Double getG2() {
        return this.g2;
    }

    public void setG2(final Double g2) {
        this.g2 = g2;
    }

    public boolean isH1() {
        return this.h1;
    }

    public void setH1(final boolean h1) {
        this.h1 = h1;
    }

    public Boolean getH2() {
        return this.h2;
    }

    public void setH2(final Boolean h2) {
        this.h2 = h2;
    }

    public char getI1() {
        return this.i1;
    }

    public void setI1(final char i1) {
        this.i1 = i1;
    }

    public Character getI2() {
        return this.i2;
    }

    public void setI2(final Character i2) {
        this.i2 = i2;
    }

    public String getJ1() {
        return this.j1;
    }

    public void setJ1(final String j1) {
        this.j1 = j1;
    }

    public TestSubBean getSubBean() {
        return this.subBean;
    }

    public void setSubBean(final TestSubBean subBean) {
        this.subBean = subBean;
    }

    public BigDecimal getK1() {
        return this.k1;
    }

    public void setK1(final BigDecimal k1) {
        this.k1 = k1;
    }

    public Date getL1() {
        return this.l1;
    }

    public void setL1(final Date l1) {
        this.l1 = l1;
    }

    public Timestamp getM1() {
        return this.m1;
    }

    public void setM1(final Timestamp m1) {
        this.m1 = m1;
    }

    public List<Integer> getN1() {
        return this.n1;
    }

    public void setN1(final List<Integer> n1) {
        this.n1 = n1;
    }

    public Set<Integer> getO1() {
        return this.o1;
    }

    public void setO1(final Set<Integer> o1) {
        this.o1 = o1;
    }

    public List<List<List<List<List<List<List<List<List<Character>>>>>>>>> getHugeList() {
        return this.hugeList;
    }

    public void setHugeList(final List<List<List<List<List<List<List<List<List<Character>>>>>>>>> hugeList) {
        this.hugeList = hugeList;
    }

    @Override
    public String toString() {
        return "TestBean{" + "CONST="
                + this.CONST + ", baseType="
                + this.baseType + ", b1="
                + this.b1 + ", b2="
                + this.b2 + ", c1="
                + this.c1 + ", c2="
                + this.c2 + ", d1="
                + this.d1 + ", d2="
                + this.d2 + ", e1="
                + this.e1 + ", e2="
                + this.e2 + ", f1="
                + this.f1 + ", f2="
                + this.f2 + ", g1="
                + this.g1 + ", g2="
                + this.g2 + ", h1="
                + this.h1 + ", h2="
                + this.h2 + ", i1="
                + this.i1 + ", i2="
                + this.i2 + ", j1='"
                + this.j1 + '\'' + ", k1="
                + this.k1 + ", subBean="
                + this.subBean + ", l1="
                + this.l1 + ", m1="
                + this.m1 + ", n1="
                + this.n1 + ", o1="
                + this.o1 + ", hugeList="
                + this.hugeList + '}';
    }
}
