package com.adben.testdatabuilder.core.analyzers.generators.generator;

import static com.adben.testdatabuilder.core.helper.ReflectionHelper.getClz;
import static java.lang.System.currentTimeMillis;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.lang.reflect.Type;
import java.sql.Date;
import org.slf4j.Logger;

/**
 * {@link com.adben.testdatabuilder.core.analyzers.generators.Generator Generator} for {@link
 * Date}.
 *
 * <p>
 *
 * Gets the date from the return generatedValue of {@link System#currentTimeMillis()}.
 */
public class DateSqlGenerator extends AbstractGenerator<Date> {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  /**
   * Default constructor.
   */
  public DateSqlGenerator() {
    super();
    LOGGER.trace("Default constructor");
  }

  /**
   * Applicable type is {@link Date}.
   *
   * <p>
   *
   * {@inheritDoc}
   */
  @Override
  protected boolean isApplicable(final Type type) {
    return Date.class.isAssignableFrom(getClz(type));
  }

  /**
   * Get the next value for this generator, which is the current time millis.
   *
   * <p>
   *
   * {@inheritDoc}
   */
  @Override
  protected Date getNextValue(final Type type) {
    return new Date(currentTimeMillis());
  }

}
