package com.adben.testdatabuilder.core.analyzers.generators.generator;

import static com.adben.testdatabuilder.core.helper.ReflectionHelper.getClz;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.google.common.base.MoreObjects;
import java.lang.reflect.Type;
import java.util.Objects;
import org.slf4j.Logger;

/**
 * {@link com.adben.testdatabuilder.core.analyzers.generators.Generator Generator} for {@link
 * Double}.
 *
 * <p>
 *
 * {@link DoubleGenerator#value} starts at 0, then increments from there.
 */
public class DoubleGenerator extends AbstractGenerator<Double> {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private double value = 0;

    /**
     * Default constructor.
     */
    public DoubleGenerator() {
        super();
        LOGGER.trace("Default constructor");
    }

    /**
     * Applicable type is {@link Double} or double.
     *
     * <p>
     *
     * {@inheritDoc}
     */
    @Override
    protected boolean isApplicable(final Type type) {
        return Double.class.isAssignableFrom(getClz(type)) || Objects.equals(getClz(type), double.class);
    }

    /**
     * Get the next value for this generator, and increment the value by 1.
     *
     * <p>
     *
     * {@inheritDoc}
     */
    @Override
    protected Double getNextValue(final Type type) {
        try {
            return this.value;
        } finally {
            this.value++;
        }
    }

    @Override
    public String toString() {
        LOGGER.trace("toString");
        return MoreObjects.toStringHelper(this)
                .add("generatedValue", this.value)
                .toString();
    }
}
