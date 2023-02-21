package com.adben.testdatabuilder.entity;

import static com.adben.testdatabuilder.entity.TestEntityBuilder.TestEntityBuilderEnum.INSTANCE;
import static com.adben.testdatabuilder.entity.cmds.build.EntityBuildCmd.emBuildCmd;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Test;

public class EntityBuilderTest extends AbstractEntityTest {

  @Test
  public void a() {
    assertThat(this.builder.getEntities().keys(), is(empty()));

    this.builder
        .add(emBuildCmd(INSTANCE.getB()).howMany(20))
        .build();

    assertThat(this.em.contains(this.builder.getByClz(TestEntity.class, 0)), is(true));
    assertThat(this.em.find(TestEntity.class,
        this.builder.getByClz(TestEntity.class, 0).getB()), is(notNullValue()));
    assertThat(this.builder.getAllByClz(TestEntity.class), hasSize(20));

    assertThat(this.helper.count(TestEntity.class), equalTo(20L));
  }


}
