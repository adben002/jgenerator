package com.adben.testdatabuilder.entity.cmds.build;

import static com.adben.testdatabuilder.core.store.DataBuilderStore.store;
import static com.adben.testdatabuilder.entity.store.EntityDataBuilderStore.entityStore;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.cmds.Cmd;
import com.adben.testdatabuilder.core.cmds.build.BuildCmd;
import com.adben.testdatabuilder.core.cmds.build.Builder;
import com.adben.testdatabuilder.core.cmds.common.AbstractBuildCmd;
import com.adben.testdatabuilder.core.override.overriders.Overrider;
import com.google.common.base.Preconditions;
import java.util.List;
import org.slf4j.Logger;

/**
 * {@link Cmd} for dealing with {@link Builder} for entities.
 *
 * <p>
 *
 * Example usage:
 *
 * <pre>
 * {@code
 *    createEntityDataBuilder(this.em)
 *        .add(emBuildCmd(new TestEntityBuilder()).howMany(20))
 *        .build();
 * }
 * </pre>
 */
@SuppressWarnings("ClassTooDeepInInheritanceTree")
public class EntityBuildCmd<T> extends AbstractBuildCmd<T, EntityBuildCmd<T>> {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  /**
   * Create a default constructor for {@link BuildCmd}.
   *
   * <p>
   *
   * <b>REMEMBER:</b> You will need to set the builder on it at some point. Probably using {@link
   * BuildCmd#builder(Builder)}.
   */
  @SuppressWarnings("WeakerAccess")
  protected EntityBuildCmd() {
    super();
    LOGGER.debug("Default constructor");
  }

  /**
   * Named constructor for {@link BuildCmd#BuildCmd()}.
   *
   * @param <T> Type of the class this is creating a {@link Cmd} for.
   * @return Newly constructed default {@link BuildCmd}.
   */
  public static <T> EntityBuildCmd<T> emBuildCmd() {
    LOGGER.debug("Named default constructor for EntityBuildCmd");
    return new EntityBuildCmd<>();
  }

  /**
   * @param builder The {@link Builder} we are making the {@link Cmd} for.
   * @param <T> Type of the class this is creating a {@link Cmd} for.
   * @return Newly constructed {@link BuildCmd} with {@link Builder}.
   */
  public static <T> EntityBuildCmd<T> emBuildCmd(final Builder<T> builder) {
    LOGGER.debug("Named default constructor for EntityBuildCmd. builder: {}", builder);
    return EntityBuildCmd.<T>emBuildCmd().builder(builder);
  }

  /**
   * @param builder The {@link Builder} we are making the {@link Cmd} for.
   * @param howMany How many of the given builder to create.
   * @param <T> Type of the class this is creating a {@link Cmd} for.
   * @return Newly constructed EntityBuildCmd with {@link Builder}.
   */
  public static <T> EntityBuildCmd<T> emBuildCmd(final Builder<T> builder, final int howMany) {
    LOGGER.debug("Named default constructor for EntityBuildCmd. builder: {}, howMany: {}",
        builder,
        howMany);
    return emBuildCmd(builder).howMany(howMany);
  }

  @SuppressWarnings({"unchecked", "FeatureEnvy"})
  @Override
  public void build() {
    LOGGER.debug("build {}", this);
    Preconditions.checkNotNull(this.getBuilder(), "Builder needs to be set on: " + this.getClass());
    final List<Object> objects = this.getBuilder().make(this.getHowMany());
    LOGGER.debug("Created objects {}", objects);
    for (int i = 0; i < objects.size(); i++) {
      final Object madeObject = objects.get(i);
      if (this.getBuilder().getClz().isAssignableFrom(madeObject.getClass())) {
        final Overrider<? super T> overrider = this.getOverriderSet().get(i);
        LOGGER.debug("Set field overrides for {}, with: {}",
            madeObject,
            overrider);
        overrider.setFields((T) madeObject);
      }
      entityStore().em.persist(madeObject);
      entityStore().em.flush();
      LOGGER.debug("Add: {}", madeObject);
      store().builtObjects.put(madeObject.getClass(), madeObject);
      entityStore().em.refresh(madeObject);
    }
  }

}
