package com.adben.testdatabuilder.entity.analyzers.introspectors.helper;

import static com.adben.testdatabuilder.core.helper.ReflectionHelper.countHowManyClzInStoredCmds;
import static com.adben.testdatabuilder.core.store.DataBuilderStore.isGoingToMakeOneOfThese;
import static com.adben.testdatabuilder.entity.analyzers.introspectors.helper.EntityIntrospectorHelper.createPkMap;
import static com.adben.testdatabuilder.entity.analyzers.introspectors.helper.EntityIntrospectorHelper.getEmbeddableClass;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.google.common.base.Optional;
import java.util.Collection;
import org.slf4j.Logger;

public final class IterableEntityIntrospectorHelper {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private IterableEntityIntrospectorHelper() {
    super();
    LOGGER.debug("Default constructor");
  }

  @SuppressWarnings("StaticMethodOnlyUsedInOneClass")
  public static int numGenericsToCreate(final Class<?> generic) {

    LOGGER.debug("Calculate number of generics to create for: {}", generic);

    final Optional<? extends Class<?>> embeddedFieldValue = getEmbeddableClass(generic);

    LOGGER.debug("Embedded field value: {}", embeddedFieldValue);

    int totalClzToCreate = 1;
    if (embeddedFieldValue.isPresent()) {

      final Collection<Class<?>> pkClasses =
          createPkMap(generic, embeddedFieldValue.get()).values();

      for (final Class<?> clz : pkClasses) {
        final boolean goingToMakeOne = isGoingToMakeOneOfThese(clz);
        LOGGER.debug("Is going to make {}: {}", clz, goingToMakeOne);
        if (goingToMakeOne) {
          totalClzToCreate *= countHowManyClzInStoredCmds(clz);
          LOGGER.debug("Running totalClzToCreate: {}", totalClzToCreate);
        }
      }

    }

    LOGGER.debug("Number of generics to create {}: {}", generic, totalClzToCreate);

    return totalClzToCreate;
  }

}
