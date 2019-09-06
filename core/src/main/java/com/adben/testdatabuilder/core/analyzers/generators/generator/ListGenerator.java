package com.adben.testdatabuilder.core.analyzers.generators.generator;

import static com.adben.testdatabuilder.core.helper.ReflectionHelper.getClz;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.google.common.collect.Lists;
import java.lang.reflect.Type;
import java.util.List;
import org.slf4j.Logger;

/**
 * {@link com.adben.testdatabuilder.core.analyzers.generators.Generator Generator} for {@link
 * List}.
 *
 * <p>
 *
 * Generate a list of the supplied type. Recursing if required.
 *
 * <p>
 *
 * For instance {@code List<List<Integer>>} would generate something like: [[1]]
 */
@SuppressWarnings("ClassTooDeepInInheritanceTree")
public class ListGenerator extends AbstractIteratorGenerator<List<?>> {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  /**
   * Default constructor.
   */
  public ListGenerator() {
    super();
    LOGGER.trace("Default constructor");
  }

  /**
   * Applicable type is {@link List}.
   *
   * <p>
   *
   * {@inheritDoc}
   */
  @Override
  protected boolean isApplicable(final Type type) {
    return List.class.isAssignableFrom(getClz(type));
  }

  /**
   * Create the list with the supplied values.
   *
   * <p>
   *
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  protected List<?> createValues(final Type type, final Object... values) {
    return Lists.newArrayList(values);
  }

}
