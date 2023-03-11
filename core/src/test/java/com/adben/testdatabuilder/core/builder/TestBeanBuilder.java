package com.adben.testdatabuilder.core.builder;

import com.adben.testdatabuilder.core.cmds.build.Builder;
import com.adben.testdatabuilder.core.model.TestBean;
import com.adben.testdatabuilder.core.model.TestSubBean;
import com.google.common.collect.Lists;
import java.util.List;

/**
 *
 */
public enum TestBeanBuilder implements Builder<TestBean> {
    TEST_BEAN_BUILDER_INSTANCE;

    @Override
    public List<Object> make(final int count) {
        final List<Object> beans = Lists.newArrayList();

        for (int i = 0; i < count; i++) {
            final TestBean testBean = new TestBean();

            testBean.setB1((byte) i);
            final TestSubBean subBean = new TestSubBean();
            subBean.setTestBean(testBean);
            testBean.setSubBean(subBean);

            beans.add(testBean);
        }

        return beans;
    }

    @Override
    public Class<TestBean> getClz() {
        return TestBean.class;
    }
}
