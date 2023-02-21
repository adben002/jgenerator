package com.adben.testdatabuilder.entity.analyzers.introspectors.helper.fieldvalues.comparator;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.exception.DataBuilderException;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;

@SuppressWarnings("EnumClass")
public enum FieldValuesComparator implements Comparator<Map<Field, Object>> {

  INSTANCE;

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  @SuppressWarnings("ObjectInstantiationInEqualsHashCode")
  @Override
  public int compare(final Map<Field, Object> o1, final Map<Field, Object> o2) {
    LOGGER.debug("Compare field values: {}, {}", o1, o2);

    // Assume that the field values are the same
    if (!o1.keySet().equals(o2.keySet())) {
      throw new DataBuilderException("Key sets are not equal, expecting the same.");
    }

    //noinspection UnqualifiedInnerClassAccess,ObjectInstantiationInEqualsHashCode
    for (final Entry<Field, Object> entry : o1.entrySet()) {
      final int compare = ObjectComparator.INSTANCE
          .compare(entry.getValue(), o2.get(entry.getKey()));

      LOGGER.debug("Compare {}, {}. Result: {}", entry.getValue(), o2.get(entry.getKey()), compare);

      if (compare != 0) {
        LOGGER.debug("Values are different");
        return compare;
      }
    }

    LOGGER.debug("Equal maps");

    return 0;
  }

}
