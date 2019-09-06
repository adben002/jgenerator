package com.adben.testdatabuilder.entity.override.overriders;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.override.overriders.AbstractFieldOverrider;
import com.adben.testdatabuilder.core.override.overriders.Overrider;
import com.adben.testdatabuilder.entity.analyzers.introspectors.introspectorset.EntityIntrospectorSet;
import org.slf4j.Logger;

/**
 * EntityOverrider, which uses {@link EntityIntrospectorSet}.
 *
 * <p>
 *
 * Example usage:
 * <pre>
 *   {@code
 *          createEntityDataBuilder(this.em)
 *              .reset()
 *              .add(entity(TestEntity.class, 10).overrides(ovrrdSet(TestEntity.class)
 *                  .all(entityFields(TestEntity.class, new Overrider<TestEntity>() {
 *                    public void setFields(final TestEntity testEntity) {
 *                      testEntity.setE("test");
 *                    }
 *                  }))))
 *              .add(entity(Actor.class, 3))
 *              .add(entity(Film.class, 2))
 *              .build();
 *   }
 * </pre>
 *
 * @param <T> Type of the {@link Overrider}.
 */
@SuppressWarnings("ClassWithoutNoArgConstructor")
public final class EntityOverrider<T> extends AbstractFieldOverrider<T, EntityOverrider<T>> {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private EntityOverrider(final Class<T> t) {
    super(new EntityIntrospectorSet());
    LOGGER.debug("Constructor. Class: {}", t);
  }

  /**
   * Create a EntityOverrider object, most of the logic is performed in {@link
   * AbstractFieldOverrider}. This is essential a wrapper on {@link EntityIntrospectorSet} and
   * {@link AbstractFieldOverrider}.
   *
   * @param clz Class that the overrider is working for. This is only for type enforcement.
   * @param <T> type of the clz.
   * @return New EntityOverrider.
   */
  public static <T> EntityOverrider<T> entityOverrider(final Class<T> clz) {
    LOGGER.debug("Named constructor. Class: {}", clz);
    return new EntityOverrider<>(clz);
  }

  /**
   * Create a EntityOverrider object, most of the logic is performed in {@link
   * AbstractFieldOverrider}. This is essential a wrapper on {@link EntityIntrospectorSet} and
   * {@link AbstractFieldOverrider}.
   *
   * @param t Class that the overrider is working for. This is only for type enforcement.
   * @param callbackOverrider Overrider as a callback.
   * @param <T> type of the clz.
   * @return New EntityOverrider.
   */
  public static <T> EntityOverrider<T> entityFields(final Class<T> t,
      final Overrider<T> callbackOverrider) {

    LOGGER.debug("Named constructor. Class: {}. callbackOverrider: {}", t, callbackOverrider);
    return entityOverrider(t).callbackOverrider(callbackOverrider);
  }

  @Override
  protected EntityOverrider<T> getThis() {
    return this;
  }

}
