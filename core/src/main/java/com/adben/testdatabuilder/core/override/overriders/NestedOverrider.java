package com.adben.testdatabuilder.core.override.overriders;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.introspectors.introspectorset.NestedIntrospectorSet;
import org.slf4j.Logger;

/**
 * CoreOverride, which uses {@link NestedIntrospectorSet}.
 *
 * <p>
 *
 * Example usage:
 * <pre>
 *   {@code
 *          createDataBuilder()
 *              .reset()
 *              .add(core(TestSubBean.class).overrides(ovrrdSet(TestSubBean.class)
 *                  .all(nestedFields(TestSubBean.class, new Overrider<TestSubBean>() {
 *                    public void setFields(final TestSubBean o) {
 *                      o.setA(AlphaNumericHelper.toAlphaNumeric(0));
 *                    }
 *                  }))))
 *              .build();
 *   }
 * </pre>
 *
 * @param <T> Type of the {@link Overrider}.
 */
@SuppressWarnings("ClassWithoutNoArgConstructor")
public final class NestedOverrider<T> extends AbstractFieldOverrider<T, NestedOverrider<T>> {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private NestedOverrider(final Class<T> t) {
        super(new NestedIntrospectorSet());
        LOGGER.debug("Constructor. Class: {}", t);
    }

    /**
     * Create a CoreOverrider object, most of the logic is performed in {@link
     * AbstractFieldOverrider}. This is essential a wrapper on {@link NestedIntrospectorSet} and
     * {@link AbstractFieldOverrider}.
     *
     * @param t Class that the overrider is working for. This is only for type enforcement.
     * @param <T> type of the clz.
     * @return New NestedOverrider.
     */
    public static <T> NestedOverrider<T> nestedOverrider(final Class<T> t) {
        LOGGER.debug("Named constructor. Class: {}", t);
        return new NestedOverrider<>(t);
    }

    /**
     * Create a CoreOverrider object, most of the logic is performed in {@link
     * AbstractFieldOverrider}. This is essential a wrapper on {@link NestedIntrospectorSet} and
     * {@link AbstractFieldOverrider}.
     *
     * @param t Class that the overrider is working for. This is only for type enforcement.
     * @param callbackOverrider Overrider as a callback.
     * @param <T> type of the clz.
     * @return New NestedOverrider.
     */
    public static <T> NestedOverrider<T> nestedFields(final Class<T> t, final Overrider<T> callbackOverrider) {

        LOGGER.debug("Named constructor. Class: {}. callbackOverrider: {}", t, callbackOverrider);
        return nestedOverrider(t).callbackOverrider(callbackOverrider);
    }

    @Override
    protected NestedOverrider<T> getThis() {
        return this;
    }
}
