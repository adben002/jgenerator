package com.adben.testdatabuilder.entity.analyzers.introspectors.helper.fieldvalues;

import static com.adben.testdatabuilder.entity.analyzers.introspectors.helper.fieldvalues.FieldValues.fieldValues;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Collections.unmodifiableSet;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.entity.analyzers.introspectors.helper.fieldvalues.comparator.FieldValuesComparator;
import com.google.common.base.MoreObjects;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import org.slf4j.Logger;

@SuppressWarnings("ClassNamePrefixedWithPackageName")
public final class FieldValuesHolder {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private final Set<Map<Field, Object>> treeSet = new TreeSet<>(FieldValuesComparator.INSTANCE);

  public static FieldValuesHolder fieldValuesHolder() {
    LOGGER.debug("Named constructor");
    final FieldValuesHolder fieldValuesHolder = new FieldValuesHolder();
    LOGGER.debug("Created fieldValuesHolder: {}", fieldValuesHolder);
    return fieldValuesHolder;
  }

  private FieldValuesHolder() {
    super();
    LOGGER.debug("Default constructor");
  }

  @SuppressWarnings({"FeatureEnvy", "UnqualifiedInnerClassAccess"})
  public static FieldValuesHolder createForPotentialValues(
      final Iterable<List<Entry<Field, Object>>> lists) {

    LOGGER.debug("Create FieldValuesHolder for: {}", lists);

    final FieldValuesHolder out = new FieldValuesHolder();
    for (final List<Entry<Field, Object>> a : lists) {
      out.add(fieldValues().putAll(a).getTreeMap());
    }

    LOGGER.debug("Created holder: {}", out);

    return out;
  }

  public void add(final Map<Field, Object> map) {
    LOGGER.debug("Add map: {}", map);
    this.treeSet.add(map);
    LOGGER.debug("Now tree set: {}", this.treeSet);
  }

  public Set<Map<Field, Object>> getTreeSet() {
    LOGGER.debug("Get tree set: {}", this.treeSet);
    return unmodifiableSet(this.treeSet);
  }

  @Override
  public String toString() {
    LOGGER.debug("toString");
    return MoreObjects.toStringHelper(this)
        .add("treeSet", this.treeSet)
        .toString();
  }

}
