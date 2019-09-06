package com.adben.testdatabuilder.entity;

import com.adben.testdatabuilder.core.cmds.build.Builder;
import com.google.common.collect.Lists;
import java.util.List;

/**
 *
 */
public class TestEntityBuilder implements Builder<TestEntity> {

  @Override
  public List<Object> make(final int count) {
    final List<Object> beans = Lists.newArrayList();

    for (int i = 0; i < count; i++) {
      final TestEntity testBean = new TestEntity();

      final TestEntityEnum[] values = TestEntityEnum.values();
      testBean.setBaseType(values[i % values.length]);
      testBean.setB((byte) i);
      testBean.setC((short) 2);
      testBean.setD(3);
      testBean.setE(4L);
      testBean.setF(1.1F);
      testBean.setG(1.2D);
      testBean.setH(true);
      testBean.setI((char) 5);
      testBean.setJ("6");

      beans.add(testBean);
    }

    return beans;
  }

  @Override
  public Class<TestEntity> getClz() {
    return TestEntity.class;
  }

  public enum TestEntityBuilderEnum {
    INSTANCE(new TestEntityBuilder());

    private final TestEntityBuilder b;

    TestEntityBuilderEnum(final TestEntityBuilder builder) {
      this.b = builder;
    }

    public TestEntityBuilder getB() {
      return this.b;
    }
  }

}
