package com.adben.testdatabuilder.core.cmds.common;

import static com.adben.testdatabuilder.core.override.overriderset.DefaultOverriderSet.ovrrdSet;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.cmds.Cmd;
import com.adben.testdatabuilder.core.override.overriderset.OverriderSet;
import com.google.common.base.MoreObjects;
import org.slf4j.Logger;

/**
 * Abstraction of {@link Cmd} for creating several objects for the {@link AbstractCmd#howMany}.
 */
@SuppressWarnings("TypeParameterNamingConvention")
public abstract class AbstractCmd<T, SELF extends AbstractCmd<T, SELF>> implements Cmd {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    /**
     * How many of the class to createDataBuilder, default is <b>#1</b>.
     */
    private int howMany = 1;

    private OverriderSet<? super T> overriderSet = ovrrdSet(Object.class);

    @SuppressWarnings("WeakerAccess")
    protected AbstractCmd() {
        super();
        LOGGER.debug("Default constructor");
    }

    public SELF howMany(final int howManyParam) {
        LOGGER.debug("Set howMany: {}", howManyParam);
        this.howMany = howManyParam;
        return this.getThis();
    }

    public SELF overrides(final OverriderSet<? super T> overriderSetParam) {
        LOGGER.debug("Set overriderSet: {}", overriderSetParam);
        this.overriderSet = overriderSetParam;
        return this.getThis();
    }

    public int getHowMany() {
        return this.howMany;
    }

    protected OverriderSet<? super T> getOverriderSet() {
        return this.overriderSet;
    }

    @SuppressWarnings("unchecked")
    protected SELF getThis() {
        return (SELF) this;
    }

    @Override
    public String toString() {
        LOGGER.debug("toString");
        return MoreObjects.toStringHelper(this)
                .add("howMany", this.howMany)
                .add("overriderSet", this.overriderSet)
                .toString();
    }
}
