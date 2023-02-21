package com.adben.testdatabuilder.core.analyzers.generators.generator;

import static com.adben.testdatabuilder.core.helper.ReflectionHelper.getClz;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.google.common.base.MoreObjects;
import java.lang.reflect.Type;
import java.util.Objects;
import org.slf4j.Logger;

/**
 * {@link com.adben.testdatabuilder.core.analyzers.generators.Generator Generator} for {@link
 * Boolean}.
 *
 * <p>
 *
 * {@link BooleanGenerator#currValue} starts at false, then flip flops from there.
 */
public class BooleanGenerator extends AbstractGenerator<Boolean> {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private boolean currValue = false;

  /**
   * Default constructor.
   */
  public BooleanGenerator() {
    super();
    LOGGER.trace("Default constructor");
  }

  /**
   * Applicable type is {@link Boolean} or boolean.
   *
   * <p>
   *
   * {@inheritDoc}
   */
  @Override
  protected boolean isApplicable(final Type type) {
    return Boolean.class.isAssignableFrom(getClz(type)) ||
        Objects.equals(getClz(type), boolean.class);
  }

  /**
   * Get the next value for this generator, and flip the boolean value.
   *
   * <p>
   *
   * {@inheritDoc}
   */
  @Override
  protected Boolean getNextValue(final Type type) {
    try {
      return this.currValue;
    } finally {
      this.currValue = !this.currValue;
    }
  }

  @Override
  public String toString() {
    LOGGER.trace("toString");
    return MoreObjects.toStringHelper(this)
        .add("currValue", this.currValue)
        .toString();
  }

}
