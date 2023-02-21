package com.adben.testdatabuilder.entity.analyzers.introspectors.helper.fieldvalues.comparator;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Collections.frequency;
import static org.slf4j.LoggerFactory.getLogger;

import com.google.common.base.MoreObjects;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;

@SuppressWarnings({"ComparatorNotSerializable", "ClassWithoutNoArgConstructor"})
public class FieldValueFrequencyComparator implements Comparator<Map<Field, Object>> {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private final Set<Map<Field, Object>> currentObjPkValues;

  public FieldValueFrequencyComparator(final Set<Map<Field, Object>> currObjPkValuesParam) {
    super();
    this.currentObjPkValues = new HashSet<>(currObjPkValuesParam);
    LOGGER.debug("Constructor. currObjPkValuesParam: {}", currObjPkValuesParam);
  }

  @Override
  public int compare(final Map<Field, Object> o1, final Map<Field, Object> o2) {

    LOGGER.debug("Compare o1, o2: {}, {}", o1, o2);

    final int o1Frequency = frequency(this.currentObjPkValues, o1);
    final int o2Frequency = frequency(this.currentObjPkValues, o2);

    //noinspection ObjectInstantiationInEqualsHashCode
    LOGGER.debug("Compare (values: {}) o1Frequency: {}, o2Frequency: {}",
        this.currentObjPkValues,
        o1Frequency,
        o2Frequency);

    final int compare = Integer.compare(o1Frequency, o2Frequency);

    if (compare == 0) {
      LOGGER.debug("Equal frequency. Object compare: {}, {}", o1, o2);
      return ObjectComparator.INSTANCE.compare(o1, o2);
    }

    LOGGER.debug("Frequency compare: {}, {}", o1, o2);

    return compare;
  }

  @Override
  public String toString() {
    LOGGER.debug("toString");
    return MoreObjects.toStringHelper(this)
        .add("currentObjPkValues", this.currentObjPkValues)
        .toString();
  }

}
