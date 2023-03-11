package com.adben.testdatabuilder.core.builder;

import static com.adben.testdatabuilder.core.builder.TestBeanBuilder.TEST_BEAN_BUILDER_INSTANCE;
import static com.adben.testdatabuilder.core.cmds.build.BuildCmd.buildCmd;
import static com.adben.testdatabuilder.core.override.overriders.EmptyOverrider.emptyFields;
import static com.adben.testdatabuilder.core.override.overriderset.DefaultOverriderSet.ovrrdSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import com.adben.testdatabuilder.core.AbstractDataBuilderTest;
import com.adben.testdatabuilder.core.model.TestBean;
import com.adben.testdatabuilder.core.override.overriders.Overrider;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class BuilderTest extends AbstractDataBuilderTest {

    private static final String J1_VALUE = "asdsad";
    private static final String SUBBEAN_A = "sadsadd";

    @Before
    @Override
    public void setUp() {
        super.setUp();

        DATA_BUILDER
                .add(buildCmd(TEST_BEAN_BUILDER_INSTANCE, 50)
                        .overrides(ovrrdSet(TestBean.class).all(emptyFields(TestBean.class, new Overrider<TestBean>() {
                            @Override
                            public void setFields(final TestBean testBean) {
                                testBean.setJ1(J1_VALUE);
                                testBean.getSubBean().setA(SUBBEAN_A);
                            }
                        }))))
                .build();
    }

    @Test
    public void testCount() {
        assertThat(DATA_BUILDER.getAllByClz(TestBean.class), hasSize(50));
    }

    @Test
    public void testFields() {

        final List<TestBean> allEntitiesByClz = DATA_BUILDER.getAllByClz(TestBean.class);

        for (int i = 0; i < allEntitiesByClz.size(); i++) {
            final TestBean gen = allEntitiesByClz.get(i);
            assertThat(gen.getBaseType(), is(nullValue()));
            assertThat(gen.getB1(), is((byte) i));
            assertThat(gen.getB2(), is(nullValue()));
            assertThat(gen.getC1(), is(notNullValue()));
            assertThat(gen.getC2(), is(nullValue()));
            assertThat(gen.getD1(), is(notNullValue()));
            assertThat(gen.getD2(), is(nullValue()));
            assertThat(gen.getE1(), is(notNullValue()));
            assertThat(gen.getE2(), is(nullValue()));
            assertThat(gen.getF1(), is(notNullValue()));
            assertThat(gen.getF2(), is(nullValue()));
            assertThat(gen.getG1(), is(notNullValue()));
            assertThat(gen.getG2(), is(nullValue()));
            assertThat(gen.isH1(), is(notNullValue()));
            assertThat(gen.getH2(), is(nullValue()));
            assertThat(gen.getI1(), is(notNullValue()));
            assertThat(gen.getI2(), is(nullValue()));
            assertThat(gen.getJ1(), is(J1_VALUE));
            assertThat(gen.getK1(), is(nullValue()));
            assertThat(gen.getSubBean(), is(notNullValue()));
            assertThat(gen.getSubBean().getA(), is(SUBBEAN_A));
        }
    }

    @Test
    public void testClzs() {
        assertThat(buildCmd(TEST_BEAN_BUILDER_INSTANCE).getClzs(), is((Iterable<Class<?>>)
                Lists.<Class<?>>newArrayList(TestBean.class)));
    }
}
