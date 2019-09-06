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
 * Long}.
 *
 * <p>
 *
 * {@link LongGenerator#value} starts at 0, then increments by 1 from there.
 */
public class LongGenerator extends AbstractGenerator<Long> {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private long value = 0;

  /**
   * Default constructor.
   */
  public LongGenerator() {
    super();
    LOGGER.trace("Default constructor");
  }

  /**
   * Applicable type is {@link Long} or long.
   *
   * <p>
   *
   * {@inheritDoc}
   */
  @Override
  protected boolean isApplicable(final Type type) {
    return Long.class.isAssignableFrom(getClz(type)) ||
        Objects.equals(getClz(type), long.class);
  }

  /**
   * Get the next value for this generator, and increment the value by 1.
   *
   * <p>
   *
   * {@inheritDoc}
   */
  @Override
  protected Long getNextValue(final Type type) {
    try {
      return this.value;
    } finally {
      this.value++;
    }
  }

  @Override
  public String toString() {
    LOGGER.trace("toString");
    return MoreObjects.toStringHelper(this)
        .add("generatedValue", this.value)
        .toString();
  }

}
