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
 * Byte}.
 *
 * <p>
 *
 * {@link ByteGenerator#currValue} starts at 0, then increments from there.
 */
public class ByteGenerator extends AbstractGenerator<Byte> {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private byte currValue = 0;

  /**
   * Default constructor.
   */
  public ByteGenerator() {
    super();
    LOGGER.trace("Default constructor");
  }

  /**
   * Applicable type is {@link Byte} or byte.
   *
   * <p>
   *
   * {@inheritDoc}
   */
  @Override
  protected boolean isApplicable(final Type type) {
    return Byte.class.isAssignableFrom(getClz(type)) ||
        Objects.equals(getClz(type), byte.class);
  }

  /**
   * Get the next value for this generator, and increment the value by 1.
   *
   * <p>
   *
   * {@inheritDoc}
   */
  @Override
  protected Byte getNextValue(final Type type) {
    try {
      return this.currValue;
    } finally {
      this.currValue++;
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
