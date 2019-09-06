package com.adben.testdatabuilder.core.analyzers.introspectors.helper;

import static com.adben.testdatabuilder.core.helper.ReflectionHelper.getAllFields;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.introspectors.Introspector;
import java.lang.reflect.Field;
import java.util.Collection;
import org.slf4j.Logger;

/**
 * Helper for creating and setting up objects from the {@link Introspector}.
 *
 * <p>
 *
 * This class deals with creating a new instance of a given class, then using the introspector set
 * values on the new object.
 */
public final class IntrospectorHelper {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private IntrospectorHelper() {
    super();
    LOGGER.debug("Default constructor");
  }

  /**
   * Build a object of the given {@link Class} type. Set field values using the {@link
   * Introspector}, which will make an "educated guess" for the field generatedValue.
   *
   * @param introspector {@link Introspector}, which is used to set the field values. nested
   * values.
   * @param <T> Type of object to build.
   * @param newInstance New instance.
   * @param fieldsToSkip Fields to not set.
   * @return Built object of the given class, with fields set per the {@link Introspector} and
   * @throws IllegalAccessException When setting a field.
   */
  public static <T> T performIntrospection(final T newInstance,
      final Introspector introspector,
      final Collection<String> fieldsToSkip)
      throws IllegalAccessException {

    LOGGER.debug("Build {}, with introspector: {}. Skip fields: {}",
        newInstance,
        introspector,
        fieldsToSkip);

    for (final Field field : getAllFields(newInstance.getClass())) {
      if (!fieldsToSkip.contains(field.getName())) {
        LOGGER.debug("Introspect field {}", field);
        introspector.introspect(field, newInstance);
      }
    }

    LOGGER.debug("Introspected instance: {}", newInstance);

    return newInstance;
  }

}
