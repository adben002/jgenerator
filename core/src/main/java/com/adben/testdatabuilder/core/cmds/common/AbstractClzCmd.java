package com.adben.testdatabuilder.core.cmds.common;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.google.common.base.MoreObjects;
import java.util.Collections;
import org.slf4j.Logger;

/**
 * Abstraction for a {@link com.adben.testdatabuilder.core.cmds.Cmd Cmd} that requires a {@link
 * Class}.
 */
@SuppressWarnings("TypeParameterNamingConvention")
public abstract class AbstractClzCmd<T, SELF extends AbstractClzCmd<T, SELF>> extends AbstractCmd<T, SELF> {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    /**
     * Class to createDataBuilder for the Cmd
     */
    @SuppressWarnings("InstanceVariableNamingConvention")
    private Class<T> clz = null;

    protected AbstractClzCmd() {
        super();
        LOGGER.debug("Default constructor");
    }

    public SELF clz(final Class<T> clzParam) {
        LOGGER.debug("Set class: {}", clzParam);
        this.clz = clzParam;
        return this.getThis();
    }

    protected Class<T> getClz() {
        return this.clz;
    }

    @Override
    public Iterable<Class<?>> getClzs() {
        LOGGER.debug("Get class: {}", this.clz);
        return Collections.<Class<?>>singletonList(this.clz);
    }

    @Override
    public String toString() {
        LOGGER.trace("toString");
        return MoreObjects.toStringHelper(this).add("clz", this.clz).toString();
    }
}
