package com.adben.testdatabuilder.core.cmds.core;

import static com.adben.testdatabuilder.core.helper.ReflectionHelper.makeInstance;
import static com.adben.testdatabuilder.core.override.overriders.CoreOverrider.coreOverrider;
import static com.adben.testdatabuilder.core.override.overriderset.DefaultOverriderSet.ovrrdSet;
import static com.adben.testdatabuilder.core.store.DataBuilderStore.store;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.cmds.Cmd;
import com.adben.testdatabuilder.core.cmds.common.AbstractClzCmd;
import com.adben.testdatabuilder.core.override.overriders.Overrider;
import org.slf4j.Logger;

/**
 * Core {@link com.adben.testdatabuilder.core.cmds.Cmd Cmd}.
 *
 * <p>
 *
 * Example usage:
 * <pre>
 *   {@code
 *        createDataBuilder()
 *            .reset()
 *            .add(core(TestSubBean.class).overrides(ovrrdSet(TestSubBean.class)
 *                .all(coreFields(TestSubBean.class, new Overrider<TestSubBean>() {
 *                  public void setFields(final TestSubBean o) {
 *                    o.setA(AlphaNumericHelper.toAlphaNumeric(0));
 *                  }
 *            }))))
 *            .build();
 *   }
 * </pre>
 */
@SuppressWarnings({"ClassNamePrefixedWithPackageName", "ClassNamingConvention", "ClassTooDeepInInheritanceTree"})
public class CoreCmd<T> extends AbstractClzCmd<T, CoreCmd<T>> {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    @SuppressWarnings("WeakerAccess")
    protected CoreCmd() {
        super();
        LOGGER.debug("Default constructor");
    }

    /**
     * Named constructor for {@link CoreCmd#CoreCmd()}.
     *
     * @param <T> Type of the class this is creating a {@link Cmd} for.
     * @return Newly constructed default CoreCmd.
     */
    public static <T> CoreCmd<T> core() {
        final CoreCmd<T> cmd = new CoreCmd<>();
        cmd.overrides(ovrrdSet(Object.class).all(coreOverrider(Object.class)));
        LOGGER.debug("New CoreCmd: {}", cmd);
        return cmd;
    }

    /**
     * @param <T> Type of the class this is creating a {@link Cmd} for.
     * @param clz Class for the CoreCmd.
     * @return Newly constructed CoreCmd with class.
     */
    public static <T> CoreCmd<T> core(final Class<T> clz) {
        LOGGER.debug("New CoreCmd with clz: {}", clz);
        return CoreCmd.<T>core().clz(clz);
    }

    /**
     * @param <T> Type of the class this is creating a {@link Cmd} for.
     * @param clz Class for the CoreCmd.
     * @param howMany How many of the given clz to create.
     * @return Newly constructed CoreCmd with class.
     */
    public static <T> CoreCmd<T> core(final Class<T> clz, final int howMany) {
        LOGGER.debug("New CoreCmd with clz: {}, howMany: {}", clz, howMany);
        return core(clz).howMany(howMany);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     *
     * Implementation. Requires that the {@link CoreCmd#clz} be set.
     */
    @Override
    public void build() {
        LOGGER.debug(
                "Build with - Clz: {}. HowMany: {}. DefaultOverriderSet: {}",
                this.getClz(),
                this.getHowMany(),
                this.getOverriderSet());

        for (int i = 0; i < this.getHowMany(); i++) {
            final T obj = makeInstance(this.getClz());
            final Overrider<? super T> overrider = this.getOverriderSet().get(i);
            LOGGER.debug("Use overrider: {}", overrider);
            overrider.setFields(obj);
            LOGGER.debug("Add object: {}", obj);
            store().builtObjects.put(this.getClz(), obj);
        }
    }
}
