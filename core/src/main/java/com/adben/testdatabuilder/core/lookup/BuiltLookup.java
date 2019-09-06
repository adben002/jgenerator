package com.adben.testdatabuilder.core.lookup;

import static com.adben.testdatabuilder.core.store.DataBuilderStore.store;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.store.DataBuilderStore;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;

/**
 * Used for referencing any already built object. For instance a Language object may have already
 * been built in the command queue, and you wish to reference this specific Language object when
 * building a film.
 *
 * <p>
 *
 * For example:
 * <pre>
 *     {@code
 *          DataBuilder.a()
 *              .q(CoreCmd.a(Language.class).howMany(20))
 *              .q(CoreCmd.a(Film.class).howMany(20)
 *                  .props(PropOverrideSet.<Film>a().all(PropOverride.<Film>a()
 *                      .prop(StringPathBuilder.of("language"), BuiltLookup.a(Language.class,
 * 2)))
 *                  )
 *              )
 *              .build()
 *     }
 * </pre>
 */
@SuppressWarnings("ClassWithoutNoArgConstructor")
public class BuiltLookup<T> implements Lookup<T> {

  private static final Logger LOGGER = getLogger(MethodHandles.lookup().lookupClass());

  @SuppressWarnings("InstanceVariableNamingConvention")
  private final Class<T> clz;

  private final int lookupIdx;

  /**
   * @param clzParam Class to fetch from previous builds.
   * @param i Index to get built entity from built collection.
   */
  @SuppressWarnings("WeakerAccess")
  protected BuiltLookup(final Class<T> clzParam, final int i) {
    super();
    this.clz = clzParam;
    this.lookupIdx = i;
    LOGGER.debug("Constructor. clzParam: {}, i: {}", clzParam, i);
  }

  /**
   * Named constructor for {@link BuiltLookup#BuiltLookup(Class, int)}.
   *
   * @param clzParam Class to fetch from previous builds.
   * @param lookupVal Index to get built entity from built collection.
   * @param <T> Type of lookup generatedValue.
   * @return New BuiltLookup.
   */
  public static <T> BuiltLookup<T> builtLookup(final Class<T> clzParam, final int lookupVal) {
    LOGGER.debug("Named constructor. clzParam: {}, i: {}", clzParam, lookupVal);
    return new BuiltLookup<>(clzParam, lookupVal);
  }

  /**
   * From the {@link DataBuilderStore#store()} get the object of the supplied {@link
   * BuiltLookup#clz} of index {@link BuiltLookup#lookupIdx}.
   *
   * {@inheritDoc}
   *
   * @return Essentially do: {@code store().builtObjects.get(this.clz).get(this.lookupIdx)}
   */
  @SuppressWarnings("unchecked")
  @Override
  public T lookup() {
    LOGGER.debug("Lookup store for. clz: {}, lookupIdx: {}", this.clz, this.lookupIdx);
    return Lists.newArrayList((Iterable<? extends T>) store().builtObjects.get(this.clz))
        .get(this.lookupIdx);
  }

  @Override
  public String toString() {
    LOGGER.debug("toString");
    return MoreObjects.toStringHelper(this)
        .add("clz", this.clz)
        .add("lookupIdx", this.lookupIdx)
        .toString();
  }

}
