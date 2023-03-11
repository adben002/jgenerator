package com.adben.testdatabuilder.core.analyzers.generators.generator;

import static com.adben.testdatabuilder.core.helper.ReflectionHelper.getClz;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.google.common.base.MoreObjects;
import java.lang.reflect.Type;
import org.slf4j.Logger;

/**
 * {@link com.adben.testdatabuilder.core.analyzers.generators.Generator Generator} for {@link
 * Enum}.
 *
 * <p>
 *
 * {@link EnumGenerator#counter} starts at 0, then increments from there. And then grabs a mod from
 * the enum of given type.
 */
public class EnumGenerator extends AbstractGenerator<Enum<?>> {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private int counter = 0;

    /**
     * Default constructor.
     */
    public EnumGenerator() {
        super();
        LOGGER.trace("Default constructor");
    }

    /**
     * Applicable type is {@link Enum}.
     *
     * <p>
     *
     * {@inheritDoc}
     */
    @Override
    protected boolean isApplicable(final Type type) {
        return Enum.class.isAssignableFrom(getClz(type));
    }

    /**
     * Get the next value for this generator, which is the enum index of the counter.
     *
     * <p>
     *
     * {@inheritDoc}
     */
    @Override
    protected Enum<?> getNextValue(final Type type) {
        try {
            final Object[] possibleValues = getClz(type).getEnumConstants();
            return (Enum<?>) possibleValues[this.counter % possibleValues.length];
        } finally {
            this.counter++;
        }
    }

    @Override
    public String toString() {
        LOGGER.trace("toString");
        return MoreObjects.toStringHelper(this).add("counter", this.counter).toString();
    }
}
