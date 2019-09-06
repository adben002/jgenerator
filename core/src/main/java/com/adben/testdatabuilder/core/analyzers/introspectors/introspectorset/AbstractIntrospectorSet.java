package com.adben.testdatabuilder.core.analyzers.introspectors.introspectorset;

import static com.adben.testdatabuilder.core.analyzers.introspectors.helper.IntrospectorSetHelper.introspectField;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.introspectors.Introspector;
import com.google.common.base.MoreObjects;
import java.lang.reflect.Field;
import org.slf4j.Logger;

public abstract class AbstractIntrospectorSet implements Introspector {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private final Iterable<Introspector> introspectors;

  protected AbstractIntrospectorSet(final Iterable<Introspector> introspectors) {
    super();
    this.introspectors = introspectors;
    LOGGER.debug("Constructor. introspectors: {}", introspectors);
  }

  @Override
  public boolean introspect(final Field field, final Object obj) throws IllegalAccessException {
    LOGGER.debug("Introspect set on field: {} of object: {}. With introspectors: {}", field, obj,
        this.introspectors);
    final boolean introspection = introspectField(this.introspectors, field, obj);
    LOGGER.debug("Returns: {}", introspection);
    return introspection;
  }

  @Override
  public String toString() {
    LOGGER.debug("toString");
    return MoreObjects.toStringHelper(this)
        .add("introspectors", this.introspectors)
        .toString();
  }

}
