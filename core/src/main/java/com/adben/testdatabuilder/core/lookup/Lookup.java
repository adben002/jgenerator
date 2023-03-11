package com.adben.testdatabuilder.core.lookup;

/**
 * Lookup a value of the given type parameter.
 *
 * @param <T> Type that the {@link Lookup#lookup()} returns.
 */
@SuppressWarnings({"ClassNamePrefixedWithPackageName", "InterfaceNamingConvention"})
public interface Lookup<T> {

    /**
     * Return a lookup of the given type.
     *
     * @return The supplied type.
     */
    T lookup();
}
