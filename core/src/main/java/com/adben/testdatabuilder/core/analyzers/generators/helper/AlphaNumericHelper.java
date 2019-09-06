package com.adben.testdatabuilder.core.analyzers.generators.helper;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

/**
 * Helper class for dealing with alpha numeric strings.
 */
public final class AlphaNumericHelper {

  private static final Logger LOGGER = getLogger(lookup().lookupClass());

  /**
   * In regex talk: [A-Za-z0-9]
   */
  private static final char[] CHARSET_AZ_09 = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
      "abcdefghijklmnopqrstuvwxyz" +
      "0123456789"
  ).toCharArray();

  private AlphaNumericHelper() {
    super();
    LOGGER.trace("Default constructor");
  }

  public static char getModCharsetAZ09(final int num) {
    LOGGER.debug("Find the char for num: {}", num);
    final char modChar = CHARSET_AZ_09[num % CHARSET_AZ_09.length];
    LOGGER.debug("Return char: {}", modChar);
    return modChar;
  }

  /**
   * Essentially a wrapper around {@link AlphaNumericHelper#convertIntToString(int, char[])}, that
   * uses the char array {@link AlphaNumericHelper#CHARSET_AZ_09}.
   *
   * @param n Number to convert to alpha numeric string.
   * @return Alpha numeric string converted from the integer.
   */
  public static String toAlphaNumeric(final int n) {
    LOGGER.debug("Get alpha numeric for: {}", n);
    return convertIntToString(n, CHARSET_AZ_09);
  }

  /**
   * Inspired by: <a href="https://en.wikipedia.org/w/index.php?title=Hexavigesimal&oldid=578218059#Bijective_base-26">
   * Bijective_base-26 </a>
   *
   * @param nAttr Int to convert to a string.
   * @param charArray Array of chars to make a string from.
   * @return String generated from the supplied integer using the char array.
   */
  @SuppressWarnings({"WeakerAccess", "LongLine"})
  public static String convertIntToString(final int nAttr, final char[] charArray) {
    LOGGER.debug("Convert int ({}) to string from chars: {}", nAttr, charArray);
    int n = nAttr;
    // Avoid empty strings.
    if (n < 1) {
      n = 1;
    }
    final StringBuilder ret = new StringBuilder();
    while (n > 0) {
      --n;
      ret.append((charArray[n % charArray.length]));
      n /= charArray.length;
    }
    // reverse the result, since its
    // digits are in the wrong order
    final String output = ret.reverse().toString();
    LOGGER.debug("Output string for {}: {}", nAttr, output);
    return output;
  }

}
