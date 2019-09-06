package com.adben.testdatabuilder.core.base;

import static com.adben.testdatabuilder.core.cmds.core.NestedCmd.nested;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import com.adben.testdatabuilder.core.AbstractDataBuilderTest;
import com.adben.testdatabuilder.core.model.TestBean;
import com.adben.testdatabuilder.core.model.TestSubBean;
import org.junit.Before;
import org.junit.Test;

public class NestedTest extends AbstractDataBuilderTest {

  private static final int TEST_BEAN_COUNT = 50;

  @Before
  @Override
  public void setUp() {
    super.setUp();

    DATA_BUILDER
        .add(nested(TestBean.class, TEST_BEAN_COUNT))
        .build();
  }

  @Test
  public void testCount() {
    assertThat(DATA_BUILDER.getAllByClz(TestSubBean.class), hasSize(1));
    assertThat(DATA_BUILDER.getAllByClz(TestBean.class), hasSize(TEST_BEAN_COUNT));
  }

}
