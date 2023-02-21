package com.adben.testdatabuilder.entity.analyzers.introspectors.helper;

import static com.adben.testdatabuilder.core.helper.ReflectionHelper.getAllFields;
import static com.adben.testdatabuilder.core.helper.ReflectionHelper.getFieldVal;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.exception.DataBuilderException;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import org.slf4j.Logger;

/**
 * Helper methods for any
 *
 * {@link com.adben.testdatabuilder.core.analyzers.introspectors.Introspector Introspector}
 *
 * classes for entities.
 */
@SuppressWarnings("WeakerAccess")
public final class EntityIntrospectorHelper {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private EntityIntrospectorHelper() {
    super();
  }

  /**
   * Get the {@link Id} {@link Field} on the given {@link Class}.
   *
   * @param clz Class to find id field on.
   * @return The {@link Field} annotated with {@link Id}.
   */
  @SuppressWarnings("StaticMethodOnlyUsedInOneClass")
  public static Field getIdField(final Class<?> clz) {
    LOGGER.debug("Get id field for: {}", clz);
    for (final Field objField : clz.getDeclaredFields()) {
      if (objField.isAnnotationPresent(Id.class)) {
        LOGGER.debug("Id field for {}: {}", clz, objField);
        return objField;
      }
    }
    LOGGER.error("No id field for {}", clz);
    throw new DataBuilderException("Could not find id field.");
  }

  /**
   * Create a map from the PK field name to the associated class.
   *
   * <p>
   *
   * Loop over the {@link javax.persistence.Embeddable} class finding all field names, then from the
   * class containing the {@link javax.persistence.Embeddable} class get the associated {@link
   * JoinColumn}s which should then allow the fetching of the associated Class for the PK.
   *
   * @param main The class that the embeddable field is being set on.
   * @param embedded The Class of the field that is being embedded.
   * @return Map from PK field name to the associated class.
   */
  @SuppressWarnings({"StaticMethodOnlyUsedInOneClass", "MethodWithMultipleLoops"})
  public static Map<Field, Class<?>> createPkMap(final Class<?> main, final Class<?> embedded) {
    LOGGER.debug("Create pk map, from main: {} to embedded: {}", main, embedded);
    final Map<Field, Class<?>> fieldMap = Maps.newHashMap();
    for (final Field embeddedField : getAllFields(embedded)) {
      for (final Field mainField : getAllFields(main)) {
        if (areMainAndEmbeddedMatchingOnCol(mainField, embeddedField)) {
          LOGGER.debug("Actual match, mainField: {} and embeddedField: {}",
              mainField,
              embeddedField);
          fieldMap.put(embeddedField, mainField.getType());
        }
      }
    }
    LOGGER.debug("Field map: {}", fieldMap);
    return fieldMap;
  }

  private static boolean areMainAndEmbeddedMatchingOnCol(final Field mainField,
      final Field embeddedField) {

    final boolean potentialMatch = embeddedField.isAnnotationPresent(Column.class)
        && mainField.isAnnotationPresent(JoinColumn.class);
    LOGGER.debug("Potential match, mainField: {} and embeddedField: {}",
        mainField,
        embeddedField);
    if (potentialMatch) {
      // Check if the two field have the same db column name, it is highly likely that the PK
      // column is the id of the JoinColumn type.
      final String embeddedName = embeddedField.getAnnotation(Column.class).name();
      final String mainFieldName = mainField.getAnnotation(JoinColumn.class).name();
      if (Objects.equals(embeddedName, mainFieldName)) {
        LOGGER.debug("Actual match, mainField: {} and embeddedField: {}",
            mainFieldName,
            embeddedName);
        return true;
      }
    }
    return false;
  }

  /**
   * Get the value for the id field on the given object, using {@link
   * EntityIntrospectorHelper#getIdField}
   *
   * @param clz Class to get id value for.
   * @return Value of the id field for the given object.
   */
  public static Object getIdVal(final Object clz) {
    LOGGER.debug("Get Id field value of: {}", clz);
    final Field idField = getIdField(clz.getClass());
    final Object idFieldValue = getFieldVal(idField, clz);
    LOGGER.debug("Id field value of {} is: {}", clz, idFieldValue);
    return idFieldValue;
  }

  /**
   * Get the first field with the {@link EmbeddedId} annotation present.
   *
   * @param mainClz Class to find Embedded field on.
   * @return Optional containing {@link EmbeddedId} field.
   */
  public static Optional<? extends Class<?>> getEmbeddableClass(final Class<?> mainClz) {
    LOGGER.debug("Get embedded field for class: {}", mainClz);
    for (final Field field : getAllFields(mainClz)) {
      if (field.isAnnotationPresent(EmbeddedId.class)) {
        LOGGER.debug("Embedded field for {} is: {}", mainClz, field.getType());
        return Optional.of(field.getType());
      }
    }
    return Optional.absent();
  }

}
