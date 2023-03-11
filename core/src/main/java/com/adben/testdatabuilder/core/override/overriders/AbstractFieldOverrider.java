package com.adben.testdatabuilder.core.override.overriders;

import static com.adben.testdatabuilder.core.analyzers.introspectors.helper.IntrospectorHelper.performIntrospection;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.introspectors.Introspector;
import com.adben.testdatabuilder.core.exception.DataBuilderException;
import com.google.common.base.MoreObjects;
import java.util.List;
import org.slf4j.Logger;

/**
 * Abstraction for {@link Overrider}. Contains logic for calling the {@link
 * AbstractFieldOverrider#introspector} for this class, and the delegating to the {@link
 * AbstractFieldOverrider#callbackOverrider} for setting fields by hand.
 *
 * @param <T> Type to override on.
 * @param <SELF> Self reference for type safety when using a fluent interface api.
 */
@SuppressWarnings({"TypeParameterNamingConvention", "ClassWithoutNoArgConstructor"})
public abstract class AbstractFieldOverrider<T, SELF extends AbstractFieldOverrider<T, SELF>> implements Overrider<T> {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private Introspector introspector;

    private List<String> fieldsToSkip = emptyList();

    @SuppressWarnings("AnonymousInnerClass")
    private Overrider<T> callbackOverrider = new Overrider<T>() {
        private final Logger callbackLogger = getLogger(lookup().lookupClass());

        @Override
        public void setFields(final T t) {
            this.callbackLogger.debug("Default callbackOverrider");
        }
    };

    /**
     * Constructor.
     *
     * @param introspectorParam The instrospector to use in this class.
     */
    protected AbstractFieldOverrider(final Introspector introspectorParam) {
        super();
        this.introspector = checkNotNull(introspectorParam, "introspector cannot be null.");
        LOGGER.debug("Constructor, with introspectorParam: {}", introspectorParam);
    }

    protected abstract SELF getThis();

    /**
     * Override using a fluent api the instrospector for this class.
     *
     * @param introspectorParam The instrospector to use in this class.
     * @return this.
     */
    public SELF withIntrospector(final Introspector introspectorParam) {
        LOGGER.debug("Set introspector to: {}", introspectorParam);
        this.introspector = checkNotNull(introspectorParam, "introspector cannot be null.");
        return this.getThis();
    }

    /**
     * Set the fields to skip when performing introspection.
     *
     * @param fieldsToSkipParam Fields to skip introspection on.
     * @return this.
     */
    public SELF fieldsToSkip(final String... fieldsToSkipParam) {
        LOGGER.debug("Set fieldsToSkip to: {}", (Object) fieldsToSkipParam);
        this.fieldsToSkip = asList(fieldsToSkipParam);
        return this.getThis();
    }

    /**
     * Override using a fluent api the callbackOverrider for this class. By default this is an empty
     * overrider: {@link AbstractFieldOverrider#callbackOverrider}.
     *
     * @param callbackOvrrdParam The callback overrider to use in this class.
     * @return this.
     */
    public SELF callbackOverrider(final Overrider<T> callbackOvrrdParam) {
        LOGGER.debug("Set callbackOverrider to: {}", callbackOvrrdParam);
        this.callbackOverrider = callbackOvrrdParam;
        return this.getThis();
    }

    @Override
    public void setFields(final T t) {
        LOGGER.debug(
                "Set field on {}. With introspector: {} and fieldsToSkip: {} and callbackOverrider: {}",
                t,
                this.introspector,
                this.fieldsToSkip,
                this.callbackOverrider);
        try {
            LOGGER.debug("Perform instrospection");
            performIntrospection(t, this.introspector, this.fieldsToSkip);
        } catch (final IllegalAccessException e) {
            LOGGER.error("Exception in introspection", e);
            throw new DataBuilderException(e);
        }
        LOGGER.debug("Callback overrider {} called to set fields on {}", this.callbackOverrider, t);
        this.callbackOverrider.setFields(t);
    }

    @Override
    public String toString() {
        LOGGER.debug("toString");
        return MoreObjects.toStringHelper(this)
                .add("introspector", this.introspector)
                .add("fieldsToSkip", this.fieldsToSkip)
                .add("callbackOverrider", this.callbackOverrider)
                .toString();
    }
}
