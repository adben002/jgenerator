package com.adben.testdatabuilder.core.override.overriderset;

import static com.adben.testdatabuilder.core.override.overriders.EmptyOverrider.emptyOverrider;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.override.overriders.EmptyOverrider;
import com.adben.testdatabuilder.core.override.overriders.Overrider;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import java.util.Map;
import org.slf4j.Logger;

/**
 * Default for {@link OverriderSet}.
 */
@SuppressWarnings("ClassWithoutNoArgConstructor")
public final class DefaultOverriderSet<T> implements OverriderSet<T> {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());
    private static final int ALL_KEY = -1;

    private final Map<Integer, Overrider<? super T>> propSet = Maps.newHashMap();

    private int counter = 0;

    /**
     * Only constructor.
     */
    private DefaultOverriderSet(final Class<T> clz) {
        super();
        LOGGER.debug("Constructor. Clz: {}", clz);
    }

    /**
     * Create a DefaultOverriderSet object.
     *
     * @param clz Class that the {@link OverriderSet} is working for. This is only for type
     * enforcement.
     * @param <T> type of the clz.
     * @return New DefaultOverriderSet.
     */
    public static <T> DefaultOverriderSet<T> ovrrdSet(final Class<T> clz) {
        LOGGER.debug("Named constructor. Clz: {}", clz);
        return new DefaultOverriderSet<>(clz);
    }

    /**
     * Set {@link Overrider} as prop for all to the supplied property.
     *
     * @param prop Property to set for all overrides.
     * @return this.
     */
    public DefaultOverriderSet<T> all(final Overrider<? super T> prop) {
        LOGGER.debug("Set all to: {}", prop);
        this.propSet.put(ALL_KEY, prop);
        LOGGER.debug("New state: {}", this);
        return this;
    }

    /**
     * Set {@link Overrider} as prop for the given index to the supplied property.
     *
     * @param i Index to set {@link Overrider} property for, this should match with the how many.
     * @param prop Property to set for index overrides.
     * @return this.
     */
    public DefaultOverriderSet<T> idx(final int i, final Overrider<? super T> prop) {
        LOGGER.debug("Set index {} to: {}", i, prop);
        this.propSet.put(i, prop);
        LOGGER.debug("New state: {}", this);
        return this;
    }

    /**
     * Add {@link Overrider}. This counts up the index, so multiple calls to this will start by
     * setting the index 0, then index 1 and so on.
     *
     * @param prop Property to set for index overrides.
     * @return this.
     */
    public DefaultOverriderSet<T> add(final Overrider<? super T> prop) {
        LOGGER.debug("Set index {} to: {}", this.counter, prop);
        this.propSet.put(this.counter, prop);
        this.counter++;
        LOGGER.debug("New state: {}", this);
        return this;
    }

    @Override
    public Overrider<? super T> get(final int idx) {
        if (this.propSet.containsKey(idx)) {
            final Overrider<? super T> overrider = this.propSet.get(idx);
            LOGGER.debug("Found overrider for index {}: {}", idx, overrider);
            return overrider;
        }
        if (this.propSet.containsKey(ALL_KEY)) {
            final Overrider<? super T> overrider = this.propSet.get(ALL_KEY);
            LOGGER.debug("Found all overrider: {}", overrider);
            return overrider;
        }
        final EmptyOverrider<Object> emptyOverrider = emptyOverrider(Object.class);
        LOGGER.debug("Found no overrider, so using an empty one: {}", emptyOverrider);
        return emptyOverrider;
    }

    @Override
    public String toString() {
        LOGGER.debug("toString");
        return MoreObjects.toStringHelper(this)
                .add("propSet", this.propSet)
                .add("counter", this.counter)
                .toString();
    }
}
