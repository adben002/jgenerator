package com.adben.testdatabuilder.core.analyzers.introspectors.introspector;

import static com.adben.testdatabuilder.core.helper.ReflectionHelper.setFieldVal;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.generators.GeneratedValue;
import com.adben.testdatabuilder.core.analyzers.generators.Generator;
import com.adben.testdatabuilder.core.analyzers.introspectors.Introspector;
import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import org.slf4j.Logger;

/**
 * {@link Introspector} for generating values, the {@link Generator} does not need knowledge of the
 * field and object and so can be decoupled.
 *
 * <p>
 *
 * For the supplied generator see if it produces a {@link GeneratedValue}, if so then we use that to
 * set the field with.
 */
@SuppressWarnings("ClassWithoutNoArgConstructor")
public class GeneratedIntrospector implements Introspector {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private final Generator generator;

  public GeneratedIntrospector(final Generator generatorParam) {
    super();
    this.generator = generatorParam;
    LOGGER.debug("Constructor. generatorParam: {}", generatorParam);
  }

  /**
   * For the supplied generator see if it produces a {@link GeneratedValue}, if so then we use that
   * to set the field with.
   *
   * {@inheritDoc}
   */
  @SuppressWarnings("FeatureEnvy")
  @Override
  public boolean introspect(final Field field, final Object obj) throws IllegalAccessException {
    final Type fieldType = field.getGenericType();
    final Optional<GeneratedValue> generatedValue = this.generator.generateValue(fieldType);
    if (generatedValue.isPresent()) {
      LOGGER.debug("Generated value for field type {}: {}", fieldType, generatedValue);
      final GeneratedValue genValue = generatedValue.get();
      if (!genValue.isSkipSetting()) {
        LOGGER.debug("Set field {} on {} to {}.", field, obj, genValue.getValue());
        setFieldVal(obj, field, genValue.getValue());
      }
      return true;
    }

    LOGGER.debug("No generated value for field type {}: {}", fieldType, generatedValue);
    return false;
  }

  @Override
  public String toString() {
    LOGGER.debug("toString");
    return MoreObjects.toStringHelper(this)
        .add("generator", this.generator)
        .toString();
  }

}
