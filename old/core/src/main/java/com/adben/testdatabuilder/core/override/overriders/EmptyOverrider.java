package com.adben.testdatabuilder.core.override.overriders;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.google.common.base.MoreObjects;
import org.slf4j.Logger;

/**
 * EmptyOverrider, which uses {@link EmptyOverrider}.
 *
 * <p>
 *
 * Example usage:
 * <pre>
 *   {@code
 *          createDataBuilder()
 *              .add(buildCmd(TEST_BEAN_BUILDER_INSTANCE, 50).overrides(ovrrdSet(TestBean.class)
 *                  .all(emptyFields(TestBean.class, new Overrider<TestBean>() {
 *                    public void setFields(final TestBean testBean) {
 *                      testBean.setJ1(J1_VALUE);
 *                      testBean.getSubBean().setA(SUBBEAN_A);
 *                    }
 *                  }))))
 *              .build();
 *   }
 * </pre>
 *
 * @param <T> Type of the {@link Overrider}.
 */
@SuppressWarnings("ClassWithoutNoArgConstructor")
public final class EmptyOverrider<T> implements Overrider<T> {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  @SuppressWarnings("AnonymousInnerClass")
  private Overrider<T> callbackOverrider = new Overrider<T>() {
    private final Logger callbackLogger = getLogger(lookup().lookupClass());

    @Override
    public void setFields(final T t) {
      this.callbackLogger.debug("Default callbackOverrider");
    }
  };

  private EmptyOverrider(final Class<T> t) {
    super();
    LOGGER.debug("Constructor. Class: {}", t);
  }

  /**
   * Create an EmptyOverrider object. This only calls the {@link EmptyOverrider#callbackOverrider}.
   *
   * @param t Class that the overrider is working for. This is only for type enforcement.
   * @param <T> type of the clz.
   * @return New EmptyOverrider.
   */
  public static <T> EmptyOverrider<T> emptyOverrider(final Class<T> t) {
    LOGGER.debug("Named constructor. Class: {}", t);
    return new EmptyOverrider<>(t);
  }

  /**
   * Create an EmptyOverrider object. This only calls the {@link EmptyOverrider#callbackOverrider}.
   *
   * @param t Class that the overrider is working for. This is only for type enforcement.
   * @param callbackOverrider Overrider as a callback.
   * @param <T> type of the clz.
   * @return New EmptyOverrider.
   */
  public static <T> EmptyOverrider<T> emptyFields(final Class<T> t,
      final Overrider<T> callbackOverrider) {

    LOGGER.debug("Named constructor. Class: {}. callbackOverrider: {}", t, callbackOverrider);
    return new EmptyOverrider<>(t).callbackOverrider(callbackOverrider);
  }

  /**
   * Override using a fluent api the callbackOverrider for this class. By default this is an empty
   * overrider: {@link EmptyOverrider#callbackOverrider}.
   *
   * @param callbackOvrrdParam The callback overrider to use in this class.
   * @return New EmptyOverrider.
   */
  @SuppressWarnings("WeakerAccess")
  public EmptyOverrider<T> callbackOverrider(final Overrider<T> callbackOvrrdParam) {
    LOGGER.debug("Set callbackOverrider to: {}", callbackOvrrdParam);
    this.callbackOverrider = callbackOvrrdParam;
    return this;
  }

  @Override
  public void setFields(final T t) {
    LOGGER.debug("Set field on {}. callbackOverrider: {}", t, this.callbackOverrider);
    this.callbackOverrider.setFields(t);
  }

  @Override
  public String toString() {
    LOGGER.debug("toString");
    return MoreObjects.toStringHelper(this)
        .add("callbackOverrider", this.callbackOverrider)
        .toString();
  }

}
