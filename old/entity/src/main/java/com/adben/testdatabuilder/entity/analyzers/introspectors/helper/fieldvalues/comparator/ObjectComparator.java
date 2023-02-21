package com.adben.testdatabuilder.entity.analyzers.introspectors.helper.fieldvalues.comparator;

import static java.lang.System.identityHashCode;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Comparator;
import org.slf4j.Logger;

@SuppressWarnings("EnumClass")
public enum ObjectComparator implements Comparator<Object> {

  INSTANCE;

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  @SuppressWarnings({"unchecked", "rawtypes", "ObjectInstantiationInEqualsHashCode"})
  @Override
  public int compare(final Object o1, final Object o2) {

    LOGGER.debug("Object comparison: {}, {}", o1, o2);

    if (o1 instanceof Comparable) {
      LOGGER.debug("Objects are comparable: {}, {}", o1, o2);

      final int comp = ((Comparable) o1).compareTo(o2);

      LOGGER.debug("Result: {}", comp);

      return comp;
    }

    LOGGER.debug("Do hash comparison: {}, {}", o1, o2);

    final int hashComp = Integer.compare(o1.hashCode(), o2.hashCode());

    LOGGER.debug("Hash comparison: {}", hashComp);
    // Check hash collision
    if (hashComp == 0) {

      LOGGER.debug("Check hash collision");

      if (o1.equals(o2)) {
        LOGGER.debug("Object are equal: {}, {}", o1, o2);
        return 0;
      }

      final int compare = Integer.compare(identityHashCode(o1), identityHashCode(o2));

      LOGGER.debug("Comaprison of identity: {}", compare);

      return compare;
    }

    LOGGER.debug("Hash compare result: {}", hashComp);

    return hashComp;
  }

}
