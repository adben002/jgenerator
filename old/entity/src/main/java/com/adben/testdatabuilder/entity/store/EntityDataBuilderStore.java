package com.adben.testdatabuilder.entity.store;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import javax.persistence.EntityManager;
import org.slf4j.Logger;

/**
 * Store for the entities. This contains the {@link EntityDataBuilderStore#em} which is used for any
 * interactions with the {@link javax.persistence.PersistenceContext}.
 *
 * <p>
 *
 * This class utilises {@link ThreadLocal} so that the store is specific to the thread. The {@link
 * ThreadLocal} is useful because it allows anything to access the data from anywhere within the
 * code.
 */
@SuppressWarnings("ClassWithoutNoArgConstructor")
public class EntityDataBuilderStore {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  /**
   * {@link ThreadLocal} store for the {@link EntityDataBuilderStore}.
   */
  public static final ThreadLocal<EntityDataBuilderStore> ENTITY = new ThreadLocal<>();

  /**
   * Entity Manager.
   */
  @SuppressWarnings({"PublicField", "InstanceVariableNamingConvention"})
  public final EntityManager em;

  /**
   * Helper accessor method for getting the {@link EntityDataBuilderStore#ENTITY}.
   *
   * @return The {@link EntityDataBuilderStore#ENTITY}.
   */
  public static EntityDataBuilderStore entityStore() {
    LOGGER.debug("Get the entity store");
    final EntityDataBuilderStore entityStore = ENTITY.get();
    LOGGER.debug("Get the entity store: {}", entityStore);
    return entityStore;
  }

  /**
   * Only constructor for EntityDataBuilderStore.
   *
   * @param emParam Entity manager.
   */
  public EntityDataBuilderStore(final EntityManager emParam) {
    super();
    this.em = Preconditions.checkNotNull(emParam, "Entity manager cannot be null.");
  }

  @Override
  public String toString() {
    LOGGER.debug("toString");
    return MoreObjects.toStringHelper(this)
        .add("em", this.em)
        .toString();
  }

}
