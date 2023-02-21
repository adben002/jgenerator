package com.adben.testdatabuilder.core.cmds.core;

import static com.adben.testdatabuilder.core.override.overriders.NestedOverrider.nestedOverrider;
import static com.adben.testdatabuilder.core.override.overriderset.DefaultOverriderSet.ovrrdSet;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.override.overriders.NestedOverrider;
import org.slf4j.Logger;

/**
 * Nested {@link com.adben.testdatabuilder.core.cmds.Cmd Cmd}. This builds the links inside the
 * supplied object class. For instance if we link to a SubBean from a Bean and use a NestedCmd on
 * Bean.class then the created Bean creates a new SubBean.
 *
 * <p>
 *
 * Example usage:
 * <pre>
 *   {@code
 *        createDataBuilder()
 *          .add(nested(TestBean.class, TEST_BEAN_COUNT))
 *          .build();
 *   }
 * </pre>
 */
public final class NestedCmd {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private NestedCmd() {
    super();
  }

  /**
   * NestedCmd, that used by default: {@link NestedOverrider#nestedOverrider(Class)}.
   *
   * @param clz Class to build for.
   * @param howMany How many to build.
   * @param <T> Type of the class.
   * @return New {@link CoreCmd} with {@link NestedOverrider#nestedOverrider(Class)}.
   */
  public static <T> CoreCmd<T> nested(final Class<T> clz, final int howMany) {
    LOGGER.debug("New NestedCmd with clz: {}, howMany: {}", clz, howMany);
    return nested(clz).howMany(howMany);
  }

  /**
   * NestedCmd, that used by default: {@link NestedOverrider#nestedOverrider(Class)}.
   *
   * @param clz Class to build for.
   * @param <T> Type of the class.
   * @return New {@link CoreCmd} with {@link NestedOverrider#nestedOverrider(Class)}.
   */
  public static <T> CoreCmd<T> nested(final Class<T> clz) {
    LOGGER.debug("New NestedCmd with clz: {}", clz);
    return NestedCmd.<T>nested().clz(clz);
  }

  /**
   * NestedCmd, that used by default: {@link NestedOverrider#nestedOverrider(Class)}.
   *
   * @param <T> Type of the class.
   * @return New {@link CoreCmd} with {@link NestedOverrider#nestedOverrider(Class)}.
   */
  public static <T> CoreCmd<T> nested() {
    LOGGER.debug("New NestedCmd");
    return CoreCmd.<T>core().overrides(ovrrdSet(Object.class).all(nestedOverrider(Object.class)));
  }

}
