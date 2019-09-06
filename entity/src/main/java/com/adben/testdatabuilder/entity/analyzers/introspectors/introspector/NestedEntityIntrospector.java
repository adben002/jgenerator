package com.adben.testdatabuilder.entity.analyzers.introspectors.introspector;

import static com.adben.testdatabuilder.core.analyzers.introspectors.helper.NestedIntrospectorHelper.findNestedClasses;
import static com.adben.testdatabuilder.core.helper.ReflectionHelper.setFieldVal;
import static com.adben.testdatabuilder.entity.cmds.entity.EntityCmd.entity;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.introspectors.Introspector;
import com.adben.testdatabuilder.core.analyzers.introspectors.helper.IntrospectorStore;
import com.adben.testdatabuilder.core.cmds.Cmd;
import com.adben.testdatabuilder.core.store.DataBuilderStore;
import com.adben.testdatabuilder.entity.cmds.entity.EntityCmd;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import java.lang.reflect.Field;
import java.util.List;
import javax.persistence.JoinColumn;
import org.slf4j.Logger;

/**
 * {@link Introspector} for building/fetching other entities.
 *
 * <p>
 *
 * If the object class already exists in the {@link DataBuilderStore#store()} then fetch it from
 * there, else create it using calling {@link EntityCmd#build()} with the specified class. There is
 * a guard around nested building here, whereby the {@link EntityCmd#build()} could potentially ask
 * to build the original object, so to prevent a stack overflow avoid nested building using {@link
 * IntrospectorStore#NESTED}.
 */
public class NestedEntityIntrospector implements Introspector {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private int counter = 0;
  private final Function<Class<?>, Cmd> cmdGenerator;

  @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass"})
  public NestedEntityIntrospector() {
    this(new Function<Class<?>, Cmd>() {
      @SuppressWarnings("SyntheticAccessorCall")
      @Override
      public Cmd apply(final Class<?> input) {
        LOGGER.debug("Default cmd");
        return entity(input).howMany(1);
      }
    });
    LOGGER.debug("Default constructor");
  }

  @SuppressWarnings("WeakerAccess")
  public NestedEntityIntrospector(final Function<Class<?>, Cmd> cmdGeneratorParam) {
    super();
    this.cmdGenerator = cmdGeneratorParam;
    LOGGER.debug("Constructor. cmdGeneratorParam: {}", cmdGeneratorParam);
  }

  @Override
  public boolean introspect(final Field field, final Object obj) throws IllegalAccessException {
    if (field.isAnnotationPresent(JoinColumn.class)
        && field.getAnnotation(JoinColumn.class).insertable()) {

      LOGGER.debug("Introspect nested entity on {} field: {}", obj, field);

      final List<Object> foundClasses = findNestedClasses(field.getType(),
          obj,
          this.cmdGenerator.apply(field.getType()));

      LOGGER.debug("Found possible classes to use for instrospection: {}", foundClasses);

      final Object value = foundClasses.get(this.counter % foundClasses.size());
      LOGGER.debug("Value to set for field: {}", value);

      setFieldVal(obj, field, value);

      this.counter++;
      return true;
    }

    return false;
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
