package com.adben.testdatabuilder.core.cmds.common;

import static com.adben.testdatabuilder.core.override.overriders.EmptyOverrider.emptyOverrider;
import static com.adben.testdatabuilder.core.override.overriderset.DefaultOverriderSet.ovrrdSet;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.cmds.build.Builder;
import com.google.common.base.MoreObjects;
import java.util.Collections;
import org.slf4j.Logger;

/**
 * Abstraction of for the {@link com.adben.testdatabuilder.core.cmds.Cmd Cmd} that uses {@link
 * Builder}.
 */
public abstract class AbstractBuildCmd<T, C extends AbstractBuildCmd<T, C>> extends
    AbstractCmd<T, C> {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  /**
   * Builder to use when delegating the construction of the objects.
   */
  private Builder<T> builder = null;

  protected AbstractBuildCmd() {
    super();
    this.overrides(ovrrdSet(Object.class).all(emptyOverrider(Object.class)));
    LOGGER.debug("Default constructor");
  }

  public C builder(final Builder<T> builderParam) {
    LOGGER.debug("Set builderParam: {}", builderParam);
    this.builder = builderParam;
    return this.getThis();
  }

  protected Builder<T> getBuilder() {
    return this.builder;
  }

  @Override
  public Iterable<Class<?>> getClzs() {
    LOGGER.debug("Get builder: {}", this.builder);
    return Collections.<Class<?>>singletonList(this.builder.getClz());
  }

  @Override
  public String toString() {
    LOGGER.debug("toString()");
    return MoreObjects.toStringHelper(this)
        .add("builder", this.builder)
        .toString();
  }

}
