package com.adben.testdatabuilder.entity.analyzers.introspectors.introspectorset;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.generators.generatorset.DefaultGeneratorSet;
import com.adben.testdatabuilder.core.analyzers.introspectors.Introspector;
import com.adben.testdatabuilder.core.analyzers.introspectors.introspector.GeneratedIntrospector;
import com.adben.testdatabuilder.core.analyzers.introspectors.introspector.NoOpIntrospector;
import com.adben.testdatabuilder.core.analyzers.introspectors.introspectorset.AbstractIntrospectorSet;
import com.adben.testdatabuilder.entity.analyzers.introspectors.introspector.EmbeddableIntrospector;
import com.adben.testdatabuilder.entity.analyzers.introspectors.introspector.GeneratedValueIntrospector;
import com.adben.testdatabuilder.entity.analyzers.introspectors.introspector.IterableEntityIntrospector;
import com.adben.testdatabuilder.entity.analyzers.introspectors.introspector.NestedEntityIntrospector;
import com.adben.testdatabuilder.entity.analyzers.introspectors.introspector.StringColumnIntrospector;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;

/**
 * The entity {@link Introspector}Set. This is a specialism of an {@link Introspector}, that
 * contains an {@link Iterable} of other {@link Introspector}s, where given the field and object
 * loop over and return when one of the {@link Introspector}s applies a field generatedValue.
 *
 * <p>
 *
 * See {@link EntityIntrospectorSet#introspectors} for the default values.
 */
public class EntityIntrospectorSet extends AbstractIntrospectorSet {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  public EntityIntrospectorSet() {
    super(ImmutableList.<Introspector>builder()
        .add(new NoOpIntrospector())
        .add(new GeneratedValueIntrospector())
        .add(new StringColumnIntrospector())
        .add(new IterableEntityIntrospector())
        .add(new NestedEntityIntrospector())
        .add(new EmbeddableIntrospector())
        .add(new GeneratedIntrospector(new DefaultGeneratorSet()))
        .build());
    LOGGER.debug("Default constructor");
  }

}
