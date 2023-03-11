package com.adben.testdatabuilder.core.analyzers.generators.generator;

import static com.adben.testdatabuilder.core.analyzers.generators.helper.AlphaNumericHelper.toAlphaNumeric;
import static com.adben.testdatabuilder.core.helper.ReflectionHelper.getClz;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.generators.helper.AlphaNumericHelper;
import com.google.common.base.MoreObjects;
import java.lang.reflect.Type;
import org.slf4j.Logger;

/**
 * {@link com.adben.testdatabuilder.core.analyzers.generators.Generator Generator} for {@link
 * Short}.
 *
 * <p>
 *
 * {@link StringGenerator#value} starts at 0, then increments by 1 from there. We use the {@link
 * StringGenerator#value} as the parameter to {@link AlphaNumericHelper#toAlphaNumeric(int)}.
 */
public class StringGenerator extends AbstractGenerator<String> {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private int value = 1;

    /**
     * Default constructor.
     */
    public StringGenerator() {
        super();
        LOGGER.trace("Default constructor");
    }

    /**
     * Applicable type is {@link String}.
     *
     * <p>
     *
     * {@inheritDoc}
     */
    @Override
    protected boolean isApplicable(final Type type) {
        return String.class.isAssignableFrom(getClz(type));
    }

    /**
     * Get the next value for this generator, which is used in
     * <p>
     * {@link AlphaNumericHelper#toAlphaNumeric(int)}
     *
     * and increment the value by 1.
     *
     * <p>
     *
     * {@inheritDoc}
     */
    @Override
    protected String getNextValue(final Type type) {
        try {
            return toAlphaNumeric(this.value);
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
