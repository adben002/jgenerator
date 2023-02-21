package com.adben.testdatabuilder.entity.analyzers.introspectors.introspector;

import static com.adben.testdatabuilder.core.helper.ReflectionHelper.getFirstGeneric;
import static com.adben.testdatabuilder.core.store.DataBuilderStore.isGoingToMakeOneOfThese;
import static com.adben.testdatabuilder.core.store.DataBuilderStore.store;
import static com.adben.testdatabuilder.entity.analyzers.introspectors.helper.IterableEntityIntrospectorHelper.numGenericsToCreate;
import static com.adben.testdatabuilder.entity.cmds.entity.EntityCmd.entity;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.introspectors.Introspector;
import com.adben.testdatabuilder.core.cmds.Cmd;
import com.adben.testdatabuilder.core.cmds.common.AbstractCmd;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import java.lang.reflect.Field;
import javax.persistence.OneToMany;
import org.slf4j.Logger;

/**
 * {@link Introspector} for {@link OneToMany} {@link Iterable}s.
 *
 * <p>
 *
 * If there is not a {@link Cmd} to later make one of the {@link Iterable} types then
 * createDataBuilder them.
 */
public class IterableEntityIntrospector implements Introspector {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private final Function<Class<?>, AbstractCmd<?, ?>> cmdGenerator;

  @SuppressWarnings("SyntheticAccessorCall")
  public IterableEntityIntrospector() {
    //noinspection AnonymousInnerClassMayBeStatic,AnonymousInnerClass
    this(new Function<Class<?>, AbstractCmd<?, ?>>() {
      @Override
      public AbstractCmd<?, ?> apply(final Class<?> input) {
        LOGGER.debug("Default cmd");
        return entity(input).howMany(1);
      }
    });
  }

  @SuppressWarnings("WeakerAccess")
  public IterableEntityIntrospector(final Function<Class<?>, AbstractCmd<?, ?>> cmdGeneratorParam) {
    super();
    this.cmdGenerator = cmdGeneratorParam;
  }

  @Override
  public boolean introspect(final Field field, final Object obj) {
    if (Iterable.class.isAssignableFrom(field.getType())
        && field.isAnnotationPresent(OneToMany.class)) {

      LOGGER.debug("Handle nested creation of entities. field: {}", field);

      final Class<?> generic = getFirstGeneric(field);

      LOGGER.debug("Generic to possibly create: {}", generic);

      if (!isGoingToMakeOneOfThese(generic)) {

        //noinspection ConstantConditions
        final Cmd cmd = this.cmdGenerator.apply(generic)
            .howMany(numGenericsToCreate(generic));

        LOGGER.debug("Cmd to queue: {}", cmd);

        store().commands.add(cmd);
      }
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    LOGGER.debug("toString");
    return MoreObjects.toStringHelper(this)
        .add("cmdGenerator", this.cmdGenerator)
        .toString();
  }

}
