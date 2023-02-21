package com.adben.testdatabuilder.entity.cmds.entity;

import static com.adben.testdatabuilder.core.override.overriderset.DefaultOverriderSet.ovrrdSet;
import static com.adben.testdatabuilder.core.store.DataBuilderStore.store;
import static com.adben.testdatabuilder.entity.override.overriders.EntityOverrider.entityOverrider;
import static com.adben.testdatabuilder.entity.store.EntityDataBuilderStore.entityStore;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.cmds.Cmd;
import com.adben.testdatabuilder.core.cmds.common.AbstractClzCmd;
import com.adben.testdatabuilder.core.helper.ReflectionHelper;
import com.adben.testdatabuilder.core.override.overriders.Overrider;
import org.slf4j.Logger;

/**
 * Entity {@link Cmd Cmd}.
 *
 * <p>
 *
 * Example usage:
 * <pre>
 *   {@code
 *        createEntityDataBuilder(this.em)
 *            .add(entity(TestEntity.class, TEST_ENTITY_COUNT).overrides(ovrrdSet(TestEntity.class)
 *                .all(entityFields(TestEntity.class, new Overrider<TestEntity>() {
 *                  public void setFields(final TestEntity testEntity) {
 *                    testEntity.setE(OVERRIDE_E);
 *                  }
 *                }))))
 *            .add(entity(Actor.class, 3))
 *            .add(entity(Film.class, 2))
 *            .build();
 *   }
 * </pre>
 */
@SuppressWarnings({"ClassNamePrefixedWithPackageName", "ClassTooDeepInInheritanceTree"})
public class EntityCmd<T> extends AbstractClzCmd<T, EntityCmd<T>> {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  @SuppressWarnings("WeakerAccess")
  protected EntityCmd() {
    super();
    LOGGER.debug("Default protected constructor.");
  }

  /**
   * Named constructor for {@link EntityCmd#EntityCmd()}.
   *
   * @param <T> Type of the class this is creating a {@link Cmd} for.
   * @return Newly constructed default EntityCmd.
   */
  public static <T> EntityCmd<T> entity() {
    final EntityCmd<T> cmd = new EntityCmd<>();
    cmd.overrides(ovrrdSet(Object.class).all(entityOverrider(Object.class)));
    LOGGER.debug("New EntityCmd: {}", cmd);
    return cmd;
  }

  /**
   * @param <T> Type of the class this is creating a {@link Cmd} for.
   * @param clz Class for the EntityCmd.
   * @return Newly constructed EntityCmd with class.
   */
  public static <T> EntityCmd<T> entity(final Class<T> clz) {
    LOGGER.debug("New EntityCmd with clz: {}", clz);
    final EntityCmd<T> cmd = EntityCmd.<T>entity().clz(clz);
    LOGGER.debug("New EntityCmd with clz {}: {}", clz, cmd);
    return cmd;
  }

  /**
   * @param <T> Type of the class this is creating a {@link Cmd} for.
   * @param clz Class for the EntityCmd.
   * @param howMany How many of the given clz to create.
   * @return Newly constructed EntityCmd with class.
   */
  public static <T> EntityCmd<T> entity(final Class<T> clz, final int howMany) {
    LOGGER.debug("New CoreCmd with clz: {}, howMany: {}", clz, howMany);
    final EntityCmd<T> cmd = entity(clz).howMany(howMany);
    LOGGER.debug("New EntityCmd with clz {}, howMany: {}: {}", clz, howMany, cmd);
    return cmd;
  }

  /**
   * {@inheritDoc}
   *
   * <p>
   *
   * Implementation. Requires that the {@link AbstractClzCmd#clz} be set.
   */
  @SuppressWarnings("FeatureEnvy")
  @Override
  public void build() {
    LOGGER.debug("Build entity with - Clz: {}. HowMany: {}. DefaultOverriderSet: {}",
        this.getClz(),
        this.getHowMany(),
        this.getOverriderSet());

    for (int i = 0; i < this.getHowMany(); i++) {
      final T obj = ReflectionHelper.makeInstance(this.getClz());
      final Overrider<? super T> overrider = this.getOverriderSet().get(i);
      LOGGER.debug("Use overrider: {}", overrider);
      overrider.setFields(obj);
      entityStore().em.persist(obj);
      LOGGER.debug("Add object: {}", obj);
      store().builtObjects.put(this.getClz(), obj);
      entityStore().em.flush();
      entityStore().em.refresh(obj);
    }
  }

}
