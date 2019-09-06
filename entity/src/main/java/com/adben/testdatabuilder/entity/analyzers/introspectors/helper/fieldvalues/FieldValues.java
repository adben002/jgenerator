package com.adben.testdatabuilder.entity.analyzers.introspectors.helper.fieldvalues;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Collections.unmodifiableMap;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.entity.analyzers.introspectors.helper.fieldvalues.comparator.ObjectComparator;
import com.google.common.base.MoreObjects;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.slf4j.Logger;

@SuppressWarnings("ClassNamePrefixedWithPackageName")
public final class FieldValues {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private final Map<Field, Object> treeMap = new TreeMap<>(ObjectComparator.INSTANCE);

  public static FieldValues fieldValues() {
    LOGGER.debug("Named constructor");
    final FieldValues fieldValues = new FieldValues();
    LOGGER.debug("Created fieldValues: {}", fieldValues);
    return fieldValues;
  }

  private FieldValues() {
    super();
    LOGGER.debug("Default constructor");
  }

  @SuppressWarnings({"UnqualifiedInnerClassAccess", "NonBooleanMethodNameMayNotStartWithQuestion"})
  public FieldValues putAll(final Iterable<Entry<Field, Object>> entriesToAdd) {
    LOGGER.debug("Entries to add: {}", entriesToAdd);
    for (final Entry<Field, Object> entry : entriesToAdd) {
      this.treeMap.put(entry.getKey(), entry.getValue());
    }
    LOGGER.debug("Map now looks like: {}", this.treeMap);
    return this;
  }

  @SuppressWarnings("NonBooleanMethodNameMayNotStartWithQuestion")
  public void put(final Field key, final Object value) {
    LOGGER.debug("Put field: {}, value: {}", key, value);
    this.treeMap.put(key, value);
  }

  public Map<Field, Object> getTreeMap() {
    LOGGER.debug("Get tree map: {}", this.treeMap);
    return unmodifiableMap(this.treeMap);
  }

  @Override
  public String toString() {
    LOGGER.debug("toString");
    return MoreObjects.toStringHelper(this)
        .add("treeMap", this.treeMap)
        .toString();
  }

}
