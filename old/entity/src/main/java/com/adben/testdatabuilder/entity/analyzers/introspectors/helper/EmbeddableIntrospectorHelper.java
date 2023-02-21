package com.adben.testdatabuilder.entity.analyzers.introspectors.helper;

import static com.adben.testdatabuilder.core.analyzers.introspectors.helper.IntrospectorHelper.performIntrospection;
import static com.adben.testdatabuilder.core.analyzers.introspectors.helper.NestedIntrospectorHelper.findNestedClasses;
import static com.adben.testdatabuilder.core.helper.ReflectionHelper.getFieldVal;
import static com.adben.testdatabuilder.core.helper.ReflectionHelper.makeInstance;
import static com.adben.testdatabuilder.core.helper.ReflectionHelper.setFieldVal;
import static com.adben.testdatabuilder.core.store.DataBuilderStore.store;
import static com.adben.testdatabuilder.entity.analyzers.introspectors.helper.EntityIntrospectorHelper.createPkMap;
import static com.adben.testdatabuilder.entity.analyzers.introspectors.helper.EntityIntrospectorHelper.getIdVal;
import static com.adben.testdatabuilder.entity.analyzers.introspectors.helper.fieldvalues.FieldValues.fieldValues;
import static com.adben.testdatabuilder.entity.analyzers.introspectors.helper.fieldvalues.FieldValuesHolder.createForPotentialValues;
import static com.adben.testdatabuilder.entity.analyzers.introspectors.helper.fieldvalues.FieldValuesHolder.fieldValuesHolder;
import static com.google.common.collect.Iterables.size;
import static com.google.common.collect.Sets.cartesianProduct;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Collections.sort;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.introspectors.Introspector;
import com.adben.testdatabuilder.core.cmds.Cmd;
import com.adben.testdatabuilder.core.exception.DataBuilderException;
import com.adben.testdatabuilder.entity.analyzers.introspectors.helper.fieldvalues.FieldValues;
import com.adben.testdatabuilder.entity.analyzers.introspectors.helper.fieldvalues.FieldValuesHolder;
import com.adben.testdatabuilder.entity.analyzers.introspectors.helper.fieldvalues.comparator.FieldValueFrequencyComparator;
import com.google.common.base.Function;
import com.google.common.base.Supplier;
import java.lang.reflect.Field;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.slf4j.Logger;

public final class EmbeddableIntrospectorHelper {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private EmbeddableIntrospectorHelper() {
    super();
    LOGGER.debug("Default constructor");
  }

  public static Object makeEmbeddableObject(final Field field,
      final Object obj,
      final Function<Class<?>, Cmd> cmdGenerator,
      final Supplier<Introspector> introspectorGen)
      throws IllegalAccessException {

    LOGGER.debug("field: {}, obj: {}, cmdGenerator: {}, introspectorGen: {}",
        field,
        obj,
        cmdGenerator,
        introspectorGen);

    final Supplier<Object> newInstanceSupplier = createNewInstanceSupplier(field,
        getValuesToUse(field, obj, cmdGenerator));

    LOGGER.debug("newInstanceSupplier: {}", newInstanceSupplier);

    final Object genEmbeddableObj = generateEmbeddableObject(field,
        obj,
        newInstanceSupplier,
        introspectorGen.get());

    LOGGER.debug("generate embeddable object: {}", genEmbeddableObj);

    return genEmbeddableObj;
  }

  @SuppressWarnings("WeakerAccess")
  public static Supplier<Object> createNewInstanceSupplier(final Field field,
      final Map<Field, Object> valToUse) {

    //noinspection AnonymousInnerClass
    final Supplier<Object> newInstanceCallback = new Supplier<Object>() {
      @Override
      public Object get() {
        final Object instanceCalcValues = createInstanceWithCalcValues(field, valToUse);
        //noinspection SyntheticAccessorCall
        LOGGER.debug("newInstanceCallback creates: {}", instanceCalcValues);
        return instanceCalcValues;
      }
    };

    LOGGER.debug("newInstanceCallback: {}", newInstanceCallback);

    return newInstanceCallback;
  }

  @SuppressWarnings("WeakerAccess")
  public static Object generateEmbeddableObject(final Field field,
      final Object obj,
      final Supplier<Object> newInstanceSupplier,
      final Introspector introspector)
      throws IllegalAccessException {

    LOGGER.debug("Generate object field {}, with newInstanceSupplier: {} and introspector: {}",
        field,
        newInstanceSupplier,
        introspector);

    final Collection<Object> createdObjects = new HashSet<>(0);

    while (true) {

      final Object newInstance = newInstanceSupplier.get();
      final Collection<String> fieldsToSkip = getPkFieldNames(field, obj);

      final Object embeddableObj = performIntrospection(newInstance, introspector, fieldsToSkip);

      LOGGER.debug("Possible embeddableObj: {}", embeddableObj);

      final boolean isUniqueEmbedObj = !getCurrentEmbeddables(field, obj).contains(embeddableObj);

      LOGGER.debug("Unique embeddableObj {}: {}", embeddableObj, isUniqueEmbedObj);

      if (isUniqueEmbedObj) {
        LOGGER.debug("Use embeddableObj: {}", embeddableObj);
        return embeddableObj;
      }

      LOGGER.debug("Check whether all introspections are exhausted.");
      if (createdObjects.contains(embeddableObj)) {
        LOGGER.debug("All created objects: {}", createdObjects);
        throw new DataBuilderException("Could not generate unique pk for: " + field);
      }

      LOGGER.debug("Add created Object: {}", embeddableObj);
      createdObjects.add(embeddableObj);
    }
  }

