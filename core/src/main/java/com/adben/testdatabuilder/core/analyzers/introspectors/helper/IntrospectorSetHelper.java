package com.adben.testdatabuilder.core.analyzers.introspectors.helper;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.introspectors.Introspector;
import java.lang.reflect.Field;
import org.slf4j.Logger;

/**
 * Helper class for composition when we are using a {@link Introspector} to set a field on an
 * object.
 */
public final class IntrospectorSetHelper {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private IntrospectorSetHelper() {
        super();
    }

    /**
     * Loop over the collection of {@link Introspector}, try and apply the {@link Introspector}. If
     * the {@link Introspector} returns true then we break out of this method, because that {@link
     * Introspector} has applied some logic. If the {@link Introspector} returns false then carry on
     * to the next {@link Introspector} to try and apply it.
     *
     * @param introspectors Collection of {@link Introspector}s.
     * @param field Field to set.
     * @param obj Object to set field generatedValue on.
     * @return True if one of the {@link Introspector}s applied successfully. False otherwise.
     * @throws IllegalAccessException Comes from {@link Introspector#introspect(Field, Object)}.
     */
    @SuppressWarnings("BooleanMethodNameMustStartWithQuestion")
    public static boolean introspectField(
            final Iterable<Introspector> introspectors, final Field field, final Object obj)
            throws IllegalAccessException {

        LOGGER.debug("Introspect field {} on object {}, with set: {}", field, obj, introspectors);

        for (final Introspector introspector : introspectors) {
            if (introspector.introspect(field, obj)) {
                LOGGER.debug("Successful instrospection for field {} on obj {}, from: {}", field, obj, introspector);
                return true;
            }
        }

        LOGGER.debug(
                "Could not find an applicable instrospector. Field: {}, object: {}, introspectors: {}",
                field,
                obj,
                introspectors);

        return false;
    }
}
