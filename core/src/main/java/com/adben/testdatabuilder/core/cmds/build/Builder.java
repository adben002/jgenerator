package com.adben.testdatabuilder.core.cmds.build;

import java.util.List;

/**
 * Builder to create objects of the given type {@link T}.
 */
@SuppressWarnings({"ClassNamePrefixedWithPackageName", "InterfaceNamingConvention"})
public interface Builder<T> {

    /**
     * Make a list of objects using the given amount and the for overrides.
     *
     * @param count The number of object to create for the given type.
     * @return List of created objects, this may include other builtObjects. But is generally
     * suggested to return a list of {@link T}.
     */
    List<Object> make(int count);

    /**
     * @return Class this builder is for.
     */
    Class<T> getClz();
}
