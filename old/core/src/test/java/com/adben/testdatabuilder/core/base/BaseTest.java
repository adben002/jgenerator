package com.adben.testdatabuilder.core.base;

import static com.adben.testdatabuilder.core.cmds.core.CoreCmd.core;
import static com.adben.testdatabuilder.core.lookup.BuiltLookup.builtLookup;
import static com.adben.testdatabuilder.core.override.overriders.CoreOverrider.coreFields;
import static com.adben.testdatabuilder.core.override.overriderset.DefaultOverriderSet.ovrrdSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import com.adben.testdatabuilder.core.AbstractDataBuilderTest;
import com.adben.testdatabuilder.core.analyzers.generators.helper.AlphaNumericHelper;
import com.adben.testdatabuilder.core.model.TestBean;
import com.adben.testdatabuilder.core.model.TestSubBean;
import com.adben.testdatabuilder.core.override.overriders.Overrider;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class BaseTest extends AbstractDataBuilderTest {

  private static final int INDEX = 2;
  private static final int TEST_BEAN_COUNT = 50;
  private static final String ALL_CORE_J1 = "allCoreJ1";
  private static final String OTHER_CORE_J1 = "otherCoreJ1";

  @Before
  @Override
  public void setUp() {
    super.setUp();

    DATA_BUILDER
        .reset()
        .add(core(TestSubBean.class).overrides(ovrrdSet(TestSubBean.class)
            .all(coreFields(TestSubBean.class, new Overrider<TestSubBean>() {
              @Override
              public void setFields(final TestSubBean o) {
                o.setA(AlphaNumericHelper.toAlphaNumeric(0));
              }
            }))))
        .add(core(TestBean.class, TEST_BEAN_COUNT).overrides(ovrrdSet(TestBean.class)
            .all(coreFields(TestBean.class, new Overrider<TestBean>() {
              @Override
              public void setFields(final TestBean o) {
                o.setJ1(ALL_CORE_J1);
              }
            }))
            .idx(INDEX, coreFields(TestBean.class, new Overrider<TestBean>() {
              @Override
              public void setFields(final TestBean o) {
                o.setJ1(OTHER_CORE_J1);
                o.setSubBean(builtLookup(TestSubBean.class, 0).lookup());
              }
            }))))
        .build();
  }

  @Test
  public void testClzs() {
    assertThat(core(TestSubBean.class).getClzs(),
        is((Iterable<Class<?>>) Lists.<Class<?>>newArrayList(TestSubBean.class)));
  }

  @Test
  public void testCount() {
    assertThat(DATA_BUILDER.getAllByClz(TestSubBean.class), hasSize(1));
    assertThat(DATA_BUILDER.getAllByClz(TestBean.class), hasSize(TEST_BEAN_COUNT));
  }

  @Test
  public void testGetEntity() {
    assertThat(DATA_BUILDER.getAllByClz(TestSubBean.class).get(0),
        equalTo(DATA_BUILDER.getByClz(TestSubBean.class, 0)));
    assertThat(DATA_BUILDER.getAllByClz(TestBean.class).get(0),
        equalTo(DATA_BUILDER.getByClz(TestBean.class, 0)));
  }

  @Test
  public void testGetEntities() {
    assertThat(DATA_BUILDER.getEntities().asMap().keySet(), hasItem(TestBean.class));
    assertThat(DATA_BUILDER.getEntities().asMap().keySet(), hasItem(TestSubBean.class));
  }

  @Test
  public void testIntrospectors() {
    for (final TestBean gen : DATA_BUILDER.getAllByClz(TestBean.class)) {
      assertThat(gen.getBaseType(), is(notNullValue()));
      assertThat(gen.getB1(), is(notNullValue()));
      assertThat(gen.getB2(), is(notNullValue()));
      assertThat(gen.getC1(), is(notNullValue()));
      assertThat(gen.getC2(), is(notNullValue()));
      assertThat(gen.getD1(), is(notNullValue()));
      assertThat(gen.getD2(), is(notNullValue()));
      assertThat(gen.getE1(), is(notNullValue()));
      assertThat(gen.getE2(), is(notNullValue()));
      assertThat(gen.getF1(), is(notNullValue()));
      assertThat(gen.getF2(), is(notNullValue()));
      assertThat(gen.getG1(), is(notNullValue()));
      assertThat(gen.getG2(), is(notNullValue()));
      assertThat(gen.isH1(), is(notNullValue()));
      assertThat(gen.getH2(), is(notNullValue()));
      assertThat(gen.getI1(), is(notNullValue()));
      assertThat(gen.getI2(), is(notNullValue()));
      assertThat(gen.getJ1(), is(notNullValue()));
      assertThat(gen.getK1(), is(notNullValue()));
    }
  }

  @Test
  public void testOverrides() {
    final List<TestBean> allEntitiesByClz = DATA_BUILDER.getAllByClz(TestBean.class);

    for (int i = 0; i < allEntitiesByClz.size(); i++) {
      final TestBean gen = allEntitiesByClz.get(i);
      if (i == INDEX) {
        assertThat(gen.getJ1(), equalTo(OTHER_CORE_J1));
        assertThat(gen.getSubBean(), is(notNullValue()));
      } else {
        assertThat(gen.getJ1(), equalTo(ALL_CORE_J1));
        assertThat(gen.getSubBean(), is(nullValue()));
      }
    }
  }

}
