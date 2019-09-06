package com.adben.testdatabuilder.core.analyzers.introspectors.helper;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.google.common.base.Optional;
import java.util.ArrayDeque;
import java.util.Deque;
import org.slf4j.Logger;

/**
 * Class to store {@link ThreadLocal} data associated with
 *
 * {@link com.adben.testdatabuilder.core.analyzers.introspectors.Introspector Introspector}s.
 *
 * <p>
 *
 * This utilises {@link ThreadLocal} so that for the thread this can be accessed from anywhere in
 * the code.
 */
public final class IntrospectorStore {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private IntrospectorStore() {
    super();
  }

  /**
   * A stack of values that are currently being built, this is useful when the introspector builds
   * another cmd.
   */
  @SuppressWarnings("AnonymousInnerClass")
  public static final ThreadLocal<Deque<Object>> NESTED = new ThreadLocal<Deque<Object>>() {
    @SuppressWarnings("CollectionWithoutInitialCapacity")
    @Override
    protected Deque<Object> initialValue() {
      return new ArrayDeque<>();
    }
  };

  /**
   * For the given class find a potentially nested object of the supplied type.
   *
   * @param clz Class to find an object of the same type in the nested stack.
   * @return Optional of the found object with the given type. {@link Optional#absent()} is none
   * found.
   */
  @SuppressWarnings("WeakerAccess")
  public static Optional<Object> getNested(final Class<?> clz) {
    final Deque<Object> objects = NESTED.get();

    LOGGER.debug("Get nested object of type {}, from: {}", clz, objects);

    for (final Object nestedObject : objects) {
      if (clz.equals(nestedObject.getClass())) {
        LOGGER.debug("Nested object found: {}", nestedObject);
        return Optional.of(nestedObject);
      }
    }

    LOGGER.debug("Could not find {} in {}", clz, objects);

    return Optional.absent();
  }

}
