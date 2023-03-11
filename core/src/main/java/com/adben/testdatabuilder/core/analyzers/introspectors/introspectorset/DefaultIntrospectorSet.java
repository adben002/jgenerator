package com.adben.testdatabuilder.core.analyzers.introspectors.introspectorset;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.generators.generatorset.DefaultGeneratorSet;
import com.adben.testdatabuilder.core.analyzers.introspectors.Introspector;
import com.adben.testdatabuilder.core.analyzers.introspectors.introspector.GeneratedIntrospector;
import com.adben.testdatabuilder.core.analyzers.introspectors.introspector.NoOpIntrospector;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;

/**
 * The default {@link Introspector}Set. This is a specialism of an {@link Introspector}, that
 * contains an {@link Iterable} of other {@link Introspector}s, where given the field and object
 * loop over and return when one of the {@link Introspector}s applies a field generatedValue.
 *
 * <p>
 *
 * See {@link DefaultIntrospectorSet#introspectors} for the default values.
 */
public class DefaultIntrospectorSet extends AbstractIntrospectorSet {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    public DefaultIntrospectorSet() {
        super(ImmutableList.<Introspector>builder()
                .add(new NoOpIntrospector())
                .add(new GeneratedIntrospector(new DefaultGeneratorSet()))
                .build());
        LOGGER.debug("Default constructor");
    }
}
