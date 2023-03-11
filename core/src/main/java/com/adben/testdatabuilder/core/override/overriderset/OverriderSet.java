package com.adben.testdatabuilder.core.override.overriderset;

import com.adben.testdatabuilder.core.override.overriders.Overrider;

/**
 * Provide an interface over a collection of {@link Overrider}s. Which is essentially a map with
 * logic on whether we have an all set on it.
 */
@SuppressWarnings({"ClassNamePrefixedWithPackageName", "InterfaceWithOnlyOneDirectInheritor"})
public interface OverriderSet<T> {

    /**
     * Get the {@link Overrider} for the supplied index.
     *
     * @param idx Index to fetch for the set of {@link Overrider}s.
     * @return The applicable {@link Overrider}.
     */
    Overrider<? super T> get(final int idx);
}
