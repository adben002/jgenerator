package com.adben.testdatabuilder.core.analyzers.generators;

import com.google.common.base.Optional;
import java.lang.reflect.Type;

/**
 * Generate generatedValue interface.
 *
 * <p>
 *
 * Used for generating core data generatedValue classes.
 */
public interface Generator {

  /**
   * Take a type, which is a super interface for class and field types. This high level interface
   * represents a generatedValue that can be generated.
   *
   * <p>
   *
   * The return generatedValue is an {@link Optional} {@link GeneratedValue}, that can be used to
   * set the field or parameter generatedValue.
   *
   * @param type The type of object class to generate.
   * @return Optional GeneratedValue of the supplied type.
   */
  Optional<GeneratedValue> generateValue(final Type type);

}
