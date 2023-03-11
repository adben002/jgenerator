package com.adben.testdatabuilder.core.analyzers.generators.generatorset;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.generators.GeneratedValue;
import com.adben.testdatabuilder.core.analyzers.generators.Generator;
import com.adben.testdatabuilder.core.analyzers.generators.generator.BigDecimalGenerator;
import com.adben.testdatabuilder.core.analyzers.generators.generator.BooleanGenerator;
import com.adben.testdatabuilder.core.analyzers.generators.generator.ByteGenerator;
import com.adben.testdatabuilder.core.analyzers.generators.generator.CharacterGenerator;
import com.adben.testdatabuilder.core.analyzers.generators.generator.DateSqlGenerator;
import com.adben.testdatabuilder.core.analyzers.generators.generator.DoubleGenerator;
import com.adben.testdatabuilder.core.analyzers.generators.generator.EnumGenerator;
import com.adben.testdatabuilder.core.analyzers.generators.generator.FloatGenerator;
import com.adben.testdatabuilder.core.analyzers.generators.generator.IntegerGenerator;
import com.adben.testdatabuilder.core.analyzers.generators.generator.ListGenerator;
import com.adben.testdatabuilder.core.analyzers.generators.generator.LongGenerator;
import com.adben.testdatabuilder.core.analyzers.generators.generator.SetGenerator;
import com.adben.testdatabuilder.core.analyzers.generators.generator.ShortGenerator;
import com.adben.testdatabuilder.core.analyzers.generators.generator.StringGenerator;
import com.adben.testdatabuilder.core.analyzers.generators.generator.TimestampGenerator;
import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import java.lang.reflect.Type;
import org.slf4j.Logger;

/**
 * A Set of {@link Generator}s. This class delegates to the list of {@link Generator}s, calling each
 * one to see if it is applicable. If it returns a {@link GeneratedValue} then use that to return
 * the value.
 *
 * <p>
 *
 * This is essentially a {@link Generator} wrapper.
 */
@SuppressWarnings("OverlyCoupledClass")
public class DefaultGeneratorSet implements Generator {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private final Iterable<Generator> generators = ImmutableList.<Generator>builder()
            .add(new BigDecimalGenerator())
            .add(new BooleanGenerator())
            .add(new ByteGenerator())
            .add(new CharacterGenerator())
            .add(new DateSqlGenerator())
            .add(new DoubleGenerator())
            .add(new EnumGenerator())
            .add(new FloatGenerator())
            .add(new IntegerGenerator())
            .add(new ListGenerator())
            .add(new SetGenerator())
            .add(new LongGenerator())
            .add(new ShortGenerator())
            .add(new StringGenerator())
            .add(new TimestampGenerator())
            .build();

    /**
     * Default constructor.
     */
    public DefaultGeneratorSet() {
        super();
        LOGGER.trace("Default constructor");
    }

    /**
     * Loop over {@link DefaultGeneratorSet#generators}, to find a generator that can generate a value
     * for the supplied type.
     *
     * <p>
     *
     * {@inheritDoc}
     */
    @Override
    public Optional<GeneratedValue> generateValue(final Type type) {
        LOGGER.debug("Generate the type: {}. For generators: {}", type, this);
        for (final Generator generator : this.generators) {
            final Optional<GeneratedValue> val = generator.generateValue(type);
            LOGGER.debug("Generator {} returned value: {}", generator, val);
            if (val.isPresent()) {
                LOGGER.debug("Returned generated value: {}", val);
                return val;
            }
        }
        LOGGER.debug("Type {} did not generate a value", type);
        return Optional.absent();
    }

    @Override
    public String toString() {
        LOGGER.trace("toString");
        return MoreObjects.toStringHelper(this)
                .add("generators", this.generators)
                .toString();
    }
}
