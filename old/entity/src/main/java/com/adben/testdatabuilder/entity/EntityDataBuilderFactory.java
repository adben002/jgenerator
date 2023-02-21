package com.adben.testdatabuilder.entity;

import static com.adben.testdatabuilder.core.DataBuilder.createDataBuilder;
import static com.adben.testdatabuilder.entity.store.EntityDataBuilderStore.ENTITY;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.DataBuilder;
import com.adben.testdatabuilder.entity.store.EntityDataBuilderStore;
import javax.persistence.EntityManager;
import org.slf4j.Logger;

/**
 * Factory class for dealing with creating a {@link DataBuilder} with associated entityStore setup.
 */
@SuppressWarnings({"WeakerAccess", "ClassNamePrefixedWithPackageName"})
public final class EntityDataBuilderFactory {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private EntityDataBuilderFactory() {
    super();
    LOGGER.debug("Default constructor");
  }

  /**
   * Set the {@link EntityManager} on the {@link EntityDataBuilderStore#ENTITY}. Then
   * createDataBuilder a new {@link DataBuilder}.
   *
   * @param em Entity manager to use.
   * @return New {@link DataBuilder}.
   */
  public static DataBuilder createEntityDataBuilder(final EntityManager em) {
    LOGGER.debug("createEntityDataBuilder using entity manage: {}", em);
    ENTITY.set(new EntityDataBuilderStore(em));
    LOGGER.debug("Entity store setup, now is: {}", ENTITY);

    final DataBuilder dataBuilder = createDataBuilder();
    LOGGER.debug("Created data builder: {}", dataBuilder);
    return dataBuilder;
  }

}
