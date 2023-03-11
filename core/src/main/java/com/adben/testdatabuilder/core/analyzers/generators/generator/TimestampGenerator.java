package com.adben.testdatabuilder.core.analyzers.generators.generator;

import static com.adben.testdatabuilder.core.helper.ReflectionHelper.getClz;
import static java.lang.System.currentTimeMillis;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import org.slf4j.Logger;

/**
 * {@link com.adben.testdatabuilder.core.analyzers.generators.Generator Generator} for {@link
 * Timestamp}.
 *
 * <p>
 *
 * Gets the timestamp from the return generatedValue of {@link System#currentTimeMillis()}.
 */
public class TimestampGenerator extends AbstractGenerator<Timestamp> {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    /**
     * Default constructor.
     */
    public TimestampGenerator() {
        super();
        LOGGER.trace("Default constructor");
    }

    /**
     * Applicable type is {@link Timestamp}.
     *
     * <p>
     *
     * {@inheritDoc}
     */
    @Override
    protected boolean isApplicable(final Type type) {
        return Timestamp.class.isAssignableFrom(getClz(type));
    }

    /**
     * Get the next value for this generator, which is the current time millis.
     *
     * <p>
     *
     * {@inheritDoc}
     */
    @Override
    protected Timestamp getNextValue(final Type type) {
        return new Timestamp(currentTimeMillis());
    }
}
