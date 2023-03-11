package com.adben.testdatabuilder.core.analyzers.generators.helper;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Collections.addAll;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.exception.DataBuilderException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import org.slf4j.Logger;

/**
 * Helper for {@link com.adben.testdatabuilder.core.analyzers.generators.Generator}s that create an
 * iterable.
 */
public final class IteratorGeneratorHelper {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private IteratorGeneratorHelper() {
        super();
        LOGGER.trace("Default constructor");
    }

    /**
     * Given a supplied type of collection, return a new instance of the collection with the given
     * values.
     *
     * <p>
     *
     * Uses the default constructor.
     *
     * @param type Type of collection to create.
     * @param values Values to add to the constructor.
     * @param <C> Type of collection.
     * @return New collection with all values added.
     */
    public static <C extends Collection<? super Object>> C createCollection(
            final Class<C> type, final Object... values) {

        LOGGER.debug("Type: {} with values: {}", type, values);

        final C newList;
        try {
            newList = type.getConstructor().newInstance();
            LOGGER.debug("New collection: {}", newList);
        } catch (final InstantiationException
                | IllegalAccessException
                | NoSuchMethodException
                | InvocationTargetException e) {
            throw new DataBuilderException(e);
        }
        addAll(newList, values);
        LOGGER.debug("Collection with added values: {}", newList);
        return newList;
    }
}
