package com.adben.testdatabuilder.core.store;

import static com.google.common.collect.Iterables.contains;
import static com.google.common.collect.Multimaps.synchronizedMultimap;
import static java.util.Collections.synchronizedList;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.DataBuilder;
import com.adben.testdatabuilder.core.cmds.Cmd;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.lang.invoke.MethodHandles;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;

/**
 * Store for the {@link DataBuilder}. This contains the {@link DataBuilderStore#commands} that are
 * being processed by the {@link DataBuilder}. Also the {@link DataBuilderStore#builtObjects} that
 * have been created by the {@link DataBuilder}.
 *
 * <p>
 *
 * This class utilises {@link ThreadLocal} so that the store is specific to the thread. The {@link
 * ThreadLocal} is useful because it allows anything to access the data from anywhere within the
 * code.
 */
public final class DataBuilderStore {

    private static final Logger LOGGER = getLogger(MethodHandles.lookup().lookupClass());

    /**
     * {@link ThreadLocal} store containing an instance of {@link DataBuilderStore}, each {@link
     * DataBuilderStore} is specific to the thread as per the implementation of {@link ThreadLocal}.
     */
    @SuppressWarnings("AnonymousInnerClass")
    private static final ThreadLocal<DataBuilderStore> STORE = new ThreadLocal<DataBuilderStore>() {

        private final Logger logger = getLogger(MethodHandles.lookup().lookupClass());

        @Override
        protected DataBuilderStore initialValue() {
            this.logger.debug("Initiate ThreadLocal for Store.");
            //noinspection SyntheticAccessorCall
            return new DataBuilderStore();
        }
    };

    /**
     * Commands that are being processed by the {@link DataBuilder}.
     */
    @SuppressWarnings("PublicField")
    public final List<Cmd> commands = synchronizedList(new LinkedList<Cmd>());

    /**
     * Objects that have been built via the {@link DataBuilder}.
     */
    @SuppressWarnings("PublicField")
    public final Multimap<Class<?>, Object> builtObjects =
            synchronizedMultimap(ArrayListMultimap.<Class<?>, Object>create());

    /**
     * Default constructor.
     */
    private DataBuilderStore() {
        // Nothing
        super();
    }

    /**
     * Reset our data store.
     */
    public void reset() {
        LOGGER.debug("Call to reset the store.");
        this.commands.clear();
        this.builtObjects.clear();
        LOGGER.debug("Store reset. {}", this);
    }

    /**
     * Useful helper method to get the {@link DataBuilderStore#STORE} from.
     *
     * @return The DataBuilderStore for the thread.
     */
    public static DataBuilderStore store() {
        LOGGER.debug("Get the store for the current thread.");
        final DataBuilderStore dataBuilderStore = STORE.get();
        LOGGER.debug("Return store: {}", dataBuilderStore);
        return dataBuilderStore;
    }

    public static boolean isGoingToMakeOneOfThese(final Class<?> clz) {
        for (final Cmd cmd : store().commands) {
            if (contains(cmd.getClzs(), clz)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        LOGGER.debug("toString");
        return MoreObjects.toStringHelper(this)
                .add("commands", this.commands)
                .add("builtObjects", this.builtObjects)
                .toString();
    }
}
