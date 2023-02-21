package com.adben.testdatabuilder.entity.analyzers.introspectors.introspector;

import static com.adben.testdatabuilder.core.helper.ReflectionHelper.setFieldVal;
import static com.adben.testdatabuilder.entity.analyzers.introspectors.helper.EmbeddableIntrospectorHelper.makeEmbeddableObject;
import static com.adben.testdatabuilder.entity.cmds.entity.EntityCmd.entity;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.introspectors.Introspector;
import com.adben.testdatabuilder.core.cmds.Cmd;
import com.adben.testdatabuilder.entity.analyzers.introspectors.introspectorset.EntityIntrospectorSet;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Supplier;
import java.lang.reflect.Field;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import org.slf4j.Logger;

/**
 * {@link Introspector} for {@link EmbeddedId}.
 *
 * <p>
 *
 * Try and do clever setting of PK columns that refer to {@link JoinColumn}s by doing a cartesian
 * product of all possible combinations and grabbing the Id of each.
 */
public class EmbeddableIntrospector implements Introspector {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private final Supplier<Introspector> introspectorGenerator;
  private final Function<Class<?>, Cmd> cmdGenerator;

  /**
   * Default constructor.
   */
  @SuppressWarnings("SyntheticAccessorCall")
  public EmbeddableIntrospector() {
    //noinspection AnonymousInnerClassMayBeStatic,AnonymousInnerClass
    this(new Supplier<Introspector>() {
           @Override
           public Introspector get() {
             LOGGER.debug("Default introspector");
             return new EntityIntrospectorSet();
           }
         },
        new Function<Class<?>, Cmd>() {
          @Override
          public Cmd apply(final Class<?> input) {
            LOGGER.debug("Default cmd");
            return entity(input).howMany(1);
          }
        });
    LOGGER.debug("Default constructor");
  }

  /**
   * Constructor for setting introspectorGenerator and cmdGenerator. This allows injection of
   * different values into the instrospector.
   *
   * @param introspectorGenParam Inspector generator.
   * @param cmdGeneratorParam Cmd generator.
   */
  @SuppressWarnings("WeakerAccess")
  public EmbeddableIntrospector(final Supplier<Introspector> introspectorGenParam,
      final Function<Class<?>, Cmd> cmdGeneratorParam) {

    super();
    this.introspectorGenerator = introspectorGenParam;
    this.cmdGenerator = cmdGeneratorParam;

    LOGGER.debug("Constructor. introspectorGenParam: {}, cmdGeneratorParam: {}",
        introspectorGenParam,
        cmdGeneratorParam);
  }

  @Override
  public boolean introspect(final Field field, final Object obj) throws IllegalAccessException {
    if (field.isAnnotationPresent(EmbeddedId.class)) {
      LOGGER.debug("Make embeddable object for field: {} on object: {}", field, obj);

      final Object embeddableObject = makeEmbeddableObject(field,
          obj,
          this.cmdGenerator,
          this.introspectorGenerator);

      LOGGER.debug("Embeddable object to set: {}", embeddableObject);

      setFieldVal(obj, field, embeddableObject);

      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    LOGGER.debug("toString");
    return MoreObjects.toStringHelper(this)
        .add("introspectorGenerator", this.introspectorGenerator)
        .add("cmdGenerator", this.cmdGenerator)
        .toString();
  }

}
