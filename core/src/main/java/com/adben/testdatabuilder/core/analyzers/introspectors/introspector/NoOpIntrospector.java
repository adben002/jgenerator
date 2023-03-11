package com.adben.testdatabuilder.core.analyzers.introspectors.introspector;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.introspectors.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.slf4j.Logger;

/**
 * {@link Introspector} for doing nothing.
 *
 * <p>
 *
 * For cases where we want to skip setting of fields, for instance if the field is final we do not
 * want to set this instance field.
 */
public class NoOpIntrospector implements Introspector {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    public NoOpIntrospector() {
        super();
        LOGGER.debug("Default constructor");
    }

    @Override
    public boolean introspect(final Field field, final Object obj) throws IllegalAccessException {

        //noinspection RedundantIfStatement
        if (Modifier.isFinal(field.getModifiers())) {
            LOGGER.debug("Skip setting of a field that we should not be setting. Object: {}, field: {}", obj, field);
            // If it is final we don't want to do anything with it.
            return true;
        }

        LOGGER.debug("NoOp does not apply to - Object: {}, field: {}", obj, field);
        return false;
    }
}
