package com.adben.testdatabuilder.core.analyzers.introspectors.helper;

import static com.adben.testdatabuilder.core.analyzers.introspectors.helper.IntrospectorStore.NESTED;
import static com.adben.testdatabuilder.core.analyzers.introspectors.helper.IntrospectorStore.getNested;
import static com.adben.testdatabuilder.core.store.DataBuilderStore.store;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Collections.singletonList;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.cmds.Cmd;
import com.adben.testdatabuilder.core.store.DataBuilderStore;
import com.google.common.base.Optional;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;

/**
 * Helper for the {@link com.adben.testdatabuilder.core.analyzers.introspectors.Introspector
 * Introspector}s that deal with {@link IntrospectorStore#NESTED}.
 */
public final class NestedIntrospectorHelper {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private NestedIntrospectorHelper() {
    super();
  }

  /**
   * A facade that allows nested building of {@link Cmd}s, protects against stack overflow loops by
   * using {@link IntrospectorStore#NESTED}.
   *
   * @param clz Class to find.
   * @param obj Object we are currently setting, this is used to prevent stack overflow.
   * @param cmd Cmd to use if to nested build.
   * @return List of built objects, either from {@link DataBuilderStore#builtObjects} or from {@link
   * IntrospectorStore#NESTED} or a newly built object.
   */
  public static List<Object> findNestedClasses(final Class<?> clz,
      final Object obj,
      final Cmd cmd) {

    LOGGER.debug("Find nested {}. Cmd: {}. Object to put onto stack if {} not found: {}",
        clz,
        cmd,
        clz,
        obj);

    return findNestedClassesImpl(clz, obj, cmd);
  }

  /**
   * A facade that allows nested building of {@link Cmd}s, protects against stack overflow loops by
   * using {@link IntrospectorStore#NESTED}.
   *
   * <p>
   *
   * Does not add to the {@link IntrospectorStore#NESTED} stack an existing object to build.
   *
   * @param clz Class to find.
   * @param cmd Cmd to use if to nested build.
   * @return List of built objects, either from {@link DataBuilderStore#builtObjects} or from {@link
   * IntrospectorStore#NESTED} or a newly built object.
   */
  public static List<Object> findNestedClasses(final Class<?> clz,
      final Cmd cmd) {

    LOGGER.debug("Find nested {}. Cmd: {}.",
        clz,
        cmd);

    return findNestedClassesImpl(clz, null, cmd);
  }

  /**
   * A facade that allows nested building of {@link Cmd}s, protects against stack overflow loops by
   * using {@link IntrospectorStore#NESTED}.
   *
   * @param clz Class to find.
   * @param obj Object we are currently setting, this is used to prevent stack overflow.
   * @param cmd Cmd to use if to nested build.
   * @return List of built objects, either from {@link DataBuilderStore#builtObjects} or from {@link
   * IntrospectorStore#NESTED} or a newly built object.
   */
  private static List<Object> findNestedClassesImpl(final Class<?> clz,
      final Object obj,
      final Cmd cmd) {

    LOGGER.debug("Find nested {}. Cmd: {}. Object to put onto stack if {} not found: {}",
        clz,
        cmd,
        clz,
        obj);

    final Optional<Object> duplicateNested = getNested(clz);
    if (duplicateNested.isPresent()) {
      LOGGER.debug("");
      return singletonList(duplicateNested.get());
    }

    // If we do not have a built entity, then create one using a Cmd.
    if (!store().builtObjects.containsKey(clz)) {
      // Put the object onto the nested stack when we are doing a rebuild, this is used to stop
      // infinite loops.
      if (obj != null) {
        NESTED.get().add(obj);
      }
      cmd.build();
      //noinspection VariableNotUsedInsideIf
      if (obj != null) {
        NESTED.get().pop();
      }
    }
    // Now that we are sure there is a stored entity of the given class we can grab that.
    return Arrays.asList(store().builtObjects.get(clz).toArray());
  }

}
