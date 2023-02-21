package com.adben.testdatabuilder.entity;

import static com.adben.testdatabuilder.core.override.overriderset.DefaultOverriderSet.ovrrdSet;
import static com.adben.testdatabuilder.entity.cmds.entity.EntityCmd.entity;
import static com.adben.testdatabuilder.entity.override.overriders.EntityOverrider.entityFields;
import static org.assertj.core.api.Assertions.assertThat;

import com.adben.testdatabuilder.core.override.overriders.Overrider;
import com.adben.testdatabuilder.entity.datamodel.Actor;
import com.adben.testdatabuilder.entity.datamodel.Film;
import com.adben.testdatabuilder.entity.datamodel.FilmActor;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.junit.Before;
import org.junit.Test;

public class EntityTest extends AbstractEntityTest {

  private static final int TEST_ENTITY_COUNT = 50;
  private static final long OVERRIDE_E = 1L;

  @Override
  @Before
  public void setup() {
    super.setup();

    this.builder
        .add(entity(TestEntity.class, TEST_ENTITY_COUNT).overrides(ovrrdSet(TestEntity.class)
            .all(entityFields(TestEntity.class, new Overrider<TestEntity>() {
              @Override
              public void setFields(final TestEntity testEntity) {
                testEntity.setE(OVERRIDE_E);
              }
            }))))
        .add(entity(Actor.class, 3))
        .add(entity(Film.class, 2))
        .build();
  }

  @Test
  public void testFieldSetting() {
    this.em.clear();

    final CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
    final CriteriaQuery<TestEntity> query = criteriaBuilder.createQuery(TestEntity.class);
    final Root<TestEntity> root = query.from(TestEntity.class);

    query.select(root);

    final List<TestEntity> testEntities = this.em.createQuery(query).getResultList();
    assertThat(testEntities).hasSize(TEST_ENTITY_COUNT);
    for (final TestEntity testEntity : testEntities) {
      assertThat(testEntity.getBaseType()).isNotNull();
      assertThat(testEntity.getB()).isNotNull();
      assertThat(testEntity.getC()).isNotNull();
      assertThat(testEntity.getD()).isNotNull();
      assertThat(testEntity.getE()).isEqualTo(OVERRIDE_E);
      assertThat(testEntity.getF()).isNotNull();
      assertThat(testEntity.getG()).isNotNull();
      assertThat(testEntity.getH()).isNotNull();
      assertThat(testEntity.getI()).isNotNull();
      assertThat(testEntity.getJ()).isNotNull();
    }
  }

  @Test
  public void testNested() {
    assertThat(this.builder.getAllByClz(FilmActor.class)).hasSize(6);

    final List<FilmActor> filmActors = this.builder.getByClz(Film.class, 0).getFilmActors();
    assertThat(filmActors).hasSize(3);
    for (final FilmActor filmActor : filmActors) {
      assertThat(filmActor.getActor()).isNotNull();
    }
  }

}
