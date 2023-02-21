package com.adben.testdatabuilder.entity;

import com.adben.testdatabuilder.core.DataBuilder;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
    classes = {
        JpaTestConfiguration.class,
        EntityTestCfg.class
    })
@Transactional
@Rollback
public abstract class AbstractEntityTest {

  @PersistenceContext
  protected EntityManager em;

  @Inject
  protected DataBuilder builder;

  @Inject
  protected EntityTestHelper helper;

  @Before
  public void setup() {
    this.builder.reset();
  }

}
