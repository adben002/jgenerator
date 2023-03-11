package com.adben.testdatabuilder.core;

import static com.adben.testdatabuilder.core.store.DataBuilderStore.store;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.cmds.Cmd;
import com.adben.testdatabuilder.core.exception.DataBuilderException;
import com.adben.testdatabuilder.core.store.DataBuilderStore;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;

/**
 * The main entry point for the test-data-builder library.
 *
 * <p>
 *
 * This takes a collection of {@link Cmd}s that are used to build objects.
 *
 * <p>
 *
 * For example, this builds 50 TestBean's with override properties:
 *
 * <pre>
 *     {@code
 *
 *        createDataBuilder()
 *          .reset()
 *          .add(core(TestSubBean.class).overrides(ovrrdSet(TestSubBean.class)
 *            .all(coreFields(TestSubBean.class, new Overrider<TestSubBean>() {
 *              public void setFields(final TestSubBean o) {
 *                o.setA(toAlphaNumeric(0));
 *              }
 *            }))))
 *          .add(core(TestBean.class, 50).overrides(ovrrdSet(TestBean.class)
 *            .all(coreFields(TestBean.class, new Overrider<TestBean>() {
 *              public void setFields(final TestBean o) {
 *                o.setJ1("J1");
 *              }
 *            }))
 *            .idx(INDEX, coreFields(TestBean.class, new Overrider<TestBean>() {
 *              public void setFields(final TestBean o) {
 *                o.setJ1("OTHER J1");
 *                o.setSubBean(builtLookup(TestSubBean.class, 0).lookup());
 *              }
 *            }))))
 *          .build();
 *     }
 * </pre>
 */
public final class DataBuilder {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    /**
     * Default constructor.
     */
    private DataBuilder() {
        super();
        LOGGER.debug("Default constructor for DataBuilder.");
    }

    /**
     * Named constructor for DataBuilder.
     *
     * @return New DataBuilder.
     */
    public static DataBuilder createDataBuilder() {
        LOGGER.debug("Static named constructor.");
        final DataBuilder dataBuilder = new DataBuilder();
        LOGGER.debug("Returning: {}.", dataBuilder);
        return dataBuilder;
    }

    /**
     * Queue some {@link Cmd}s that are to be stored in {@link DataBuilderStore#commands}.
     *
     * @param cmds Cmds to later build on the {@link DataBuilder#build()}.
     * @return This instance, for fluent api.
     */
    public DataBuilder add(final Cmd... cmds) {
        final List<Cmd> cmdsList = Arrays.asList(Preconditions.checkNotNull(cmds));
        if (Iterables.any(cmdsList, Predicates.<Cmd>isNull())) {
            throw new DataBuilderException("cmds has a null cmd: " + cmdsList);
        }
        LOGGER.debug("Add cmds: {}.", cmdsList);
        store().commands.addAll(cmdsList);
        LOGGER.debug("All cmds are now: {}.", store().commands);
        return this;
    }

    /**
     * Loop over the {@link Cmd}s in {@link DataBuilderStore#commands}, and build the {@link Cmd} with
     * {@link Cmd#build()}.
     *
     * @return This instance, for fluent api.
     */
    public DataBuilder build() {
        final Stopwatch wholeBuildStopwatch = Stopwatch.createStarted();
        // Do a simple for loop to avoid a concurrent modification error, where a cmd may add onto the
        // commands list.
        for (int i = 0; i < store().commands.size(); i++) {
            final Cmd cmd = store().commands.get(i);
            LOGGER.debug("Build the cmd: {}.", cmd);
            final Stopwatch buildStopwatch = Stopwatch.createStarted();
            cmd.build(); // Run the build for the Cmd
            LOGGER.debug("Finished building cmd: {}. In {}ms.", cmd, buildStopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
        LOGGER.debug("Finished building the cmds. In {}ms.", wholeBuildStopwatch.elapsed(TimeUnit.MILLISECONDS));
        return this;
    }

    /**
     * Get a list of entities that have been built and stored in {@link
     * DataBuilderStore#builtObjects}.
     *
     * @param clz Class to get entities for.
     * @param <X> Type of entities to get, this is the clz type.
     * @return List of built objects of the given type.
     */
    @SuppressWarnings({"MethodMayBeStatic"})
    public <X> List<X> getAllByClz(final Class<X> clz) {
        LOGGER.debug("Get clz: {}.", clz);
        final List<X> builtObjects = (List<X>) store().builtObjects.get(clz);
        LOGGER.debug("Return clzs: {}.", clz);
        return builtObjects;
    }

    /**
     * Essentially a wrapper around {@link DataBuilder#getAllByClz(Class)}, that gets the specified
     * index.
     *
     * @param clz Class to get entities for.
     * @param number Index of the list to get.
     * @param <X> Type of entities to get, this is the clz type.
     * @return Built object of given type and given index in list.
     */
    public <X> X getByClz(final Class<X> clz, final int number) {
        LOGGER.debug("Get clz: {}, with index: {}.", clz, number);
        final X builtClz = this.getAllByClz(clz).get(number);
        LOGGER.debug("Return clz: {}.", builtClz);
        return builtClz;
    }

    /**
     * @return Get all {@link DataBuilderStore#builtObjects}.
     */
    @SuppressWarnings("MethodMayBeStatic")
    public Multimap<Class<?>, Object> getEntities() {
        LOGGER.debug("Get built objects.");
        final Multimap<Class<?>, Object> builtObjects = store().builtObjects;
        LOGGER.debug("Return built objects: {}.", builtObjects);
        return builtObjects;
    }

    /**
     * Reset our data. See {@link DataBuilderStore#reset()}
     *
     * @return This instance, for fluent api.
     */
    public DataBuilder reset() {
        LOGGER.debug("Reset the builder store.");
        store().reset();
        LOGGER.debug("Finished resetting the builder store.");
        return this;
    }
}
