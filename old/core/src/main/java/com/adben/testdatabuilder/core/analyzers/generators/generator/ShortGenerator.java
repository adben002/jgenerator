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
 * Short}.
 *
 * <p>
 *
 * {@link ShortGenerator#value} starts at 0, then increments by 1 from there.
 */
public class ShortGenerator extends AbstractGenerator<Short> {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private short value = 0;

  /**
   * Default constructor.
   */
  public ShortGenerator() {
    super();
    LOGGER.trace("Default constructor");
  }

  /**
   * Applicable type is {@link Short} or short.
   *
   * <p>
   *
   * {@inheritDoc}
   */
  @Override
  protected boolean isApplicable(final Type type) {
    return Short.class.isAssignableFrom(getClz(type)) ||
        Objects.equals(getClz(type), short.class);
  }

  /**
   * Get the next value for this generator, and increment the value by 1.
   *
   * <p>
   *
   * {@inheritDoc}
   */
  @Override
  protected Short getNextValue(final Type type) {
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
