package com.adben.testdatabuilder.core.model;

/**
 *
 */
public class TestSubBean {

  private String a;

  private TestBean testBean;

  public String getA() {
    return this.a;
  }

  public void setA(final String a) {
    this.a = a;
  }

  public TestBean getTestBean() {
    return this.testBean;
  }

  public void setTestBean(final TestBean testBean) {
    this.testBean = testBean;
  }

  @Override
  public String toString() {
    return "TestSubBean{" +
        "a='" + this.a + '\'' +
        ", testBean=" + (this.testBean == null ? "" : this.testBean.getBaseType()) +
        '}';
  }
}
