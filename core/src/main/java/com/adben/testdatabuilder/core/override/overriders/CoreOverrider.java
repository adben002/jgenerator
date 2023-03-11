package com.adben.testdatabuilder.core.override.overriders;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.introspectors.introspectorset.DefaultIntrospectorSet;
import org.slf4j.Logger;

/**
 * CoreOverride, which uses {@link DefaultIntrospectorSet}.
 *
 * <p>
 *
 * Example usage:
 * <pre>
 *   {@code
 *          createDataBuilder()
 *              .reset()
 *              .add(core(TestSubBean.class).overrides(ovrrdSet(TestSubBean.class)
 *                  .all(coreFields(TestSubBean.class, new Overrider<TestSubBean>() {
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
public final class CoreOverrider<T> extends AbstractFieldOverrider<T, CoreOverrider<T>> {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private CoreOverrider(final Class<T> t) {
        super(new DefaultIntrospectorSet());
        LOGGER.debug("Constructor. Class: {}", t);
    }

    /**
     * Create a CoreOverrider object, most of the logic is performed in {@link
     * AbstractFieldOverrider}. This is essential a wrapper on {@link DefaultIntrospectorSet} and
     * {@link AbstractFieldOverrider}.
     *
     * @param clz Class that the overrider is working for. This is only for type enforcement.
     * @param <T> type of the clz.
     * @return New CoreOverrider.
     */
    public static <T> CoreOverrider<T> coreOverrider(final Class<T> clz) {
        LOGGER.debug("Named constructor. Class: {}", clz);
        return new CoreOverrider<>(clz);
    }

    /**
     * Create a CoreOverrider object, most of the logic is performed in {@link
     * AbstractFieldOverrider}. This is essential a wrapper on {@link DefaultIntrospectorSet} and
     * {@link AbstractFieldOverrider}.
     *
     * @param t Class that the overrider is working for. This is only for type enforcement.
     * @param callbackOverrider Overrider as a callback.
     * @param <T> type of the clz.
     * @return New CoreOverrider.
     */
    public static <T> CoreOverrider<T> coreFields(final Class<T> t, final Overrider<T> callbackOverrider) {

        LOGGER.debug("Named constructor. Class: {}. callbackOverrider: {}", t, callbackOverrider);
        return coreOverrider(t).callbackOverrider(callbackOverrider);
    }

    @Override
    protected CoreOverrider<T> getThis() {
        return this;
    }
}
