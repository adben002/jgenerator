package com.adben.testdatabuilder.core.analyzers.generators.generator;

import static com.adben.testdatabuilder.core.analyzers.introspectors.helper.NestedIntrospectorHelper.findNestedClasses;
import static com.adben.testdatabuilder.core.helper.ReflectionHelper.getSingleGenericOrClz;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.generators.GeneratedValue;
import com.adben.testdatabuilder.core.analyzers.generators.Generator;
import com.adben.testdatabuilder.core.analyzers.generators.generatorset.DefaultGeneratorSet;
import com.adben.testdatabuilder.core.cmds.Cmd;
import com.adben.testdatabuilder.core.cmds.core.CoreCmd;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import java.lang.reflect.Type;
import java.util.List;
import org.slf4j.Logger;

/**
 * Abstraction for dealing with iterators.
 *
 * @param <T> Type of the iterator.
 */
public abstract class AbstractIteratorGenerator<T> extends AbstractGenerator<T> {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private final Supplier<Generator> generator;
    private final Function<Class<?>, Cmd> cmdGenerator;

    private int counter = 0;

    /**
     * Default constructor.
     */
    @SuppressWarnings({"WeakerAccess", "AnonymousInnerClassMayBeStatic", "AnonymousInnerClass"})
    protected AbstractIteratorGenerator() {
        this(
                new Supplier<Generator>() {
                    @SuppressWarnings("PublicMethodWithoutLogging")
                    @Override
                    public Generator get() {
                        return new DefaultGeneratorSet();
                    }
                },
                new Function<Class<?>, Cmd>() {
                    @SuppressWarnings("PublicMethodWithoutLogging")
                    @Override
                    public Cmd apply(final Class<?> input) {
                        return CoreCmd.core(input).howMany(1);
                    }
                });
        LOGGER.trace("Default constructor");
    }

    /**
     * Constructor with the generator supplier and cmd generator function.
     *
     * @param generatorParam {@link Generator} supplier, to be used to generate values.
     * @param cmdGeneratorParam {@link Cmd} to use when making a value that is not found on the
     * store.
     */
    @SuppressWarnings("WeakerAccess")
    protected AbstractIteratorGenerator(
            final Supplier<Generator> generatorParam, final Function<Class<?>, Cmd> cmdGeneratorParam) {

        super();
        this.generator = generatorParam;
        this.cmdGenerator = cmdGeneratorParam;
        LOGGER.trace("Constructor with value - generator: {}, cmdGenerator: {}", generatorParam, cmdGeneratorParam);
    }

    /**
     * Generate the value for the given type and values.
     *
     * @param type Type to generate.
     * @param values Values to put into the generated iterator.
     * @return Generated value.
     */
    protected abstract T createValues(final Type type, final Object... values);

    /**
     * Get the next value for this generator.
     *
     * <p>
     *
     * Use the {@link AbstractIteratorGenerator#generator} to get the generators and generate a value
     * for the supplied type argument of the iterator. If it returns a value we use that as the sole
     * value in the created iterator.
     *
     * <p>
     *
     * Else we use the {@link AbstractIteratorGenerator#cmdGenerator} to create a cmd that we use to
     * create an object.
     *
     * <p>
     *
     * This setup allows us to generate an Iterator inside an Iterator.
     * <p>
     * {@inheritDoc}
     */
    @Override
    protected T getNextValue(final Type type) {

        final Type typeArg = getSingleGenericOrClz(type);
        LOGGER.debug("Get next value of: {}. With type arg: {}", type, typeArg);
        final Optional<GeneratedValue> generatedValue = this.generator.get().generateValue(typeArg);
        LOGGER.debug("Generated value: {}", generatedValue);

        if (generatedValue.isPresent()) {
            final T outputValue = this.createValues(type, generatedValue.get().getValue());
            LOGGER.debug("Output iterator: {}", outputValue);
            return outputValue;
        } else {
            final T outputValue;
            if (typeArg instanceof Class) {
                final Class<?> generic = (Class<?>) typeArg;
                LOGGER.debug("Generic: {}", generic);
                final Cmd cmd = this.cmdGenerator.apply(generic);

                final List<Object> foundClasses = findNestedClasses(generic, cmd);
                LOGGER.debug("Found classes: {}", foundClasses);

                outputValue = this.createValues(type, foundClasses.get(this.counter++ % foundClasses.size()));
            } else {
                outputValue = this.createValues(type);
            }
            LOGGER.debug("Output iterator: {}", outputValue);
            return outputValue;
        }
    }

    @Override
    public String toString() {
        LOGGER.trace("toString");
        return MoreObjects.toStringHelper(this)
                .add("generator", this.generator)
                .add("cmdGenerator", this.cmdGenerator)
                .add("counter", this.counter)
                .toString();
    }
}
