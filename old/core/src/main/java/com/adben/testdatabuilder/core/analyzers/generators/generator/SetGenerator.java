package com.adben.testdatabuilder.core.analyzers.generators.generator;

import static com.adben.testdatabuilder.core.helper.ReflectionHelper.getClz;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.google.common.collect.Sets;
import java.lang.reflect.Type;
import java.util.Set;
import org.slf4j.Logger;

/**
 * {@link com.adben.testdatabuilder.core.analyzers.generators.Generator Generator} for {@link Set}.
 *
 * <p>
 *
 * Generate a list of the supplied type. Recursing if required.
 *
 * <p>
 *
 * For instance {@code Set<Set<Integer>>} would generate something like: [[1]]
 */
@SuppressWarnings("ClassTooDeepInInheritanceTree")
public class SetGenerator extends AbstractIteratorGenerator<Set<?>> {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  /**
   * Default constructor.
   */
  public SetGenerator() {
    super();
    LOGGER.trace("Default constructor");
  }

  /**
   * Applicable type is {@link Set}.
   *
   * <p>
   *
   * {@inheritDoc}
   */
  @Override
  protected boolean isApplicable(final Type type) {
    return Set.class.isAssignableFrom(getClz(type));
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
  protected Set<?> createValues(final Type type, final Object... values) {
    return Sets.newHashSet(values);
  }

}
