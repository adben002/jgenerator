package com.adben.testdatabuilder.core.analyzers.introspectors;

import java.lang.reflect.Field;

/**
 * Introspect a field and set values on objects.
 *
 * <p>
 *
 * This is a generator that requires knowledge of the object that contains the field.
 */
public interface Introspector {

  /**
   * For the given {@link Field} and {@link Object} set the field generatedValue on the object
   * making an "educated guess".
   *
   * @param field Field which will be set.
   * @param obj Object to set the field generatedValue on.
   * @return True if the introspector applied a generatedValue to the field on the object. False
   * otherwise.
   * @throws IllegalAccessException When setting a field generatedValue on an object.
   */
  @SuppressWarnings("BooleanMethodNameMustStartWithQuestion")
  boolean introspect(Field field, Object obj) throws IllegalAccessException;

}
