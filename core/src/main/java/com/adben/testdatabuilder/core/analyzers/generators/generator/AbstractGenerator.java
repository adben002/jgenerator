package com.adben.testdatabuilder.core.analyzers.generators.generator;

import static com.adben.testdatabuilder.core.analyzers.generators.GeneratedValue.generatedValue;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.generators.GeneratedValue;
import com.adben.testdatabuilder.core.analyzers.generators.Generator;
import com.google.common.base.Optional;
import java.lang.reflect.Type;
import org.slf4j.Logger;

/**
 * Abstraction for generating values. Handles logic for calculating whether it is applicable and
 * returning the next value.
 *
 * @param <T> Type the generator applies for.
 */
public abstract class AbstractGenerator<T> implements Generator {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    /**
     * Default constructor.
     */
    protected AbstractGenerator() {
        super();
        LOGGER.trace("Default constructor");
    }

    /**
     * If the type applies for the generator, {@link AbstractGenerator#getNextValue(Type)} is called.
     *
     * <p>
     *
     * {@inheritDoc}
     */
    @Override
    public Optional<GeneratedValue> generateValue(final Type type) {

        if (this.isApplicable(type)) {
            LOGGER.debug("Type {} type, is Applicable for: {}", type, this);

            final Object val = this.getNextValue(type);
            LOGGER.debug("Next generatedValue for {} {}: {}", type, this, val);

            final Optional<GeneratedValue> returnVal = Optional.of(generatedValue(val));
            LOGGER.debug("Return generatedValue: {}", returnVal);
            return returnVal;
        }
        LOGGER.debug("Type {} type, is NOT Applicable: {}", type, this);

        final Optional<GeneratedValue> emptyGeneratedValue = Optional.absent();
        LOGGER.debug("Empty generated return generatedValue: {}", emptyGeneratedValue);
        return emptyGeneratedValue;
    }

    /**
     * Whether the generator is applicable to the supplied type.
     *
     * @param type Type that should be generated.
     * @return True if this {@link Generator} can generate the supplied type.
     */
    protected abstract boolean isApplicable(final Type type);

    /**
     * Get the next value for this type generator.
     *
     * @param type Type that should be generated.
     * @return The next generated value for the supplied type.
     */
    protected abstract T getNextValue(final Type type);
}