  @SuppressWarnings("WeakerAccess")
  public static Object createInstanceWithCalcValues(final Field field,
      final Map<Field, Object> valToUse) {

    LOGGER.debug("Create instance field {} with calculated values: {}", field, valToUse);

    final Object newInstance = makeInstance(field.getType());

    //noinspection UnnecessarilyQualifiedInnerClassAccess
    for (final Map.Entry<Field, Object> fieldValue : valToUse.entrySet()) {
      LOGGER.debug("Set field value {} on {}", fieldValue, newInstance);
      setFieldVal(newInstance, fieldValue.getKey(), fieldValue.getValue());
    }

    LOGGER.debug("New instance now looks like: {}", newInstance);

    return newInstance;
  }

  @SuppressWarnings("WeakerAccess")
  public static Collection<String> getPkFieldNames(final Field field, final Object obj) {

    LOGGER.debug("Get names of fields that are expected to be generated: {}", field);

    final Collection<Field> fields = createPkMap(obj.getClass(), field.getType()).keySet();

    final Collection<String> fieldNames = new ArrayList<>(fields.size());

    for (final Field fld : fields) {
      fieldNames.add(fld.getName());
    }

    LOGGER.debug("Field names for field {}: {}", field, fieldNames);

    return fieldNames;
  }

  @SuppressWarnings("WeakerAccess")
  public static Collection<Object> getCurrentEmbeddables(final Field field, final Object obj) {

    final Collection<Object> currentObjects = store().builtObjects.get(obj.getClass());

    LOGGER.debug("Current object: {}", currentObjects);

    final Collection<Object> currentEmbeddables = new HashSet<>(currentObjects.size());

    for (final Object currObj : currentObjects) {
      final Object embeddedObjFieldVal = getFieldVal(field, currObj);

      LOGGER.debug("Embedded field object value: {}", embeddedObjFieldVal);

      currentEmbeddables.add(embeddedObjFieldVal);
    }

    LOGGER.debug("All current embeddable object values: {}", currentEmbeddables);

    return currentEmbeddables;
  }

  @SuppressWarnings({"MethodWithMultipleLoops", "WeakerAccess", "FeatureEnvy"})
  public static Map<Field, Object> getValuesToUse(final Field field,
      final Object obj,
      final Function<Class<?>, Cmd> cmdGeneratorParam) {

    LOGGER.debug("Get values to use. Field: {}, obj: {}, cmdGenerator: {}",
        field,
        obj,
        cmdGeneratorParam);

    final FieldValuesHolder currentObjPkValues = getCurrentObjPkFieldValues(field, obj);

    final FieldValuesHolder allPotentialValues = getPotentialPkValues(field,
        obj,
        cmdGeneratorParam);

    LOGGER.debug("currentObjPkValues: {}, allPotentialValues: {}",
        currentObjPkValues,
        allPotentialValues);

    for (final Map<Field, Object> possVal : allPotentialValues.getTreeSet()) {
      final boolean isNewFieldValue = !currentObjPkValues.getTreeSet().contains(possVal);
      if (isNewFieldValue) {
        LOGGER.debug("possVal: {} is a new value.", possVal);
        return possVal;
      }
    }

    LOGGER.debug("No new potential values, so find the least frequent used one.");

    final List<Map<Field, Object>> freqSortedPossVals = new ArrayList<>(
        allPotentialValues.getTreeSet());

    sort(freqSortedPossVals, new FieldValueFrequencyComparator(currentObjPkValues.getTreeSet()));

    LOGGER.debug("Frequency sorted potential values: {}.", freqSortedPossVals);

    if (!freqSortedPossVals.isEmpty()) {
      LOGGER.debug("Least frequent possible value: {}.", freqSortedPossVals);
      return freqSortedPossVals.get(0);
    }

    final FieldValues defaultFieldValues = fieldValues();

    LOGGER.debug("Could not find field value, so default to: {}", defaultFieldValues);

    return defaultFieldValues.getTreeMap();
  }

