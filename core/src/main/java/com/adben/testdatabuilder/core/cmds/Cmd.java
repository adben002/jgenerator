package com.adben.testdatabuilder.core.cmds;

import com.adben.testdatabuilder.core.store.DataBuilderStore;

/**
 * Command to be passed into the {@link com.adben.testdatabuilder.core.DataBuilder}, which allows
 * the creation of builtObjects via the {@link Cmd#build()} method.
 */
@SuppressWarnings({"InterfaceNamingConvention", "InterfaceWithOnlyOneDirectInheritor"})
public interface Cmd {

    /**
     * Create the objects for the given implementation when this method is called. Store the created
     * object in {@link DataBuilderStore#commands}.
     */
    void build();

    /**
     * @return Classes that this Cmd deals with.
     */
    Iterable<Class<?>> getClzs();
}
