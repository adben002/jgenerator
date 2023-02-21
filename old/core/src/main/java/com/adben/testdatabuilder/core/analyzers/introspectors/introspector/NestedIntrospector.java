package com.adben.testdatabuilder.core.analyzers.introspectors.introspector;

import static com.adben.testdatabuilder.core.analyzers.introspectors.helper.NestedIntrospectorHelper.findNestedClasses;
import static com.adben.testdatabuilder.core.cmds.core.CoreCmd.core;
import static com.adben.testdatabuilder.core.helper.ReflectionHelper.setFieldVal;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.introspectors.Introspector;
import com.adben.testdatabuilder.core.analyzers.introspectors.helper.IntrospectorStore;
import com.adben.testdatabuilder.core.cmds.Cmd;
import com.adben.testdatabuilder.core.cmds.core.CoreCmd;
import com.adben.testdatabuilder.core.store.DataBuilderStore;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import java.lang.reflect.Field;
import java.util.List;
import org.slf4j.Logger;

/**
 * {@link Introspector} for building/fetching other objects.
 *
 * <p>
 *
 * If the object class already exists in the {@link DataBuilderStore#store()} then fetch it from
 * there, else createDataBuilder it using calling {@link CoreCmd#build()} with the specified class.
 * There is a guard around nested building here, whereby the {@link CoreCmd#build()} could
 * potentially ask to build the original object, so to prevent a stack overflow avoid nested
 * building using {@link IntrospectorStore#NESTED}.
 */
public class NestedIntrospector implements Introspector {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private int counter = 0;
  private final Function<Class<?>, Cmd> cmdGenerator;

  @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass"})
  public NestedIntrospector() {
    this(new Function<Class<?>, Cmd>() {
      @SuppressWarnings("SyntheticAccessorCall")
      @Override
      public Cmd apply(final Class<?> input) {
        LOGGER.debug("Default cmd");
        return core(input).howMany(1);
      }
    });
    LOGGER.debug("Default constructor");
  }

  @SuppressWarnings("WeakerAccess")
  public NestedIntrospector(final Function<Class<?>, Cmd> cmdGeneratorParam) {
    super();
    this.cmdGenerator = cmdGeneratorParam;
    LOGGER.debug("Default constructor");
  }

  @Override
  public boolean introspect(final Field field, final Object obj) throws IllegalAccessException {

    LOGGER.debug("Introspect {} field: {}", obj, field);

    final List<Object> foundClasses = findNestedClasses(field.getType(),
        obj,
        this.cmdGenerator.apply(field.getType()));
    LOGGER.debug("Found classes {}", foundClasses);

    final Object foundClass = foundClasses.get(this.counter % foundClasses.size());
    LOGGER.debug("Class to set on field: {}", foundClass);

    setFieldVal(obj, field, foundClass);

    this.counter++;
    return true;
  }

  @Override
  public String toString() {
    LOGGER.debug("toString");
    return MoreObjects.toStringHelper(this)
        .add("counter", this.counter)
        .add("cmdGenerator", this.cmdGenerator)
        .toString();
  }

}
