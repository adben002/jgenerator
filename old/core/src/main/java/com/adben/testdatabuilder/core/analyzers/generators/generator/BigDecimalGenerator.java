package com.adben.testdatabuilder.core.analyzers.generators.generator;

import static com.adben.testdatabuilder.core.helper.ReflectionHelper.getClz;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.google.common.base.MoreObjects;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import org.slf4j.Logger;

/**
 * {@link com.adben.testdatabuilder.core.analyzers.generators.Generator Generator} for {@link
 * BigDecimal}.
 *
 * <p>
 *
 * {@link BigDecimalGenerator#currValue} starts at 0, then increments by 1 from there.
 */
public class BigDecimalGenerator extends AbstractGenerator<BigDecimal> {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private BigDecimal currValue = new BigDecimal("0");

  /**
   * Default constructor.
   */
  public BigDecimalGenerator() {
    super();
    LOGGER.trace("Default constructor");
  }

  /**
   * Applicable type is {@link BigDecimal}.
   *
   * <p>
   *
   * {@inheritDoc}
   */
  @Override
  protected boolean isApplicable(final Type type) {
    return BigDecimal.class.isAssignableFrom(getClz(type));
  }

  /**
   * Get the next value for this generator, and increment by 1.
   *
   * <p>
   *
   * {@inheritDoc}
   */
  @Override
  protected BigDecimal getNextValue(final Type type) {
    try {
      return this.currValue;
    } finally {
      this.currValue = this.currValue.add(new BigDecimal("1"));
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
