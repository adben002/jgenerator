package com.adben.testdatabuilder.core.analyzers.generators;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;

@SuppressWarnings("ClassWithoutNoArgConstructor")
public final class GeneratedValue {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private final boolean skipSetting;

  private final Object value;

  private GeneratedValue(final boolean skipSettingAttr, final Object valueAttr) {
    super();
    LOGGER.debug("GeneratedValue default constructor.");
    this.skipSetting = skipSettingAttr;
    this.value = Preconditions
        .checkNotNull(valueAttr, "Use GeneratedValue.doNotSetGeneratedValue()");
  }

  public static GeneratedValue doNotSetGeneratedValue() {
    LOGGER.debug("Do not use the GeneratedValue to set.");
    final GeneratedValue doNotSetValue = new GeneratedValue(true, new Object());
    LOGGER.debug("Returned doNotSetGeneratedValue GeneratedValue: {}.", doNotSetValue);
    return doNotSetValue;
  }

  public static GeneratedValue generatedValue(final Object value) {
    LOGGER.debug("GeneratedValue with generatedValue: {}.", value);
    final GeneratedValue setValue = new GeneratedValue(false, value);
    LOGGER.debug("Returned set GeneratedValue: {}.", setValue);
    return setValue;
  }

  public boolean isSkipSetting() {
    return this.skipSetting;
  }

  public Object getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    LOGGER.debug("toString()");
    return MoreObjects.toStringHelper(this)
        .add("skipSetting", this.skipSetting)
        .add("generatedValue", this.value)
        .toString();
  }

}