  @SuppressWarnings({"MethodWithMultipleLoops", "UnqualifiedInnerClassAccess", "WeakerAccess"})
  public static FieldValuesHolder getPotentialPkValues(final Field field,
      final Object obj,
      final Function<Class<?>, Cmd> cmdGenerator) {

    LOGGER.debug("Get all potential pk field values, generator: {}", cmdGenerator);

    final Map<Field, Class<?>> pkMap = createPkMap(obj.getClass(), field.getType());

    final Iterable<Class<?>> classesToGenerate = new HashSet<>(pkMap.values());

    final Map<Class<?>, List<Object>> generatedClassesMap = generateRequiredClasses(
        classesToGenerate,
        obj,
        cmdGenerator);

    final List<Set<Entry<Field, Object>>> generatedIdFieldVals =
        getClzsPkIdFieldValues(pkMap, generatedClassesMap);

    LOGGER.debug("Generated id field values: {}", generatedIdFieldVals);

    final Set<List<Entry<Field, Object>>> productAllPossIdVals = cartesianProduct(
        generatedIdFieldVals);

    LOGGER.debug("Product of id field values: {}", productAllPossIdVals);

    return createForPotentialValues(productAllPossIdVals);
  }

  @SuppressWarnings({"UnqualifiedInnerClassAccess", "ObjectAllocationInLoop",
      "MethodWithMultipleLoops", "WeakerAccess"})
  public static List<Set<Entry<Field, Object>>> getClzsPkIdFieldValues(
      final Map<Field, Class<?>> pkMap,
      final Map<Class<?>, List<Object>> generatedClassesMap) {

    LOGGER.debug("Get pk({}) id field values for classes: {}", pkMap, generatedClassesMap);

    final List<Set<Entry<Field, Object>>> pkIdFieldValues = new ArrayList<>(pkMap.size());

    for (final Entry<Field, Class<?>> pkMapEntry : pkMap.entrySet()) {

      final List<Object> generatedClasses = generatedClassesMap.get(pkMapEntry.getValue());

      LOGGER.debug("Class {} has generatedClasses: {}", pkMapEntry.getValue(), generatedClasses);

      final Set<Entry<Field, Object>> idFieldVals = new HashSet<>(generatedClasses.size());

      for (final Object generatedClass : generatedClasses) {
        final Object nestedClassIdValue = getIdVal(generatedClass);

        idFieldVals.add(new SimpleEntry<>(pkMapEntry.getKey(), nestedClassIdValue));
      }

      LOGGER.debug("Generated classes {} have id field values: {}", generatedClasses, idFieldVals);

      pkIdFieldValues.add(idFieldVals);
    }

    LOGGER.debug("All Generated classes {} have id field values: {}",
        generatedClassesMap,
        pkIdFieldValues);

    return pkIdFieldValues;
  }

  @SuppressWarnings("WeakerAccess")
  public static Map<Class<?>, List<Object>> generateRequiredClasses(
      final Iterable<Class<?>> classes,
      final Object obj,
      final Function<Class<?>, Cmd> cmdGenerator) {

    LOGGER.debug("Generate the class: {} using the generator: {}", classes, cmdGenerator);

    final Map<Class<?>, List<Object>> generatedClasses = new HashMap<>(size(classes));

    for (final Class<?> clz : classes) {
      final List<Object> nestedClasses = findNestedClasses(clz, obj, cmdGenerator.apply(clz));

      LOGGER.debug("Made nested classes with generator {}: {}", cmdGenerator, nestedClasses);

      generatedClasses.put(clz, nestedClasses);
    }

    LOGGER.debug("Generated class map: {}", generatedClasses);

    return generatedClasses;
  }

  @SuppressWarnings({"MethodWithMultipleLoops", "WeakerAccess"})
  public static FieldValuesHolder getCurrentObjPkFieldValues(final Field field,
      final Object obj) {

    LOGGER.debug("Get current store pk field values for, obj: {}, field: {}", obj, field);

    final FieldValuesHolder currObjPkFieldValues = fieldValuesHolder();

    final Collection<Object> currentObjects = store().builtObjects.get(obj.getClass());

    final Set<Field> embeddedObjectFields = createPkMap(obj.getClass(), field.getType()).keySet();

    for (final Object currObj : currentObjects) {

      final Object embeddedObject = getFieldVal(field, currObj);

      LOGGER.debug("Embedded object for {}: {}", currObj, embeddedObject);

      final FieldValues pkFieldValuesForObj = getFieldValuesForObj(embeddedObject,
          embeddedObjectFields);

      LOGGER.debug("PK values for {} are: {}", currObj, pkFieldValuesForObj);

      currObjPkFieldValues.add(pkFieldValuesForObj.getTreeMap());
    }

    LOGGER.debug("All current store pk field value ({}) for embedded objects: {}",
        field,
        currObjPkFieldValues);

    return currObjPkFieldValues;
  }

  @SuppressWarnings({"UnnecessarilyQualifiedInnerClassAccess", "WeakerAccess"})
  public static FieldValues getFieldValuesForObj(final Object object,
      final Iterable<Field> objFields) {

    LOGGER.debug("Get field values ({}) for given object {}", objFields, object);

    final FieldValues objectFieldValues = fieldValues();

    for (final Field field : objFields) {
      final Object fieldValue = getFieldVal(field, object);

      LOGGER.debug("Field value ({}) for {}:", field, fieldValue);

      objectFieldValues.put(field, fieldValue);
    }

    LOGGER.debug("Field values ({}) for given object {}: {}", objFields, object, objectFieldValues);

    return objectFieldValues;
  }

}
