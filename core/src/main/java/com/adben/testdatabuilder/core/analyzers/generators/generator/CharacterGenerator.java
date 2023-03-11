package com.adben.testdatabuilder.core.analyzers.generators.generator;

import static com.adben.testdatabuilder.core.analyzers.generators.helper.AlphaNumericHelper.getModCharsetAZ09;
import static com.adben.testdatabuilder.core.helper.ReflectionHelper.getClz;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.generators.helper.AlphaNumericHelper;
import com.google.common.base.MoreObjects;
import java.lang.reflect.Type;
import java.util.Objects;
import org.slf4j.Logger;

/**
 * {@link com.adben.testdatabuilder.core.analyzers.generators.Generator Generator} for {@link
 * Character}.
 *
 * <p>
 *
 * {@link CharacterGenerator#counter} starts at 0, then increments from there. And then grabs a mod
 * from {@link AlphaNumericHelper#CHARSET_AZ_09 CHARSET_AZ_09}.
 */
public class CharacterGenerator extends AbstractGenerator<Character> {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private int counter = 0;

    /**
     * Default constructor.
     */
    public CharacterGenerator() {
        super();
        LOGGER.trace("Default constructor");
    }

    /**
     * Applicable type is {@link Character} or char.
     *
     * <p>
     *
     * {@inheritDoc}
     */
    @Override
    protected boolean isApplicable(final Type type) {
        return Character.class.isAssignableFrom(getClz(type)) || Objects.equals(getClz(type), char.class);
    }

    /**
     * Get the next value for this generator, and increment the counter used to generate a character.
     *
     * <p>
     *
     * {@inheritDoc}
     */
    @Override
    protected Character getNextValue(final Type type) {
        try {
            return getModCharsetAZ09(this.counter);
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
