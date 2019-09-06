package com.adben.testdatabuilder.entity.analyzers.introspectors.introspector;

import static com.adben.testdatabuilder.core.analyzers.generators.helper.AlphaNumericHelper.toAlphaNumeric;
import static com.adben.testdatabuilder.core.helper.ReflectionHelper.setFieldVal;
import static com.google.common.base.Strings.padEnd;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import com.adben.testdatabuilder.core.analyzers.introspectors.Introspector;
import com.google.common.base.MoreObjects;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Column;
import org.slf4j.Logger;

/**
 * {@link Introspector} for {@link String} and {@link Column} fields.
 *
 * <p>
 *
 * Similar to. But deals with the length of the required db column and if it is a CHAR extend the
 * string to match the length.
 */
public class StringColumnIntrospector implements Introspector {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  private static final char PAD_CHAR = '0';
  private static final Pattern DB_CHAR_MATCHER = Pattern.compile("^CHAR[(][0-9]+[)]$");

  private int value = 1;

  public StringColumnIntrospector() {
    super();
    LOGGER.debug("Default constructor");
  }

  @Override
  public boolean introspect(final Field field, final Object obj) throws IllegalAccessException {
    if (String.class.isAssignableFrom(field.getType()) && field.isAnnotationPresent(Column.class)) {
      LOGGER.debug("Field is an entity String: {}", field);

      final int length = field.getAnnotation(Column.class).length();
      String alphaNumeric = toAlphaNumeric(this.value);
      if (alphaNumeric.length() > length) {
        alphaNumeric = alphaNumeric.substring(0, length);
      }
      LOGGER.debug("Possible value: {}", alphaNumeric);

      final String colDef = field.getAnnotation(Column.class).columnDefinition();
      final Matcher matcher = DB_CHAR_MATCHER.matcher(colDef);
      if (matcher.matches()) {
        LOGGER.debug("Is a char value: {}", colDef);
        alphaNumeric = padEnd(alphaNumeric, length, PAD_CHAR);
      }
      LOGGER.debug("Actual value to use: {}", alphaNumeric);

      setFieldVal(obj, field, alphaNumeric);
      this.value++;
      return true;
    }

    return false;
  }

  @Override
  public String toString() {
    LOGGER.debug("toString");
    return MoreObjects.toStringHelper(this)
        .add("value", this.value)
        .toString();
  }

}
