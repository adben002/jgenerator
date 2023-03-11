package com.adben.testdatabuilder.core.helper;

import static com.adben.testdatabuilder.core.store.DataBuilderStore.store;
import static com.google.common.collect.Iterables.contains;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.cmds.Cmd;
import com.adben.testdatabuilder.core.cmds.common.AbstractCmd;
import com.adben.testdatabuilder.core.exception.DataBuilderException;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;

/**
 * Core helper for the {@link com.adben.testdatabuilder.core.analyzers.generators.Generator}
 * implementations.
 */
public final class ReflectionHelper {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private ReflectionHelper() {
        super();
        LOGGER.trace("Default constructor");
    }

    /**
     * Make a new instance using the default constructor of the supplied {@link Class}.
     *
     * @param mainClz The class to createDataBuilder.
     * @param <T> Type of object to createDataBuilder.
     * @return Created instance using default constructor of given class.
     */
    public static <T> T makeInstance(final Class<T> mainClz) {
        LOGGER.debug("Make a new instance of: {}", mainClz);
        try {
            return mainClz.getConstructor().newInstance();
        } catch (InstantiationException
                | IllegalAccessException
                | NoSuchMethodException
                | InvocationTargetException e) {
            LOGGER.error("Exception in making new instance", e);
            throw new DataBuilderException(e);
        }
    }

    /**
     * Get the class of the supplied type.
     *
     * @param clz Type to get the underlying class for.
     * @return The underlying class for the supplied type.
     */
    public static Class<?> getClz(final Type clz) {
        if (clz instanceof ParameterizedType) {
            final Class<?> output = (Class<?>) ((ParameterizedType) clz).getRawType();
            LOGGER.debug("Get class for: {}, return: {}", clz, output);
            return output;
        }
        LOGGER.debug("Get class for: {}, return: {}", clz, clz);
        return (Class<?>) clz;
    }

    /**
     * Get the single parameter of the supplied type or the original type.
     *
     * <p>
     *
     * See {@link ReflectionHelper#getSingleGeneric(Type)} then {@link ReflectionHelper#getClz(Type)}
     *
     * @param clz Type to get single generic class parameter or class for.
     * @return Single class parameter for the class of the supplied class.
     */
    public static Type getSingleGenericOrClz(final Type clz) {
        final Optional<Type> singleGeneric = getSingleGeneric(clz);
        if (singleGeneric.isPresent()) {
            LOGGER.debug("Get single generic for: {}, return: {}", clz, singleGeneric.get());
            return singleGeneric.get();
        }
        final Class<?> output = getClz(clz);
        LOGGER.debug("Get single generic for for: {}, return: {}", clz, output);
        return output;
    }

    /**
     * Get the single parameter of the supplied type. If there is a single parameter for the type it
     * is returned, if there are none or more than one an {@link Optional#absent()} is returned.
     *
     * @param clz Type to get single generic class parameter for.
     * @return Single class parameter for the class.
     */
    @SuppressWarnings("WeakerAccess")
    public static Optional<Type> getSingleGeneric(final Type clz) {
        if (clz instanceof ParameterizedType) {
            final Type[] args = ((ParameterizedType) clz).getActualTypeArguments();
            if (args.length == 1) {
                LOGGER.debug("Get single generic for for: {}, return: {}", clz, args[0]);
                return Optional.of(args[0]);
            }
        }
        LOGGER.debug("No single parameter found for type: {}", clz);
        return Optional.absent();
    }

    /**
     * @param mainClz The class to find the all fields for.
     * @return All fields on the class, including fields on super classes.
     */
    public static Iterable<Field> getAllFields(final Class<?> mainClz) {
        LOGGER.debug("Get all fields including the superclasses for: {}", mainClz);
        final List<Field> fields = Lists.newArrayList();
        for (final Class<?> clz : getAllSuperclasses(mainClz)) {
            fields.addAll(Lists.newArrayList(clz.getDeclaredFields()));
        }
        LOGGER.debug("Found all fields for {}, these are: {}", mainClz, fields);
        return fields;
    }

    /**
     * Get the first generic parameter of the supplied field.
     *
     * @param field Field to get the first generic for.
     * @return First generic class.
     */
    public static Class<?> getFirstGeneric(final Field field) {
        LOGGER.debug("Get first generic for: {}", field);
        final Class<?> firstGeneric =
                (Class<?>) ((ParameterizedType) (field).getGenericType()).getActualTypeArguments()[0];
        LOGGER.debug("First generic for {}: {}", field, firstGeneric);
        return firstGeneric;
    }

    /**
     * Get the field value for the given field and object.
     *
     * @param field Field to get the value for.
     * @param obj Object to get the field value for.
     * @return Value of the field on the given object.
     */
    public static Object getFieldVal(final Field field, final Object obj) {
        try {
            field.setAccessible(true);
            return field.get(obj);
        } catch (final IllegalAccessException e) {
            LOGGER.error("Exception in getting field value", e);
            throw new DataBuilderException(e);
        }
    }

    public static Object setFieldVal(final Object obj, final Field field, final Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
            return field.get(obj);
        } catch (final IllegalAccessException e) {
            LOGGER.error("Exception in getting field value", e);
            throw new DataBuilderException(e);
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static Iterable<Class<?>> getAllSuperclasses(final Class<?> baseClz) {
        LOGGER.debug("Get all superclasses for: {}", baseClz);
        //noinspection CollectionWithoutInitialCapacity
        final Collection<Class<?>> superclasses = new ArrayList<>();
        for (Class<?> clz = baseClz; clz != null; clz = clz.getSuperclass()) {
            superclasses.add(clz);
        }
        LOGGER.debug("All superclasses for {} are: {}", baseClz, superclasses);
        return superclasses;
    }

    @SuppressWarnings("CastToConcreteClass")
    public static int countHowManyClzInStoredCmds(final Class<?> clz) {
        LOGGER.debug("Count How Many Clz In Stored Cmds: {}", clz);
        int counter = 0;
        for (final Cmd cmd : store().commands) {
            if (AbstractCmd.class.isAssignableFrom(cmd.getClass())) {
                final AbstractCmd<?, ?> abstractCmd = (AbstractCmd<?, ?>) cmd;
                if (contains(abstractCmd.getClzs(), clz)) {
                    counter += abstractCmd.getHowMany();
                }
            }
        }
        return counter;
    }
}
