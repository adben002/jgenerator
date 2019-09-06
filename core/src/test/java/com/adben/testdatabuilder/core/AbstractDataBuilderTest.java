package com.adben.testdatabuilder.core;

import static com.adben.testdatabuilder.core.DataBuilder.createDataBuilder;

import org.junit.Before;

public abstract class AbstractDataBuilderTest {

  protected static final DataBuilder DATA_BUILDER = createDataBuilder();

  @Before
  public void setUp() {
    DATA_BUILDER.reset();
  }

}
