package com.adben.testdatabuilder.core.cmds.build;

import static com.adben.testdatabuilder.core.store.DataBuilderStore.store;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.cmds.Cmd;
import com.adben.testdatabuilder.core.cmds.common.AbstractBuildCmd;
import com.adben.testdatabuilder.core.override.overriders.Overrider;
import com.google.common.base.Preconditions;
import java.util.List;
import org.slf4j.Logger;

/**
 * {@link Cmd} for dealing with {@link Builder}.
 *
 * <p>
 *
 * Example usage:
 *
 * <pre>
 * {@code
 *    createDataBuilder()
 *        .add(buildCmd(TEST_BEAN_BUILDER_INSTANCE, 50).overrides(ovrrdSet(TestBean.class)
 *            .all(emptyFields(TestBean.class, new Overrider<TestBean>() {
 *              public void setFields(final TestBean testBean) {
 *                testBean.setJ1(J1_VALUE);
 *                testBean.getSubBean().setA(SUBBEAN_A);
 *              }
 *            }))))
 *        .build();
 * }
 * </pre>
 */
@SuppressWarnings({"ClassNamePrefixedWithPackageName", "ClassTooDeepInInheritanceTree"})
public class BuildCmd<T> extends AbstractBuildCmd<T, BuildCmd<T>> {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  /**
   * Create a default constructor for BuildCmd.
   *
   * <p>
   *
   * <b>REMEMBER:</b> You will need to set the builder on it at some point. Probably using {@link
   * BuildCmd#builder(Builder)}.
   */
  protected BuildCmd() {
    super();
    LOGGER.debug("Default constructor");
  }

  /**
   * Named constructor for {@link BuildCmd#BuildCmd()}.
   *
   * @param <T> Type of the class this is creating a {@link Cmd} for.
   * @return Newly constructed default BuildCmd.
   */
  public static <T> BuildCmd<T> buildCmd() {
    LOGGER.debug("Named default constructor for BuildCmd");
    return new BuildCmd<>();
  }

  /**
   * @param builder The {@link Builder} we are making the {@link Cmd} for.
   * @param <T> Type of the class this is creating a {@link Cmd} for.
   * @return Newly constructed BuildCmd with {@link Builder}.
   */
  public static <T> BuildCmd<T> buildCmd(final Builder<T> builder) {
    LOGGER.debug("Named default constructor for BuildCmd. builder: {}", builder);
    return BuildCmd.<T>buildCmd().builder(builder);
  }

  /**
   * @param builder The {@link Builder} we are making the {@link Cmd} for.
   * @param howMany How many of the given builder to create.
   * @param <T> Type of the class this is creating a {@link Cmd} for.
   * @return Newly constructed BuildCmd with {@link Builder}.
   */
  public static <T> BuildCmd<T> buildCmd(final Builder<T> builder, final int howMany) {
    LOGGER.debug("Named default constructor for BuildCmd. builder: {}, howMany: {}",
        builder,
        howMany);
    return buildCmd(builder).howMany(howMany);
  }

  @SuppressWarnings("unchecked")
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
      LOGGER.debug("Add: {}", madeObject);
      store().builtObjects.put(madeObject.getClass(), madeObject);
    }
  }

}
